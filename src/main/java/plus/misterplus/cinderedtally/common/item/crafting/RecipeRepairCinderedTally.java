package plus.misterplus.cinderedtally.common.item.crafting;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import plus.misterplus.cinderedtally.common.item.ItemCinderedPage;
import plus.misterplus.cinderedtally.common.item.ItemCinderedTally;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepairCinderedTally extends SpecialRecipe {
    public RecipeRepairCinderedTally(ResourceLocation p_i48169_1_) {
        super(p_i48169_1_);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        int tally = 0, pages = 0;
        for(int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemCinderedTally)
                    tally++;
                else if (stack.getItem() instanceof ItemCinderedPage)
                    pages++;
                else
                    return false;
            }
        }
        return tally == 1 && pages > 0;
    }

    @Override
    public ItemStack assemble(CraftingInventory inventory) {
        int tallyIndex = findTallyIndex(inventory);
        List<Integer> pageIndex = findPageIndex(inventory);
        if (tallyIndex == -1 && pageIndex.isEmpty())
            return null;
        ItemStack tally = inventory.getItem(tallyIndex);
        int toRepair = ItemCinderedTally.repair(tally, pageIndex.size());
        for (int i = 0; i < toRepair; i++) {
            inventory.getItem(pageIndex.get(i)).setCount(inventory.getItem(pageIndex.get(i)).getCount() - 1);
        }
        ItemStack result = tally.copy();
        tally.setCount(0);
        return result;
    }

    private List<Integer> findPageIndex(CraftingInventory inventory) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (!inventory.getItem(i).isEmpty() && inventory.getItem(i).getItem() instanceof ItemCinderedPage) {
                list.add(i);
            }
        }
        return list;
    }

    private int findTallyIndex(CraftingInventory inventory) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (!inventory.getItem(i).isEmpty() && inventory.getItem(i).getItem() instanceof ItemCinderedTally)
                return i;
        }
        return -1;
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return p_194133_1_ * p_194133_2_ >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return IRecipeSerializer.register("cinderedtally_crafting_special_repair_cindered_tally", new SpecialRecipeSerializer<>(RecipeRepairCinderedTally::new));
    }
}
