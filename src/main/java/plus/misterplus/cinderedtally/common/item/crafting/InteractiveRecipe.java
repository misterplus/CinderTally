package plus.misterplus.cinderedtally.common.item.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;

public abstract class InteractiveRecipe<C extends IInventory> implements IRecipe<C> {

    // no gui, so it can always craft
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }
}
