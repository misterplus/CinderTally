package plus.misterplus.cindertally.helper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import plus.misterplus.cindertally.common.inventory.CinderTallyInventory;
import plus.misterplus.cindertally.common.item.ItemLifeSpan;
import plus.misterplus.cindertally.registry.CinderTallyItems;

public class LifespanHelper {
    public static CinderTallyInventory getCinderTallyInventory(PlayerEntity player) {
        int lifespan = NBTHelper.getLifespan(player);
        int decades = lifespan / ItemLifeSpan.VALUE_DECADE;
        int years = lifespan % ItemLifeSpan.VALUE_DECADE / ItemLifeSpan.VALUE_YEAR;
        int seasons = lifespan % ItemLifeSpan.VALUE_YEAR / ItemLifeSpan.VALUE_SEASON;
        int months = lifespan % ItemLifeSpan.VALUE_SEASON / ItemLifeSpan.VALUE_MONTH;
        int weeks = lifespan % ItemLifeSpan.VALUE_MONTH / ItemLifeSpan.VALUE_WEEK;
        int days = lifespan % ItemLifeSpan.VALUE_WEEK / ItemLifeSpan.VALUE_DAY;
        int hours = lifespan % ItemLifeSpan.VALUE_DAY / ItemLifeSpan.VALUE_HOUR;
        int quarters = lifespan % ItemLifeSpan.VALUE_HOUR / ItemLifeSpan.VALUE_QUARTER;
        CinderTallyInventory inventory = new CinderTallyInventory();
        inventory.addItem(new ItemStack(CinderTallyItems.LIFESPAN_DECADE, decades));
        inventory.addItem(new ItemStack(CinderTallyItems.LIFESPAN_YEAR, years));
        inventory.addItem(new ItemStack(CinderTallyItems.LIFESPAN_SEASON, seasons));
        inventory.addItem(new ItemStack(CinderTallyItems.LIFESPAN_MONTH, months));
        inventory.addItem(new ItemStack(CinderTallyItems.LIFESPAN_WEEK, weeks));
        inventory.addItem(new ItemStack(CinderTallyItems.LIFESPAN_DAY, days));
        inventory.addItem(new ItemStack(CinderTallyItems.LIFESPAN_HOUR, hours));
        inventory.addItem(new ItemStack(CinderTallyItems.LIFESPAN_QUARTER, quarters));
        return inventory;
    }
}
