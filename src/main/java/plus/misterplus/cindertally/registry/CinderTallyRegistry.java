package plus.misterplus.cindertally.registry;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import plus.misterplus.cindertally.CinderTallyConstants;
import plus.misterplus.cindertally.common.effect.EffectStasis;
import plus.misterplus.cindertally.common.inventory.container.CinderTallyContainer;
import plus.misterplus.cindertally.common.item.ItemCinderTally;
import plus.misterplus.cindertally.common.item.ItemDebugStick;
import plus.misterplus.cindertally.common.item.ItemLifeSpan;

public class CinderTallyRegistry {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CinderTallyConstants.MOD_ID);
    private static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, CinderTallyConstants.MOD_ID);
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, CinderTallyConstants.MOD_ID);

    private static RegistryObject<ContainerType<?>> registerContainer(String name, ContainerType<?> container) {
        return CONTAINERS.register(name, () -> container);
    }

    private static RegistryObject<Item> registerItem(String name, Item item) {
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<Effect> registerEffect(String name, Effect effect) {
        return EFFECTS.register(name, () -> effect);
    }

    private static DeferredRegister<Item> registerItems() {
        registerItem("lifespan_quarter", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(4).rarity(Rarity.COMMON), ItemLifeSpan.VALUE_QUARTER));
        registerItem("lifespan_hour", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(24).rarity(Rarity.COMMON), ItemLifeSpan.VALUE_HOUR));
        registerItem("lifespan_day", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(7).rarity(Rarity.UNCOMMON), ItemLifeSpan.VALUE_DAY));
        registerItem("lifespan_week", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(4).rarity(Rarity.UNCOMMON), ItemLifeSpan.VALUE_WEEK));
        registerItem("lifespan_month", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(3).rarity(Rarity.RARE), ItemLifeSpan.VALUE_MONTH));
        registerItem("lifespan_season", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(4).rarity(Rarity.RARE), ItemLifeSpan.VALUE_SEASON));
        registerItem("lifespan_year", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(10).rarity(Rarity.EPIC), ItemLifeSpan.VALUE_YEAR));
        registerItem("lifespan_decade", new ItemLifeSpan(ItemLifeSpan.properties().stacksTo(50).rarity(Rarity.EPIC), ItemLifeSpan.VALUE_DECADE));

        registerItem("cinder_tally", new ItemCinderTally(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1).rarity(Rarity.EPIC)));
        registerItem("debug_stick", new ItemDebugStick(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1).rarity(Rarity.EPIC)));
        return ITEMS;
    }

    private static DeferredRegister<Effect> registerEffects() {
        registerEffect("stasis", new EffectStasis());
        return EFFECTS;
    }

    private static DeferredRegister<ContainerType<?>> registerContainers() {
        registerContainer("container_cindertally", new ContainerType<>(CinderTallyContainer::new));
        return CONTAINERS;
    }

    public static void registerAll(IEventBus modEventBus) {
        registerItems().register(modEventBus);
        registerEffects().register(modEventBus);
        registerContainers().register(modEventBus);
    }
}
