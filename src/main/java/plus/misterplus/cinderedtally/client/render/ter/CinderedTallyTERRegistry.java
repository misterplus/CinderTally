package plus.misterplus.cinderedtally.client.render.ter;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CinderedTallyTERRegistry {

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        ClientRegistry.bindTileEntityRenderer(CinderedTallyRegistry.TILE_CRUCIBLE, CrucibleTER::new);
    }
}
