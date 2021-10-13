package plus.misterplus.cindertally.common.event;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.OverlayRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import plus.misterplus.cindertally.helper.EffectHelper;

public class RenderEventHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void postLivingRender(RenderLivingEvent.Post event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (EffectHelper.isInStasis(player)) {
                // what now
            }
        }
    }
}
