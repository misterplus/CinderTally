package plus.misterplus.cinderedtally.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import plus.misterplus.cinderedtally.CinderedTally;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEntityCrucible extends TileEntity {

    /**
     * For recipe inputs.
     */
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
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
    private final LazyOptional<ItemStackHandler> itemCap = LazyOptional.of(() -> itemHandler);
    private Fluid lastFluid = Fluids.EMPTY;
    /**
     * For holding the recipe base.
     */
    private final FluidTank fluidTank = new FluidTank(1000, fluidStack -> fluidStack.getFluid() == Fluids.WATER) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            setChanged();
            cacheLastFluid(getFluid());
        }
    };
    private final LazyOptional<FluidTank> fluidCap = LazyOptional.of(() -> fluidTank);
    private int heightAmount = 0;

    public TileEntityCrucible() {
        super(CinderedTallyRegistry.TILE_CRUCIBLE);
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }

    private void cacheLastFluid(FluidStack fluidStack) {
        if (fluidStack.getAmount() > 0)
            lastFluid = fluidStack.getFluid();
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        // save here
        itemCap.ifPresent(i -> nbt.put("ItemStackHandler", i.serializeNBT()));
        fluidCap.ifPresent(f -> nbt.put("FluidTank", f.writeToNBT(new CompoundNBT())));
        return nbt;
    }

    @Override
    public void load(BlockState blockState, CompoundNBT nbt) {
        super.load(blockState, nbt);
        // load here
        itemCap.ifPresent(i -> i.deserializeNBT(nbt.getCompound("ItemStackHandler")));
        fluidCap.ifPresent(f -> {
            f.readFromNBT(nbt.getCompound("FluidTank"));
            cacheLastFluid(f.getFluid());
        });
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

    public List<ItemStack> getContainedItems() {
        List<ItemStack> itemList = new ArrayList<>();
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (itemHandler.getStackInSlot(i).isEmpty())
                break;
            else
                itemList.add(itemHandler.getStackInSlot(i));
        }
        return itemList;
    }

    private int getFluidAmount() {
        return fluidTank.getFluidAmount();
    }

    private void updateFluidHeight() {
        int viscosity = Math.max(fluidTank.getFluid().getFluid().getAttributes().getViscosity() / 50, 10);
        if (heightAmount > getFluidAmount()) {
            heightAmount -= Math.max(1, (heightAmount - getFluidAmount()) / viscosity);
        } else if (heightAmount < getFluidAmount()) {
            heightAmount += Math.max(1, (getFluidAmount() - heightAmount) / viscosity);
        }
    }

    public float getAnimatedFluidHeight() {
        updateFluidHeight();
        return 0.1875F + 0.375F * this.heightAmount / 1000;
    }

    public Fluid getLastFluid() {
        return lastFluid;
    }
}
