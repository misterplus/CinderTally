package plus.misterplus.cinderedtally.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import plus.misterplus.cinderedtally.CinderedTally;
import plus.misterplus.cinderedtally.common.block.BlockCrucible;
import plus.misterplus.cinderedtally.common.block.BlockResearchTable;
import plus.misterplus.cinderedtally.common.effect.EffectStasis;
import plus.misterplus.cinderedtally.common.effect.EffectTimeDilation;
import plus.misterplus.cinderedtally.common.inventory.container.CinderedTallyContainer;
import plus.misterplus.cinderedtally.common.item.ItemCinderedPage;
import plus.misterplus.cinderedtally.common.item.ItemCinderedTally;
import plus.misterplus.cinderedtally.common.item.ItemDebugStick;
import plus.misterplus.cinderedtally.common.item.ItemLifespan;
import plus.misterplus.cinderedtally.common.item.crafting.RecipeRepairCinderedTally;
import plus.misterplus.cinderedtally.helper.LifespanHelper;
import plus.misterplus.cinderedtally.tile.TileEntityCrucible;

public class CinderedTallyRegistry {

    public static final ItemGroup TAB_CINDEREDTALLY = new ItemGroup(-1, CinderedTally.MOD_ID) {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.DIAMOND_BLOCK);
        }
    };

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CinderedTally.MOD_ID);
    private static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, CinderedTally.MOD_ID);
    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, CinderedTally.MOD_ID);
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CinderedTally.MOD_ID);
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
    public static final Item CINDERED_PAGE = register(ITEMS, "cindered_page", new ItemCinderedPage(new Item.Properties().tab(TAB_CINDEREDTALLY).stacksTo(10).rarity(Rarity.UNCOMMON)));
    public static final Item CINDER = register(ITEMS, "cinder", new Item(new Item.Properties().tab(TAB_CINDEREDTALLY)));
    public static final Item SULFUR_CRYSTAL = register(ITEMS, "sulfur_crystal", new Item(new Item.Properties().tab(TAB_CINDEREDTALLY)));
    public static final Item SULFUR_DUST = register(ITEMS, "sulfur_dust", new Item(new Item.Properties().tab(TAB_CINDEREDTALLY)));
    public static final Effect STASIS = register(EFFECTS, "stasis", new EffectStasis());
    public static final Effect TIME_DILATION = register(EFFECTS, "time_dilation", new EffectTimeDilation());
    public static final ContainerType<CinderedTallyContainer> CONTAINER_CINDER_TALLY = register(CONTAINERS, "cinder_tally", IForgeContainerType.create((windowId, playerInv, extraData) -> new CinderedTallyContainer(windowId, playerInv, LifespanHelper.getCinderedTallyInventory(extraData.readLong()))));
    public static final Block RESEARCH_TABLE = register(BLOCKS, "research_table", new BlockResearchTable());
    public static final Block SULFUR_ORE = register(BLOCKS, "sulfur_ore", new OreBlock(AbstractBlock.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
    public static final Block CRUCIBLE = register(BLOCKS, "crucible", new BlockCrucible());
    private static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CinderedTally.MOD_ID);
    public static final SpecialRecipeSerializer<RecipeRepairCinderedTally> REPAIR_CINDERED_TALLY = register(RECIPES, "repair_cindered_tally", new SpecialRecipeSerializer<>(RecipeRepairCinderedTally::new));
    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, CinderedTally.MOD_ID);
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, CinderedTally.MOD_ID);
    public static final TileEntityType<TileEntityCrucible> TILE_CRUCIBLE = register(TILE_ENTITIES, "crucible", TileEntityType.Builder.of(TileEntityCrucible::new, CRUCIBLE).build(null));

    private static <T extends IForgeRegistryEntry<T>, E extends T> E register(DeferredRegister<T> register, String name, E entry) {
        register.register(name, () -> entry);
        if (register == BLOCKS)
            register(ITEMS, name, new BlockItem((Block) entry, new Item.Properties().tab(TAB_CINDEREDTALLY)));
        return entry;
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        EFFECTS.register(bus);
        CONTAINERS.register(bus);
        BLOCKS.register(bus);
        RECIPES.register(bus);
        TILE_ENTITIES.register(bus);
    }
}
