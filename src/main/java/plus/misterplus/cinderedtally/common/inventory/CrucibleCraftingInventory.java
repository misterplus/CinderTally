package plus.misterplus.cinderedtally.common.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import plus.misterplus.cinderedtally.common.tile.TileEntityCrucible;

public class CrucibleCraftingInventory implements IInventory {

    private final ItemStackHandler itemHandler;
    private final FluidTank fluidTank;
    private final BlockPos blockPos;

    public CrucibleCraftingInventory(TileEntityCrucible te) {
        this.itemHandler = te.getItemHandler();
        this.fluidTank = te.getFluidTank();
        this.blockPos = te.getBlockPos();
    }

    @Override
    public int getContainerSize() {
        return itemHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Deprecated
    @Override
    public ItemStack removeItem(int index, int amount) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_70304_1_) {
        return null;
    }

    @Override
    public void setItem(int p_70299_1_, ItemStack p_70299_2_) {

    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(PlayerEntity p_70300_1_) {
        return true;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < itemHandler.getSlots(); i++)
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
    }

    public FluidTank getFluidTank() {
        return fluidTank;
    }

    public FluidStack getFluidStack() {
        return fluidTank.getFluid();
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}
