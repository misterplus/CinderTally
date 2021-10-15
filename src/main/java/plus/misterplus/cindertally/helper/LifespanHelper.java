package plus.misterplus.cindertally.helper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import plus.misterplus.cindertally.common.item.ItemLifeSpan;
import plus.misterplus.cindertally.registry.CinderTallyRegistry;

public class LifespanHelper {
    public static Inventory getCinderTallyInventory(long lifespan) {
        int decades = (int) (lifespan / ItemLifeSpan.VALUE_DECADE);
        int years = (int) (lifespan % ItemLifeSpan.VALUE_DECADE / ItemLifeSpan.VALUE_YEAR);
        int seasons = (int) (lifespan % ItemLifeSpan.VALUE_YEAR / ItemLifeSpan.VALUE_SEASON);
        int months = (int) (lifespan % ItemLifeSpan.VALUE_SEASON / ItemLifeSpan.VALUE_MONTH);
        int weeks = (int) (lifespan % ItemLifeSpan.VALUE_MONTH / ItemLifeSpan.VALUE_WEEK);
        int days = (int) (lifespan % ItemLifeSpan.VALUE_WEEK / ItemLifeSpan.VALUE_DAY);
        int hours = (int) (lifespan % ItemLifeSpan.VALUE_DAY / ItemLifeSpan.VALUE_HOUR);
        int quarters = (int) (lifespan % ItemLifeSpan.VALUE_HOUR / ItemLifeSpan.VALUE_QUARTER);
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
