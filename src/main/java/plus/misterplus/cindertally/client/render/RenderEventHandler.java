package plus.misterplus.cindertally.client.render;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import plus.misterplus.cindertally.CinderTally;
import plus.misterplus.cindertally.CinderTallyConstants;
import plus.misterplus.cindertally.client.render.layer.StasisLayer;

@Mod.EventBusSubscriber(modid = CinderTallyConstants.MOD_ID, value = Dist.CLIENT)
public class RenderEventHandler {

    private static boolean LAYER_INIT = false;

    @SubscribeEvent
    public static void postPlayerRender(RenderPlayerEvent.Post event) {
        if (!LAYER_INIT) {
            LAYER_INIT = event.getRenderer().addLayer(new StasisLayer(event.getRenderer()));
            CinderTally.LOGGER.debug("PostPlayerRender: Additional render layers added!");
        }
    }
}
