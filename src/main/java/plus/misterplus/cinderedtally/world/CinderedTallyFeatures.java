package plus.misterplus.cinderedtally.world;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import plus.misterplus.cinderedtally.CinderedTally;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

@Mod.EventBusSubscriber(modid = CinderedTally.MOD_ID)
public class CinderedTallyFeatures {
    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(CinderedTally.MOD_ID, key), configuredFeature);
    }

    // range (height, 64 is for 0-64)
    // squared (for ores)
    // p_i241989_3_ ore blocks count for a single cluster
    // count (generation tries in a single chunk)

    // uses vanilla iron values for sulfur ores for now
    public static final ConfiguredFeature<?, ?> ORE_SULFUR = register("ore_sulfur", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, CinderedTallyRegistry.ORE_SULFUR.defaultBlockState(), 9)).range(64).squared().count(20));

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_SULFUR);
    }
}
