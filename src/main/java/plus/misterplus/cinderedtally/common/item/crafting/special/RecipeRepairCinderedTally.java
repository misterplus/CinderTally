package plus.misterplus.cinderedtally.common.item.crafting.special;

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
    public boolean matches(CraftingInventory inv, World world) {
        ItemStack itemstack = ItemStack.EMPTY;
        int pages = 0;

        for (int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack itemstack1 = inv.getItem(i);
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
    public ItemStack assemble(CraftingInventory inv) {
        ItemStack itemstack = ItemStack.EMPTY;
        int pages = 0;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack itemstack1 = inv.getItem(i);
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
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return CinderedTallyRegistry.REPAIR_CINDERED_TALLY;
    }
}
