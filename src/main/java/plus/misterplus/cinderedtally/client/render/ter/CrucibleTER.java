package plus.misterplus.cinderedtally.client.render.ter;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraftforge.fluids.FluidStack;
import plus.misterplus.cinderedtally.tile.TileEntityCrucible;

public class CrucibleTER extends TileEntityRenderer<TileEntityCrucible> {
    public CrucibleTER(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(TileEntityCrucible tile, float partialTicks, MatrixStack mStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        Minecraft mc = Minecraft.getInstance();

        FluidStack fluidStack = tile.getContainedFluidStack();
        Fluid fluid = fluidStack.getFluid();
        if (fluid != Fluids.EMPTY)
        {
            mStack.pushPose();

            IVertexBuilder builder = buffer.getBuffer(RenderType.translucentNoCrumbling());
            // get the fluid texture from the texture atlas, u = x coordinates, v = y coordinates
            // u0 < u1, v0 < v1
            TextureAtlasSprite still = mc.getTextureAtlas(PlayerContainer.BLOCK_ATLAS).apply(fluid.getAttributes().getStillTexture());

            int color = fluid.getAttributes().getColor();
            int r = color >> 16 & 0xFF;
            int g = color >> 8 & 0xFF;
            int b = color & 0xFF;
            int a = color >> 24 & 0xFF;

            float height = 0.5625F;
            int light = WorldRenderer.getLightColor(tile.getLevel(), tile.getBlockPos());

            //matrixStack.getLast().getPositionMatrix(), x, y, z  color(r,g,b,a)  texture(u,v)
            builder.vertex(mStack.last().pose(), 0.1875F, height, 0.1875F).color(r, g, b, a).uv(still.getU0(), still.getV0()).uv2(light).normal(1.0F, 0, 0).endVertex();
            builder.vertex(mStack.last().pose(), 0.1875F, height, 0.8125F).color(r, g, b, a).uv(still.getU0(), still.getV1()).uv2(light).normal(1.0F, 0, 0).endVertex();
            builder.vertex(mStack.last().pose(), 0.8125F, height, 0.8125F).color(r, g, b, a).uv(still.getU1(), still.getV1()).uv2(light).normal(1.0F, 0, 0).endVertex();
            builder.vertex(mStack.last().pose(), 0.8125F, height, 0.1875F).color(r, g, b, a).uv(still.getU1(), still.getV0()).uv2(light).normal(1.0F, 0, 0).endVertex();

            mStack.popPose();
        }
    }
}
