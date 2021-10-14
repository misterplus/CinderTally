package plus.misterplus.cindertally.common.event;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import plus.misterplus.cindertally.CinderTallyConstants;
import plus.misterplus.cindertally.client.gui.screen.CinderTallyScreen;
import plus.misterplus.cindertally.registry.CinderTallyRegistry;

@Mod.EventBusSubscriber(modid = CinderTallyConstants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ScreenManager.register(CinderTallyRegistry.CONTAINER_CINDER_TALLY, CinderTallyScreen::new);
    }
}
