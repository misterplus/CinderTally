package plus.misterplus.cinderedtally.client.render.ter;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;
import plus.misterplus.cinderedtally.CinderedTally;
import plus.misterplus.cinderedtally.common.tile.TileEntityCrucible;

import java.util.List;
import java.util.Random;

public class CrucibleTER extends TileEntityRenderer<TileEntityCrucible> {
    public CrucibleTER(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(TileEntityCrucible tile, float partialTicks, MatrixStack mStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        Minecraft mc = Minecraft.getInstance();

        Fluid fluid = tile.getLastFluid();
        float height = tile.getAnimatedFluidHeight();
        if (fluid != Fluids.EMPTY && height > 0.1875F) {
            IVertexBuilder builder = buffer.getBuffer(RenderType.translucentNoCrumbling());
            // get the fluid texture from the texture atlas, u = x coordinates, v = y coordinates
            // u0 < u1, v0 < v1
            TextureAtlasSprite still = mc.getTextureAtlas(PlayerContainer.BLOCK_ATLAS).apply(fluid.getAttributes().getStillTexture());

            int color = fluid.getAttributes().getColor();
            int r = color >> 16 & 0xFF;
            int g = color >> 8 & 0xFF;
            int b = color & 0xFF;
            int a = color >> 24 & 0xFF;

            int light = WorldRenderer.getLightColor(tile.getLevel(), tile.getBlockPos());

            mStack.pushPose();

            //matrixStack.getLast().getPositionMatrix(), x, y, z  color(r,g,b,a)  texture(u,v)
            builder.vertex(mStack.last().pose(), 0.1875F, height, 0.1875F).color(r, g, b, a).uv(still.getU0(), still.getV0()).uv2(light).normal(1.0F, 0, 0).endVertex();
            builder.vertex(mStack.last().pose(), 0.1875F, height, 0.8125F).color(r, g, b, a).uv(still.getU0(), still.getV1()).uv2(light).normal(1.0F, 0, 0).endVertex();
            builder.vertex(mStack.last().pose(), 0.8125F, height, 0.8125F).color(r, g, b, a).uv(still.getU1(), still.getV1()).uv2(light).normal(1.0F, 0, 0).endVertex();
            builder.vertex(mStack.last().pose(), 0.8125F, height, 0.1875F).color(r, g, b, a).uv(still.getU1(), still.getV0()).uv2(light).normal(1.0F, 0, 0).endVertex();

            mStack.popPose();
        }

        // TODO: rotate items when crafting
        List<ItemStack> itemList = tile.getContainedItems();
        Random rand = tile.getRandom();
        if (!itemList.isEmpty()) {
            ItemRenderer itemRenderer = mc.getItemRenderer();
            IBakedModel model;
            float f1, f2, f3;
            mStack.pushPose();
            // move to the bottom of the crucible
            mStack.translate(0F, 0.2F, 0F);
            // rotate around the x axis 90 degrees, to flat
            mStack.mulPose(Vector3f.XP.rotationDegrees(90));

            for (ItemStack stack : itemList) {
                // move to a random spot on the bottom of the crucible
                f1 = rand.nextFloat() * 0.4F + 0.3F;
                f2 = rand.nextFloat() * 0.4F + 0.3F;
                // x is rotated, (x, z, -y) is the axis now
                mStack.translate(f1, f2, 0F);

                // move y up a bit if it's a block
                if (stack.getItem() instanceof BlockItem)
                    mStack.translate(0F, 0F, -0.0725F);

                // rotate around the z axis for a random degree
                f3 = rand.nextFloat();
                mStack.mulPose(Vector3f.ZP.rotationDegrees(f3 * 360));

                // scale down to a third of the size (scale before rendering)
                mStack.scale(0.33F, 0.33F, 0.33F);

                // do the actual rendering
                model = itemRenderer.getModel(stack, tile.getLevel(), null);
                itemRenderer.render(stack, ItemCameraTransforms.TransformType.FIXED, true, mStack, buffer, combinedLight, combinedOverlay, model);

                // reset mStack in reverse order, scale, z axis, position
                mStack.scale(3.0F, 3.0F, 3.0F);
                mStack.mulPose(Vector3f.ZP.rotationDegrees(360 - f3 * 360));
                mStack.translate(-f1, -f2, 0F);
                // undo move up if it's a block
                if (stack.getItem() instanceof BlockItem)
                    mStack.translate(0F, 0F, 0.0725F);
            }
            mStack.popPose();
        }

        // render scoop animation
        // TODO: displace scoop depending on rotation
        int scoopRotation = tile.getScoopRotation();
        IBakedModel scoop = mc.getModelManager().getModel(new ResourceLocation(CinderedTally.MOD_ID, "block/crucible_scoop"));
        mStack.pushPose();
        IVertexBuilder builder = buffer.getBuffer(RenderType.cutout());
        mStack.translate(0.5F, 0.1875F, 0.5F);
        mStack.mulPose(Vector3f.YP.rotationDegrees(scoopRotation));
        mc.getBlockRenderer().getModelRenderer().renderModel(tile.getLevel(), scoop, tile.getBlockState(), tile.getBlockPos(), mStack, builder, true, rand, 0L, combinedOverlay, EmptyModelData.INSTANCE);
        mStack.popPose();
    }
}
