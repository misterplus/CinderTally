package plus.misterplus.cindertally.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import plus.misterplus.cindertally.common.inventory.container.CinderTallyContainer;

public class CinderTallyScreen extends ContainerScreen<CinderTallyContainer> {

    public CinderTallyScreen(CinderTallyContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float mouseX, int mouseY, int partialTicks) {
//        super.render(matrixStack, mouseX, mouseY, partialTicks);
//        renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }
}
