package plus.misterplus.cinderedtally.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import plus.misterplus.cinderedtally.CinderedTallyConstants;

@Mod.EventBusSubscriber(modid = CinderedTallyConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CinderedTallyDataGenerator {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        if (event.includeServer()) {
            gen.addProvider(new CinderedTallyRecipeProvider(gen));
        }
    }
}
