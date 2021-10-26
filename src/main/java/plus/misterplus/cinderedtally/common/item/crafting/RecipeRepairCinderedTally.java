package plus.misterplus.cinderedtally.common.item.crafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import plus.misterplus.cinderedtally.common.item.ItemCinderedPage;
import plus.misterplus.cinderedtally.common.item.ItemCinderedTally;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

public class RecipeRepairCinderedTally extends SpecialRecipe {
    public RecipeRepairCinderedTally(ResourceLocation p_i48169_1_) {
        super(p_i48169_1_);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        ItemStack itemstack = ItemStack.EMPTY;
        int pages = 0;

        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack itemstack1 = inventory.getItem(i);
            if (!itemstack1.isEmpty()) {
                if (itemstack1.getItem() instanceof ItemCinderedTally) {
                    if (!itemstack.isEmpty()) {
                        return false;
                    }
                    itemstack = itemstack1;
                } else {
                    if (!(itemstack1.getItem() instanceof ItemCinderedPage)) {
                        return false;
                    }
                    pages++;
                }
            }
        }
        return !itemstack.isEmpty() && ItemCinderedTally.isValidRepair(itemstack, pages);
    }

    @Override
    public ItemStack assemble(CraftingInventory inventory) {
        ItemStack itemstack = ItemStack.EMPTY;
        int pages = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemstack1 = inventory.getItem(i);
            Item item = itemstack1.getItem();
            if (!itemstack1.isEmpty()) {
                if (item instanceof ItemCinderedTally) {
                    if (!itemstack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    itemstack = itemstack1.copy();
                } else {
                    if (!(item instanceof ItemCinderedPage)) {
                        return ItemStack.EMPTY;
                    }
                    pages++;
                }
            }
        }
        return !itemstack.isEmpty() && ItemCinderedTally.isValidRepair(itemstack, pages) ? ItemCinderedTally.repair(itemstack, pages) : ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return p_194133_1_ * p_194133_2_ >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return CinderedTallyRegistry.REPAIR_CINDERED_TALLY;
    }
}
