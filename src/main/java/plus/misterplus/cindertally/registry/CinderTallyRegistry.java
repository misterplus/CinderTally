package plus.misterplus.cindertally.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import plus.misterplus.cindertally.CinderTallyConstants;
import plus.misterplus.cindertally.common.effect.EffectFrozenTime;
import plus.misterplus.cindertally.common.item.ItemCinderTally;
import plus.misterplus.cindertally.common.item.ItemDebugStick;
import plus.misterplus.cindertally.common.item.ItemLifeSpan;

public class CinderTallyRegistry {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CinderTallyConstants.MOD_ID);
    private static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, CinderTallyConstants.MOD_ID);


    private static RegistryObject<Item> registerItem(String name, Item item) {
        return ITEMS.register(name, () -> item);
    }

    private static RegistryObject<Effect> registerEffect(String name, Effect effect) {
        return EFFECTS.register(name, () -> effect);
    }

    public static DeferredRegister<Item> registerItems() {
        registerItem("lifespan_quarter", new ItemLifeSpan(ItemLifeSpan.propertiesLifeSpan().rarity(Rarity.COMMON), 20 * 60 * 20 / 24 / 4));
        registerItem("lifespan_hour", new ItemLifeSpan(ItemLifeSpan.propertiesLifeSpan().rarity(Rarity.COMMON), 20 * 60 * 20 / 24));
        registerItem("lifespan_day", new ItemLifeSpan(ItemLifeSpan.propertiesLifeSpan().rarity(Rarity.UNCOMMON), 20 * 60 * 20));
        registerItem("lifespan_week", new ItemLifeSpan(ItemLifeSpan.propertiesLifeSpan().rarity(Rarity.UNCOMMON), 7 * 20 * 60 * 20));
        registerItem("lifespan_month", new ItemLifeSpan(ItemLifeSpan.propertiesLifeSpan().rarity(Rarity.RARE), 30 * 20 * 60 * 20));
        registerItem("lifespan_season", new ItemLifeSpan(ItemLifeSpan.propertiesLifeSpan().rarity(Rarity.RARE), 90 * 20 * 60 * 20));
        registerItem("lifespan_year", new ItemLifeSpan(ItemLifeSpan.propertiesLifeSpan().rarity(Rarity.EPIC), 365 * 20 * 60 * 20));
        registerItem("lifespan_decade", new ItemLifeSpan(ItemLifeSpan.propertiesLifeSpan().rarity(Rarity.EPIC), 3650 * 20 * 60 * 20));

        registerItem("cinder_tally", new ItemCinderTally(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1).rarity(Rarity.EPIC)));
        registerItem("debug_stick", new ItemDebugStick(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1).rarity(Rarity.EPIC)));
        return ITEMS;
    }

    public static DeferredRegister<Effect> registerEffects() {
        registerEffect("frozen_time", new EffectFrozenTime());
        return EFFECTS;
    }
}
