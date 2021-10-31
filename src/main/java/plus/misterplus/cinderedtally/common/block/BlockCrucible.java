package plus.misterplus.cinderedtally.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import plus.misterplus.cinderedtally.common.inventory.CrucibleCraftingInventory;
import plus.misterplus.cinderedtally.common.item.crafting.CrucibleRecipe;
import plus.misterplus.cinderedtally.common.tile.TileEntityCrucible;
import plus.misterplus.cinderedtally.helper.ItemStackHandlerHelper;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCrucible extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    public BlockCrucible() {
        super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.STONE).requiresCorrectToolForDrops().strength(2.0F).noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityCrucible();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void onRemove(BlockState blockState, World world, BlockPos blockPos, BlockState newState, boolean isMoving) {
        // if it's removed / replaced
        if (blockState.getBlock() != newState.getBlock()) {
            TileEntityCrucible te = (TileEntityCrucible) world.getBlockEntity(blockPos);
            te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
                ItemStack stack;
                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    stack = itemHandler.getStackInSlot(i);
                    if (stack.isEmpty())
                        break;
                    else
                        popResource(world, blockPos, stack);
                }
            });
            // fluid is ignored
        }
        super.onRemove(blockState, world, blockPos, newState, isMoving);
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult hit) {
        if (hand == Hand.MAIN_HAND) {
            TileEntityCrucible te = (TileEntityCrucible) world.getBlockEntity(blockPos);
            if (te.isCrafting())
                return ActionResultType.PASS;
            ItemStack stackInHand = playerEntity.getItemInHand(hand);
            ItemStackHandler itemStackHandler = te.getItemHandler();
            if (stackInHand.isEmpty()) {
                if (playerEntity.isShiftKeyDown()) {
                    // remove item from crucible
                    ItemStack extracted = ItemStackHandlerHelper.extractAllFromLastFilledSlot(itemStackHandler, false);
                    ItemHandlerHelper.giveItemToPlayer(playerEntity, extracted);
                } else {
                    // craft the recipe
                    CrucibleCraftingInventory inventory = new CrucibleCraftingInventory(te);
                    CrucibleRecipe recipe = world.getRecipeManager().getRecipeFor(CinderedTallyRegistry.RECIPE_CRUCIBLE, inventory, world).orElse(null);
                    Random rand = te.getRandom();
                    if (recipe != null) {
                        // save toDrain base amount
                        te.setToDrain(recipe.getToDrain());
                        // cache ingredients for rendering
                        te.cacheIngredients();
                        // cache result itemStack
                        te.setToCraft(recipe.assemble(inventory));
                        te.setChanged();
                        playParticles(world, blockPos, rand, ParticleTypes.WITCH);
                        world.playSound(playerEntity, blockPos, SoundEvents.PLAYER_LEVELUP, SoundCategory.BLOCKS, 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    } else {
                        playParticles(world, blockPos, rand, ParticleTypes.ANGRY_VILLAGER);
                        world.playSound(playerEntity, blockPos, SoundEvents.VILLAGER_NO, SoundCategory.BLOCKS, 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    }
                }
            } else if (!playerEntity.isShiftKeyDown()) {
                // try filling the fluid tank first
                boolean fluidFilled = FluidUtil.interactWithFluidHandler(playerEntity, hand, te.getFluidTank());
                if (!fluidFilled) {
                    // if not a bucket action, add item to crucible
                    ItemStack extra = ItemStackHandlerHelper.insertToFirstEmptySlot(itemStackHandler, stackInHand, false);
                    playerEntity.setItemInHand(hand, extra);
                }
            }
            return ActionResultType.sidedSuccess(world.isClientSide());
        }
        return ActionResultType.PASS;
    }

    private void playParticles(World world, BlockPos pos, Random rand, IParticleData particle) {
        double d0 = rand.nextGaussian() * 0.02D;
        double d1 = rand.nextGaussian() * 0.02D;
        double d2 = rand.nextGaussian() * 0.02D;
        world.addParticle(particle, pos.getX() + 0.5F, pos.getY() + 1.0F, pos.getZ() + 0.5F, d0, d1, d2);
    }
}
