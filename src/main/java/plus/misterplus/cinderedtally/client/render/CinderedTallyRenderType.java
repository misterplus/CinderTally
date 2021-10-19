package plus.misterplus.cinderedtally.client.render;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import plus.misterplus.cinderedtally.CinderedTallyConstants;

@OnlyIn(Dist.CLIENT)
public class CinderedTallyRenderType extends RenderType {

    private static final ResourceLocation STASIS_GLINT_LOCATION = new ResourceLocation(CinderedTallyConstants.MOD_ID, "textures/misc/stasis_glint.png");
    private static final RenderType STASIS = RenderType.create("stasis", DefaultVertexFormats.POSITION_TEX, 7, 256, RenderType.State.builder().setTextureState(new RenderState.TextureState(STASIS_GLINT_LOCATION, true, false)).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(ENTITY_GLINT_TEXTURING).createCompositeState(false));

    public CinderedTallyRenderType(String p_i225992_1_, VertexFormat p_i225992_2_, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_, Runnable p_i225992_7_, Runnable p_i225992_8_) {
        super(p_i225992_1_, p_i225992_2_, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_, p_i225992_7_, p_i225992_8_);
    }

    public static RenderType stasis() {
        return STASIS;
    }
}
