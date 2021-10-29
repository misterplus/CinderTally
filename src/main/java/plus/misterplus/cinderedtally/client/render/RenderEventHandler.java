package plus.misterplus.cinderedtally.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import plus.misterplus.cinderedtally.CinderedTally;
import plus.misterplus.cinderedtally.client.render.layer.StasisLayer;
import plus.misterplus.cinderedtally.helper.EffectHelper;

@Mod.EventBusSubscriber(modid = CinderedTally.MOD_ID, value = Dist.CLIENT)
public class RenderEventHandler {

    private static final ResourceLocation STASIS_OVERLAY = new ResourceLocation(CinderedTally.MOD_ID, "textures/misc/stasis_overlay.png");
    private static boolean LAYER_INIT = false;

    @SubscribeEvent
    public static void postPlayerRender(RenderPlayerEvent.Post event) {
        if (!LAYER_INIT) {
            LAYER_INIT = event.getRenderer().addLayer(new StasisLayer(event.getRenderer()));
            CinderedTally.LOGGER.debug("PostPlayerRender: Additional render layers added!");
        }
    }

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HELMET && EffectHelper.isInStasis(Minecraft.getInstance().player)) {
            //renderStasis(Minecraft.getInstance()); disable this for now, as I don't have the overlay texture yet
        }
    }

    private static void renderStasis(Minecraft mc) {
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableAlphaTest();
        mc.getTextureManager().bind(STASIS_OVERLAY);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.vertex(0.0D, screenHeight, -90.0D).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex(screenWidth, screenHeight, -90.0D).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(screenWidth, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
        tessellator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableAlphaTest();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
