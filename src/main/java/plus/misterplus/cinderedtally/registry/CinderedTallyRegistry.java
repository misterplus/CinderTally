package plus.misterplus.cinderedtally.registry;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effect;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import plus.misterplus.cinderedtally.CinderedTallyConstants;
import plus.misterplus.cinderedtally.common.block.BlockResearchTable;
import plus.misterplus.cinderedtally.common.effect.EffectStasis;
import plus.misterplus.cinderedtally.common.effect.EffectTimeDilation;
import plus.misterplus.cinderedtally.common.inventory.container.CinderedTallyContainer;
import plus.misterplus.cinderedtally.common.item.ItemCinderedPage;
import plus.misterplus.cinderedtally.common.item.ItemCinderedTally;
import plus.misterplus.cinderedtally.common.item.ItemDebugStick;
import plus.misterplus.cinderedtally.common.item.ItemLifespan;
import plus.misterplus.cinderedtally.helper.LifespanHelper;

public class CinderedTallyRegistry {

    public static final ItemGroup TAB_CINDEREDTALLY = new ItemGroup(-1, CinderedTallyConstants.MOD_ID) {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.DIAMOND_BLOCK);
        }
    };

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CinderedTallyConstants.MOD_ID);
    public static final Item LIFESPAN_QUARTER = register(ITEMS, "lifespan_quarter", new ItemLifespan(ItemLifespan.properties().stacksTo(4).rarity(Rarity.COMMON), ItemLifespan.VALUE_QUARTER));
    public static final Item LIFESPAN_HOUR = register(ITEMS, "lifespan_hour", new ItemLifespan(ItemLifespan.properties().stacksTo(24).rarity(Rarity.COMMON), ItemLifespan.VALUE_HOUR));
    public static final Item LIFESPAN_DAY = register(ITEMS, "lifespan_day", new ItemLifespan(ItemLifespan.properties().stacksTo(7).rarity(Rarity.UNCOMMON), ItemLifespan.VALUE_DAY));
    public static final Item LIFESPAN_WEEK = register(ITEMS, "lifespan_week", new ItemLifespan(ItemLifespan.properties().stacksTo(4).rarity(Rarity.UNCOMMON), ItemLifespan.VALUE_WEEK));
    public static final Item LIFESPAN_MONTH = register(ITEMS, "lifespan_month", new ItemLifespan(ItemLifespan.properties().stacksTo(3).rarity(Rarity.RARE), ItemLifespan.VALUE_MONTH));
    public static final Item LIFESPAN_SEASON = register(ITEMS, "lifespan_season", new ItemLifespan(ItemLifespan.properties().stacksTo(4).rarity(Rarity.RARE), ItemLifespan.VALUE_SEASON));
    public static final Item LIFESPAN_YEAR = register(ITEMS, "lifespan_year", new ItemLifespan(ItemLifespan.properties().stacksTo(10).rarity(Rarity.EPIC), ItemLifespan.VALUE_YEAR));
    public static final Item LIFESPAN_DECADE = register(ITEMS, "lifespan_decade", new ItemLifespan(ItemLifespan.properties().stacksTo(50).rarity(Rarity.EPIC), ItemLifespan.VALUE_DECADE));
    public static final Item CINDERED_TALLY = register(ITEMS, "cindered_tally", new ItemCinderedTally(new Item.Properties().tab(TAB_CINDEREDTALLY).stacksTo(1).rarity(Rarity.EPIC)));
    public static final Item DEBUG_STICK = register(ITEMS, "debug_stick", new ItemDebugStick(new Item.Properties().tab(TAB_CINDEREDTALLY).stacksTo(1).rarity(Rarity.EPIC)));
    public static final Item CINDERED_PAGE = register(ITEMS, "cindered_page", new ItemCinderedPage(new Item.Properties().tab(TAB_CINDEREDTALLY)));
    public static final Item CINDER = register(ITEMS, "cinder", new Item(new Item.Properties().tab(TAB_CINDEREDTALLY)));
    private static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, CinderedTallyConstants.MOD_ID);
    public static final Effect STASIS = register(EFFECTS, "stasis", new EffectStasis());
    public static final Effect TIME_DILATION = register(EFFECTS, "time_dilation", new EffectTimeDilation());
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, CinderedTallyConstants.MOD_ID);
    public static final ContainerType<CinderedTallyContainer> CONTAINER_CINDER_TALLY = register(CONTAINERS, "cinder_tally", IForgeContainerType.create((windowId, playerInv, extraData) -> new CinderedTallyContainer(windowId, playerInv, LifespanHelper.getCinderedTallyInventory(extraData.readLong()))));
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CinderedTallyConstants.MOD_ID);
    public static final Block RESEARCH_TABLE = register(BLOCKS, "research_table", new BlockResearchTable());

    private static <T extends IForgeRegistryEntry<T>, E extends T> E register(DeferredRegister<T> register, String name, E entry) {
        register.register(name, () -> entry);
        return entry;
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        EFFECTS.register(bus);
        CONTAINERS.register(bus);
        BLOCKS.register(bus);
        CookingRecipeBuilder.cooking(Ingredient.of(Items.PAPER), Items.BOOK, 0.35F, 600, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE);
    }
}
