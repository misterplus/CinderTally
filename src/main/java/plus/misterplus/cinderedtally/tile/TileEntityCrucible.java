package plus.misterplus.cinderedtally.tile;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityCrucible extends TileEntity {

    public ItemStackHandler getItemStackHandler() {
        return itemStackHandler;
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }

    /**
     * For recipe inputs.
     */
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(4){
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };
    /**
     * For holding the recipe base.
     */
    private final FluidTank fluidTank = new FluidTank(1000, fluidStack -> fluidStack.getFluid() == Fluids.WATER);

    public TileEntityCrucible() {
        super(CinderedTallyRegistry.TILE_CRUCIBLE);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        // save here
        nbt.put("ItemStackHandler", itemStackHandler.serializeNBT());
        nbt.put("FluidTank", fluidTank.writeToNBT(nbt));
        return nbt;
    }

    @Override
    public void load(BlockState blockState, CompoundNBT nbt) {
        super.load(blockState, nbt);
        // load here
        itemStackHandler.deserializeNBT(nbt.getCompound("ItemStackHandler"));
        fluidTank.readFromNBT(nbt.getCompound("FluidTank"));
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(super.getUpdateTag());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getBlockPos(), -1, serializeNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        deserializeNBT(pkt.getTag());
    }

    private final LazyOptional<IItemHandler> itemHandlerCap = LazyOptional.of(() -> itemStackHandler);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null) {
                return itemHandlerCap.cast();
            }
        }
        return super.getCapability(cap, side);
    }
}
