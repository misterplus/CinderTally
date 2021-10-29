package plus.misterplus.cinderedtally.tile;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityCrucible extends TileEntity {

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }

    /**
     * For recipe inputs.
     */
    private final ItemStackHandler itemHandler = new ItemStackHandler(4){
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
    };
    /**
     * For holding the recipe base.
     */
    private final FluidTank fluidTank = new FluidTank(1000, fluidStack -> fluidStack.getFluid() == Fluids.WATER){
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            setChanged();
        }
    };

    public TileEntityCrucible() {
        super(CinderedTallyRegistry.TILE_CRUCIBLE);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        // save here
        nbt.put("ItemStackHandler", itemHandler.serializeNBT());
        nbt.put("FluidTank", fluidTank.writeToNBT(new CompoundNBT()));
        return nbt;
    }

    @Override
    public void load(BlockState blockState, CompoundNBT nbt) {
        super.load(blockState, nbt);
        // load here
        itemHandler.deserializeNBT(nbt.getCompound("ItemStackHandler"));
        fluidTank.readFromNBT(nbt.getCompound("FluidTank"));
    }

    public FluidStack getContainedFluidStack() {
        return fluidTank.getFluid();
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

    private final LazyOptional<IItemHandler> itemCap = LazyOptional.of(() -> itemHandler);
    private final LazyOptional<IFluidHandler> fluidCap = LazyOptional.of(() -> fluidTank);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemCap.cast();
        } else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidCap.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        itemCap.invalidate();
        fluidCap.invalidate();
    }
}
