package plus.misterplus.cindertally.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
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

    @SubscribeEvent
    public static void preRenderHand(RenderHandEvent event) {
        //FirstPersonRenderer firstPersonRenderer = Minecraft.getInstance().gameRenderer.itemInHandRenderer;
        PlayerRenderer playerRenderer = (PlayerRenderer)Minecraft.getInstance().getEntityRenderDispatcher().<AbstractClientPlayerEntity>getRenderer(Minecraft.getInstance().player);
        if (event.getHand() == Hand.MAIN_HAND) {
            renderRightHand(event.getMatrixStack(), event.getBuffers(), (int) Minecraft.getInstance().getFrameTime(), Minecraft.getInstance().player, playerRenderer);
        }
        else {
            renderLeftHand(event.getMatrixStack(), event.getBuffers(), (int) Minecraft.getInstance().getFrameTime(), Minecraft.getInstance().player, playerRenderer);
        }
    }

    public static void renderRightHand(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, AbstractClientPlayerEntity playerEntity, PlayerRenderer playerRenderer) {
        renderHand(matrixStack, iRenderTypeBuffer, i, playerEntity, (playerRenderer.getModel()).rightArm, (playerRenderer.getModel()).rightSleeve, playerRenderer);
    }

    public static void renderLeftHand(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, AbstractClientPlayerEntity playerEntity, PlayerRenderer playerRenderer) {
        renderHand(matrixStack, iRenderTypeBuffer, i, playerEntity, (playerRenderer.getModel()).leftArm, (playerRenderer.getModel()).leftSleeve, playerRenderer);
    }

    private static void renderHand(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, AbstractClientPlayerEntity playerEntity, ModelRenderer p_229145_5_, ModelRenderer p_229145_6_, PlayerRenderer playerRenderer) {
        p_229145_5_.render(matrixStack, iRenderTypeBuffer.getBuffer(CinderTallyRenderType.stasis()), i, OverlayTexture.NO_OVERLAY);
        p_229145_6_.render(matrixStack, iRenderTypeBuffer.getBuffer(CinderTallyRenderType.stasis()), i, OverlayTexture.NO_OVERLAY);
    }
}
