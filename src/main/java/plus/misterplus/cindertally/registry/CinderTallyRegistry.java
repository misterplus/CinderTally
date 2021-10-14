package plus.misterplus.cindertally.registry;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.potion.Effect;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import plus.misterplus.cindertally.CinderTallyConstants;
import plus.misterplus.cindertally.common.effect.EffectStasis;
import plus.misterplus.cindertally.common.inventory.container.CinderTallyContainer;
import plus.misterplus.cindertally.common.item.ItemCinderTally;
import plus.misterplus.cindertally.common.item.ItemDebugStick;
import plus.misterplus.cindertally.common.item.ItemLifeSpan;
import plus.misterplus.cindertally.helper.LifespanHelper;

public class CinderTallyRegistry {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CinderTallyConstants.MOD_ID);
    public static final Item LIFESPAN_QUARTER = register(ITEMS, "lifespan_quarter", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(4).rarity(Rarity.COMMON), ItemLifeSpan.VALUE_QUARTER));
    public static final Item LIFESPAN_HOUR = register(ITEMS, "lifespan_hour", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(24).rarity(Rarity.COMMON), ItemLifeSpan.VALUE_HOUR));
    public static final Item LIFESPAN_DAY = register(ITEMS, "lifespan_day", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(7).rarity(Rarity.UNCOMMON), ItemLifeSpan.VALUE_DAY));
    public static final Item LIFESPAN_WEEK = register(ITEMS, "lifespan_week", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(4).rarity(Rarity.UNCOMMON), ItemLifeSpan.VALUE_WEEK));
    public static final Item LIFESPAN_MONTH = register(ITEMS, "lifespan_month", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(3).rarity(Rarity.RARE), ItemLifeSpan.VALUE_MONTH));
    public static final Item LIFESPAN_SEASON = register(ITEMS, "lifespan_season", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(4).rarity(Rarity.RARE), ItemLifeSpan.VALUE_SEASON));
    public static final Item LIFESPAN_YEAR = register(ITEMS, "lifespan_year", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(10).rarity(Rarity.EPIC), ItemLifeSpan.VALUE_YEAR));
    public static final Item LIFESPAN_DECADE = register(ITEMS, "lifespan_decade", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(50).rarity(Rarity.EPIC), ItemLifeSpan.VALUE_DECADE));
    public static final Item CINDER_TALLY = register(ITEMS, "cinder_tally", new ItemCinderTally(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1).rarity(Rarity.EPIC)));
    public static final Item DEBUG_STICK = register(ITEMS, "debug_stick", new ItemDebugStick(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1).rarity(Rarity.EPIC)));
    private static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, CinderTallyConstants.MOD_ID);
    public static final Effect STASIS = register(EFFECTS, "stasis", new EffectStasis());
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, CinderTallyConstants.MOD_ID);
    public static final ContainerType<CinderTallyContainer> CONTAINER_CINDER_TALLY = register(CONTAINERS, "cinder_tally", IForgeContainerType.create((windowId, playerInv, extraData) -> new CinderTallyContainer(windowId, playerInv, LifespanHelper.getCinderTallyInventory(extraData.readInt()))));

    private static <T extends IForgeRegistryEntry<T>, E extends T> E register(DeferredRegister<T> register, String name, E entry) {
        register.register(name, () -> entry);
        return entry;
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        EFFECTS.register(modEventBus);
        CONTAINERS.register(modEventBus);
    }
}
