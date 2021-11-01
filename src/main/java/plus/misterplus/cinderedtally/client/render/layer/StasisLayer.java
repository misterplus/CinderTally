package plus.misterplus.cinderedtally.client.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import plus.misterplus.cinderedtally.client.render.CinderedTallyRenderType;
import plus.misterplus.cinderedtally.helper.EffectHelper;

public class StasisLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {


    public StasisLayer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> p_i50926_1_) {
        super(p_i50926_1_);
    }

    @Override
    public void render(MatrixStack mStack, IRenderTypeBuffer buffer, int packedLight, AbstractClientPlayerEntity clientPlayerEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (EffectHelper.isInStasis(clientPlayerEntity)) {
            mStack.pushPose();
            getParentModel().renderToBuffer(mStack, buffer.getBuffer(CinderedTallyRenderType.stasis()), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            mStack.popPose();
        }
    }
}
