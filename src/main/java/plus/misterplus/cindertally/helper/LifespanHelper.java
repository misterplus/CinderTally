package plus.misterplus.cindertally.helper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import plus.misterplus.cindertally.common.item.ItemLifespan;
import plus.misterplus.cindertally.registry.CinderTallyRegistry;

public class LifespanHelper {
    public static Inventory getCinderTallyInventory(long lifespan) {
        int decades = (int) (lifespan / ItemLifespan.VALUE_DECADE);
        int years = (int) (lifespan % ItemLifespan.VALUE_DECADE / ItemLifespan.VALUE_YEAR);
        int seasons = (int) (lifespan % ItemLifespan.VALUE_YEAR / ItemLifespan.VALUE_SEASON);
        int months = (int) (lifespan % ItemLifespan.VALUE_SEASON / ItemLifespan.VALUE_MONTH);
        int weeks = (int) (lifespan % ItemLifespan.VALUE_MONTH / ItemLifespan.VALUE_WEEK);
        int days = (int) (lifespan % ItemLifespan.VALUE_WEEK / ItemLifespan.VALUE_DAY);
        int hours = (int) (lifespan % ItemLifespan.VALUE_DAY / ItemLifespan.VALUE_HOUR);
        int quarters = (int) (lifespan % ItemLifespan.VALUE_HOUR / ItemLifespan.VALUE_QUARTER);
        Inventory inventory = new Inventory(8);
        inventory.setItem(0, new ItemStack(CinderTallyRegistry.LIFESPAN_DECADE, decades));
        inventory.setItem(1, new ItemStack(CinderTallyRegistry.LIFESPAN_YEAR, years));
        inventory.setItem(2, new ItemStack(CinderTallyRegistry.LIFESPAN_SEASON, seasons));
        inventory.setItem(3, new ItemStack(CinderTallyRegistry.LIFESPAN_MONTH, months));
        inventory.setItem(4, new ItemStack(CinderTallyRegistry.LIFESPAN_WEEK, weeks));
        inventory.setItem(5, new ItemStack(CinderTallyRegistry.LIFESPAN_DAY, days));
        inventory.setItem(6, new ItemStack(CinderTallyRegistry.LIFESPAN_HOUR, hours));
        inventory.setItem(7, new ItemStack(CinderTallyRegistry.LIFESPAN_QUARTER, quarters));
        return inventory;
    }

    public static Inventory getCinderTallyInventory(PlayerEntity player) {
        return getCinderTallyInventory(NBTHelper.getLifespan(player));
    }
}
