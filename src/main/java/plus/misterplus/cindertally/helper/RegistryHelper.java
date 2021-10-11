package plus.misterplus.cindertally.helper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import plus.misterplus.cindertally.CinderTally;
import plus.misterplus.cindertally.CinderTallyConstants;
import plus.misterplus.cindertally.common.item.ItemCinderTally;
import plus.misterplus.cindertally.common.item.ItemLifeSpan;

public class RegistryHelper {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CinderTallyConstants.MOD_ID);

    private static RegistryObject<Item> registerItem(String name, Item item){
        return ITEMS.register(name, () -> item);
    }

    private static Item.Properties propertiesLifeSpan() {
        return new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(10).rarity(Rarity.RARE);
    }

    public static DeferredRegister<Item> registerItems() {
        registerItem("lifespan_year", new ItemLifeSpan(propertiesLifeSpan(), 365));
        registerItem("lifespan_decade", new ItemLifeSpan(propertiesLifeSpan(), 3650));
        registerItem("cinder_tally", new ItemCinderTally(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1).rarity(Rarity.EPIC)));
        return ITEMS;
    }
}
