package plus.misterplus.cinderedtally.helper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import plus.misterplus.cinderedtally.CinderedTally;
import plus.misterplus.cinderedtally.CinderedTallyConstants;
import plus.misterplus.cinderedtally.common.item.ItemLifespan;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

public class LifespanHelper {
    public static Inventory getCinderedTallyInventory(long lifespan) {
        int decades = (int) (lifespan / ItemLifespan.VALUE_DECADE);
        int years = (int) (lifespan % ItemLifespan.VALUE_DECADE / ItemLifespan.VALUE_YEAR);
        int seasons = (int) (lifespan % ItemLifespan.VALUE_YEAR / ItemLifespan.VALUE_SEASON);
        int months = (int) (lifespan % ItemLifespan.VALUE_SEASON / ItemLifespan.VALUE_MONTH);
        int weeks = (int) (lifespan % ItemLifespan.VALUE_MONTH / ItemLifespan.VALUE_WEEK);
        int days = (int) (lifespan % ItemLifespan.VALUE_WEEK / ItemLifespan.VALUE_DAY);
        int hours = (int) (lifespan % ItemLifespan.VALUE_DAY / ItemLifespan.VALUE_HOUR);
        int quarters = (int) (lifespan % ItemLifespan.VALUE_HOUR / ItemLifespan.VALUE_QUARTER);
        Inventory inventory = new Inventory(8);
        inventory.setItem(0, new ItemStack(CinderedTallyRegistry.LIFESPAN_DECADE, decades));
        inventory.setItem(1, new ItemStack(CinderedTallyRegistry.LIFESPAN_YEAR, years));
        inventory.setItem(2, new ItemStack(CinderedTallyRegistry.LIFESPAN_SEASON, seasons));
        inventory.setItem(3, new ItemStack(CinderedTallyRegistry.LIFESPAN_MONTH, months));
        inventory.setItem(4, new ItemStack(CinderedTallyRegistry.LIFESPAN_WEEK, weeks));
        inventory.setItem(5, new ItemStack(CinderedTallyRegistry.LIFESPAN_DAY, days));
        inventory.setItem(6, new ItemStack(CinderedTallyRegistry.LIFESPAN_HOUR, hours));
        inventory.setItem(7, new ItemStack(CinderedTallyRegistry.LIFESPAN_QUARTER, quarters));
        return inventory;
    }

    public static Inventory getCinderedTallyInventory(PlayerEntity player) {
        return getCinderedTallyInventory(LifespanHelper.getLifespan(player));
    }

    public static void initLifespan(PlayerEntity player) {
        // lifespan in in-game years, 70-100
        int lifespan = player.getCommandSenderWorld().random.nextInt(30) + 70;
        // nbt stored in ticks
        NBTHelper.getPersistedData(player, true).putLong(CinderedTallyConstants.LIFESPAN_NBT_TAG, lifespan * ItemLifespan.VALUE_YEAR);
        CinderedTally.LOGGER.debug(String.format("Init lifespan for player %s: %d years.", player.getName().getContents(), lifespan));
    }

    public static boolean diminishLifespan(PlayerEntity player) {
        if (!player.isAlive() || EffectHelper.isEffectivelyInStasis(player))
            return false;
        long remain = getLifespan(player) - 1;
        if (remain > 0) {
            NBTHelper.getPersistedData(player, true).putLong(CinderedTallyConstants.LIFESPAN_NBT_TAG, remain);
            return false;
        } else {
            NBTHelper.getPersistedData(player, true).putLong(CinderedTallyConstants.LIFESPAN_NBT_TAG, remain);
            return true;
        }
    }

    public static void setLifespan(PlayerEntity player, int lifespan) {
        NBTHelper.getPersistedData(player, true).putLong(CinderedTallyConstants.LIFESPAN_NBT_TAG, lifespan);
    }

    public static long getLifespan(PlayerEntity player) {
        return NBTHelper.getPersistedData(player, false).getLong(CinderedTallyConstants.LIFESPAN_NBT_TAG);
    }

    public static void extendLifespan(PlayerEntity player, int lifespan) {
        NBTHelper.getPersistedData(player, true).putLong(CinderedTallyConstants.LIFESPAN_NBT_TAG, getLifespan(player) + lifespan);
    }
}
