package plus.misterplus.cindertally.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

/**
 * Items that represents players lifespan,
 * it may come in various forms: a flash of light, sand, hourglasses, clocks, watches, etc.
 */
public class ItemLifeSpan extends Item {

    /**
     * Lifespan value in ticks
     */
    private int value;

    public ItemLifeSpan(Properties properties, int value) {
        super(properties);
        this.value = value;
    }

    public static Item.Properties propertiesLifeSpan() {
        return new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(10);
    }
}
