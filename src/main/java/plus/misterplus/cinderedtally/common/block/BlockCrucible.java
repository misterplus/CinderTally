package plus.misterplus.cinderedtally.common.block;

import net.minecraft.block.*;
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
import net.minecraftforge.items.ItemHandlerHelper;
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
        if (!world.isClientSide() && hand == Hand.MAIN_HAND) {
            TileEntityCrucible te = (TileEntityCrucible) world.getBlockEntity(blockPos);
            ItemStack stackInHand = playerEntity.getItemInHand(hand);
            if (playerEntity.isShiftKeyDown()) {
                if (stackInHand.isEmpty()) {
                    // remove item from crucible
                }
            } else {
                // add item to crucible
                // rework this, this fills the entire inventory
                ItemStack extra = ItemHandlerHelper.insertItem(te.getItemStackHandler(), stackInHand, false);
                playerEntity.setItemInHand(hand, extra);
                te.setChanged();
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
}
