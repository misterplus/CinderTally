package plus.misterplus.cinderedtally.common.item;

import net.minecraft.item.Item;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

/**
 * Items that represents players lifespan,
 * it may come in various forms: a flash of light, sand, hourglasses, clocks, watches, etc.
 */
public class ItemLifespan extends Item {

    public static final long VALUE_DAY = 20 * 60 * 20;
    public static final long VALUE_HOUR = VALUE_DAY / 24;
    public static final long VALUE_QUARTER = VALUE_HOUR / 4;
    public static final long VALUE_WEEK = VALUE_DAY * 7;
    public static final long VALUE_MONTH = VALUE_WEEK * 4;
    public static final long VALUE_SEASON = VALUE_MONTH * 3;
    public static final long VALUE_YEAR = VALUE_SEASON * 4;
    public static final long VALUE_DECADE = VALUE_YEAR * 10;

    // unused
    public static final long VALUE_CENTURY = VALUE_DECADE * 10;
    public static final long VALUE_MILLENNIUM = VALUE_CENTURY * 10;
    public static final long VALUE_EON = VALUE_MILLENNIUM * 1000000;

    /**
     * Lifespan value in ticks
     */
    private final long value;

    public ItemLifespan(Properties properties, long value) {
        super(properties);
        this.value = value;
    }

    public static Item.Properties properties() {
        return new Item.Properties().tab(CinderedTallyRegistry.TAB_CINDEREDTALLY);
    }

    public long getValue() {
        return value;
    }
}