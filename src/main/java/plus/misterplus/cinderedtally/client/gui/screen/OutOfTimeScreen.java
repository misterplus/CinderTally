package plus.misterplus.cinderedtally.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;

import javax.annotation.Nullable;

/**
 * A death screen that shows when players ran out of time in singleplayer.
 */
public class OutOfTimeScreen extends Screen {

    private final ITextComponent causeOfDeath;
    private int delayTicker;
    private ITextComponent deathScore;

    public OutOfTimeScreen() {
        super(new TranslationTextComponent("deathScreen.title.out_of_time"));
        this.causeOfDeath = new TranslationTextComponent("death.cinderedtally.out_of_time");
    }

    protected void init() {
        this.delayTicker = 0;
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 72, 200, 20, new TranslationTextComponent("deathScreen.spectate"), (p_213021_1_) -> this.minecraft.setScreen(null)));
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 96, 200, 20, new TranslationTextComponent("deathScreen.titleScreen"), (p_213020_1_) -> {
            this.exitToTitleScreen();
        }));

        for (Widget widget : this.buttons) {
            widget.active = false;
        }

        this.deathScore = (new TranslationTextComponent("deathScreen.score")).append(": ").append((new StringTextComponent(Integer.toString(this.minecraft.player.getScore()))).withStyle(TextFormatting.YELLOW));
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    private void exitToTitleScreen() {
        if (this.minecraft.level != null) {
            this.minecraft.level.disconnect();
        }

        this.minecraft.clearLevel(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
        this.minecraft.setScreen(new MainMenuScreen());
    }

    public void render(MatrixStack mStack, int mouseX, int mouseY, float partialTicks) {
        this.fillGradient(mStack, 0, 0, this.width, this.height, 1615855616, -1602211792);
        RenderSystem.pushMatrix();
        RenderSystem.scalef(2.0F, 2.0F, 2.0F);
        drawCenteredString(mStack, this.font, this.title, this.width / 2 / 2, 30, 16777215);
        RenderSystem.popMatrix();
        drawCenteredString(mStack, this.font, this.causeOfDeath, this.width / 2, 85, 16777215);

        drawCenteredString(mStack, this.font, this.deathScore, this.width / 2, 100, 16777215);
        if (mouseY > 85 && mouseY < 85 + 9) {
            Style style = this.getClickedComponentStyleAt(mouseX);
            this.renderComponentHoverEffect(mStack, style, mouseX, mouseY);
        }

        super.render(mStack, mouseX, mouseY, partialTicks);
    }

    @Nullable
    private Style getClickedComponentStyleAt(int mouseX) {
        int i = this.minecraft.font.width(this.causeOfDeath);
        int j = this.width / 2 - i / 2;
        int k = this.width / 2 + i / 2;
        return mouseX >= j && mouseX <= k ? this.minecraft.font.getSplitter().componentStyleAtWidth(this.causeOfDeath, mouseX - j) : null;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (mouseY > 85.0D && mouseY < (double) (85 + 9)) {
            Style style = this.getClickedComponentStyleAt((int) mouseX);
            if (style != null && style.getClickEvent() != null && style.getClickEvent().getAction() == ClickEvent.Action.OPEN_URL) {
                this.handleComponentClicked(style);
                return false;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void tick() {
        super.tick();
        ++this.delayTicker;
        if (this.delayTicker == 20) {
            for (Widget widget : this.buttons) {
                widget.active = true;
            }
        }
    }
}
