package plus.misterplus.cinderedtally.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import plus.misterplus.cinderedtally.common.item.crafting.CrucibleRecipe;
import plus.misterplus.cinderedtally.common.item.wrapper.CrucibleRecipeWrapper;
import plus.misterplus.cinderedtally.common.tile.TileEntityCrucible;
import plus.misterplus.cinderedtally.helper.ItemStackHandlerHelper;
import plus.misterplus.cinderedtally.helper.SoundHelper;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCrucible extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    private static final VoxelShape SHAPE = VoxelShapes.or(
            // feet
            box(2, 0, 2, 4, 2, 4),
            box(12, 0, 2, 14, 2, 4),
            box(2, 0, 12, 4, 2, 14),
            box(12, 0, 12, 14, 2, 14),
            // body
            VoxelShapes.join(
                    box(2, 2, 2, 14, 9, 14),
                    box(3, 3, 3, 13, 9, 13),
                    IBooleanFunction.ONLY_FIRST
            ),
            // top
            VoxelShapes.join(
                    box(1, 10, 1, 15, 10, 15),
                    box(2, 10, 2, 14, 10, 14),
                    IBooleanFunction.ONLY_FIRST
            )
    );

    public BlockCrucible() {
        super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.STONE).requiresCorrectToolForDrops().strength(2.0F).noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
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
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        // if it's removed / replaced
        if (state.getBlock() != newState.getBlock()) {
            TileEntityCrucible te = (TileEntityCrucible) world.getBlockEntity(pos);
            te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
                ItemStack stack;
                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    stack = itemHandler.getStackInSlot(i);
                    if (stack.isEmpty())
                        break;
                    else
                        popResource(world, pos, stack);
                }
            });
            // fluid is ignored
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (hand == Hand.MAIN_HAND) {
            TileEntityCrucible tile = (TileEntityCrucible) world.getBlockEntity(pos);
            if (tile.isCrafting())
                return ActionResultType.PASS;
            ItemStack stackInHand = player.getItemInHand(hand);
            ItemStackHandler itemStackHandler = tile.getItemHandler();
            if (stackInHand.isEmpty()) {
                if (player.isShiftKeyDown()) {
                    // remove item from crucible
                    ItemStack extracted = ItemStackHandlerHelper.extractAllFromLastFilledSlot(itemStackHandler, false);
                    ItemHandlerHelper.giveItemToPlayer(player, extracted);
                } else {
                    // craft the recipe
                    CrucibleRecipeWrapper wrapper = new CrucibleRecipeWrapper(tile);
                    CrucibleRecipe recipe = world.getRecipeManager().getRecipeFor(CinderedTallyRegistry.RECIPE_CRUCIBLE, wrapper, world).orElse(null);
                    Random rand = tile.getRandom();
                    if (recipe != null) {
                        // save toDrain base amount
                        tile.setToDrain(recipe.getToDrain());
                        // cache ingredients for rendering
                        tile.cacheIngredients();
                        // cache result itemStack
                        tile.setToCraft(recipe.assemble(wrapper));
                        tile.setChanged();
                        playParticles(world, pos, rand, ParticleTypes.WITCH);
                        world.playSound(player, pos, SoundEvents.PLAYER_LEVELUP, SoundCategory.BLOCKS, 1.0F, SoundHelper.getPitch(rand));
                    } else {
                        playParticles(world, pos, rand, ParticleTypes.ANGRY_VILLAGER);
                        world.playSound(player, pos, SoundEvents.VILLAGER_NO, SoundCategory.BLOCKS, 1.0F, SoundHelper.getPitch(rand));
                    }
                }
            } else if (!player.isShiftKeyDown()) {
                // try filling the fluid tank first
                boolean fluidFilled = FluidUtil.interactWithFluidHandler(player, hand, tile.getFluidTank());
                if (!fluidFilled) {
                    // if not a bucket action, add item to crucible
                    ItemStack extra = ItemStackHandlerHelper.insertToFirstEmptySlot(itemStackHandler, stackInHand, false);
                    player.setItemInHand(hand, extra);
                }
            }
            return ActionResultType.sidedSuccess(world.isClientSide());
        }
        return ActionResultType.PASS;
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        if (!world.isClientSide() && x > pos.getX() + 0.0625D && x < pos.getX() + 0.9375D && z > pos.getZ() + 0.0625D && z < pos.getZ() + 0.9375D && y < pos.getY() + 0.5625D && world.getBlockState(pos.below()).getBlock() == Blocks.FIRE && !entity.isOnFire()) {
            entity.setSecondsOnFire(3);
        }
    }

    private void playParticles(World world, BlockPos pos, Random rand, IParticleData particle) {
        double d0 = rand.nextGaussian() * 0.02D;
        double d1 = rand.nextGaussian() * 0.02D;
        double d2 = rand.nextGaussian() * 0.02D;
        world.addParticle(particle, pos.getX() + 0.5F, pos.getY() + 0.7F, pos.getZ() + 0.5F, d0, d1, d2);
    }
}
