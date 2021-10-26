package plus.misterplus.cinderedtally.common.event;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import plus.misterplus.cinderedtally.CinderedTally;
import plus.misterplus.cinderedtally.client.gui.screen.CinderedTallyScreen;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

@Mod.EventBusSubscriber(modid = CinderedTally.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ScreenManager.register(CinderedTallyRegistry.CONTAINER_CINDER_TALLY, CinderedTallyScreen::new);
    }
}
