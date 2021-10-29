package plus.misterplus.cinderedtally.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import plus.misterplus.cinderedtally.helper.ItemStackHandlerHelper;
import plus.misterplus.cinderedtally.tile.TileEntityCrucible;

import javax.annotation.Nullable;

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

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityCrucible();
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult hit) {
        if (hand == Hand.MAIN_HAND) {
            TileEntityCrucible te = (TileEntityCrucible) world.getBlockEntity(blockPos);
            ItemStack stackInHand = playerEntity.getItemInHand(hand);
            ItemStackHandler itemStackHandler = te.getItemHandler();
            if (playerEntity.isShiftKeyDown()) {
                if (stackInHand.isEmpty()) {
                    // remove item from crucible
                    ItemStack extracted = ItemStackHandlerHelper.extractAllFromLastFilledSlot(itemStackHandler, false);
                    ItemHandlerHelper.giveItemToPlayer(playerEntity, extracted);
                }
            } else {
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
}
