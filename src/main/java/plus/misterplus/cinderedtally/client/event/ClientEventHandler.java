package plus.misterplus.cinderedtally.client.event;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import plus.misterplus.cinderedtally.CinderedTally;
import plus.misterplus.cinderedtally.client.gui.screen.CinderedTallyScreen;
import plus.misterplus.cinderedtally.client.render.ter.CrucibleTER;
import plus.misterplus.cinderedtally.registry.CinderedTallyRegistry;

@Mod.EventBusSubscriber(modid = CinderedTally.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ScreenManager.register(CinderedTallyRegistry.CONTAINER_CINDER_TALLY, CinderedTallyScreen::new);
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        ClientRegistry.bindTileEntityRenderer(CinderedTallyRegistry.TILE_CRUCIBLE, CrucibleTER::new);
        ModelLoader.addSpecialModel(new ResourceLocation(CinderedTally.MOD_ID, "block/crucible_scoop"));
    }
}
