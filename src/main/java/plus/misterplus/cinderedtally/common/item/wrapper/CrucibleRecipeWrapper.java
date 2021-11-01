package plus.misterplus.cinderedtally.common.item.wrapper;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import plus.misterplus.cinderedtally.common.tile.TileEntityCrucible;

public class CrucibleRecipeWrapper extends RecipeWrapper {

    private final FluidTank fluidTank;
    private final BlockPos blockPos;

    public CrucibleRecipeWrapper(TileEntityCrucible tile) {
        super(tile.getItemHandler());
        this.fluidTank = tile.getFluidTank();
        this.blockPos = tile.getBlockPos();
    }

    public FluidStack getFluidStack() {
        return fluidTank.getFluid();
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}
