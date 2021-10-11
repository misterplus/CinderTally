package plus.misterplus.cindertally.common.item;

import net.minecraft.item.Item;

/**
 * Items that represents players lifespan,
 * it may come in various forms: a flash of light, sand, hourglasses, clocks, watches, etc.
 */
public class ItemLifeSpan extends Item {

    private int value;

    public ItemLifeSpan(Properties properties, int value) {
        super(properties);
        this.value = value * 20 * 60; //lifespan value in seconds: days * 20min/day * 60sec/min
    }
}
