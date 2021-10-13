package plus.misterplus.cindertally.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

/**
 * Items that represents players lifespan,
 * it may come in various forms: a flash of light, sand, hourglasses, clocks, watches, etc.
 */
public class ItemLifeSpan extends Item {

    public int getValue() {
        return value;
    }

    /**
     * Lifespan value in ticks
     */
    private int value;

    public static final int VALUE_QUARTER = 20 * 60 * 20 / 24 / 4;
    public static final int VALUE_HOUR = 20 * 60 * 20 / 24;
    public static final int VALUE_DAY = 20 * 60 * 20;
    public static final int VALUE_WEEK = 7 * 20 * 60 * 20;
    public static final int VALUE_MONTH = 30 * 20 * 60 * 20;
    public static final int VALUE_SEASON = 90 * 20 * 60 * 20;
    public static final int VALUE_YEAR = 365 * 20 * 60 * 20;
    public static final int VALUE_DECADE =  3650 * 20 * 60 * 20;

    public ItemLifeSpan(Properties properties, int value) {
        super(properties);
        this.value = value;
    }

    public static Item.Properties properties() {
        return new Item.Properties().tab(ItemGroup.TAB_MISC);
    }
}
