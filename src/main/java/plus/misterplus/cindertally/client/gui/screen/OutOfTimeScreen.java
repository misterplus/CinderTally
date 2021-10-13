package plus.misterplus.cindertally.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

/**
 * A death screen that shows when players ran out of time in singleplayer.
 */
@OnlyIn(Dist.CLIENT)
public class OutOfTimeScreen extends Screen {

    private int delayTicker;
    private final ITextComponent causeOfDeath;
    private ITextComponent deathScore;

    public OutOfTimeScreen() {
        super(new TranslationTextComponent("deathScreen.title.out_of_time"));
        this.causeOfDeath = new TranslationTextComponent("death.cindertally.out_of_time");
    }

    protected void init() {
        this.delayTicker = 0;
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 72, 200, 20, new TranslationTextComponent("deathScreen.spectate"), (p_213021_1_) -> {
            this.minecraft.setScreen((Screen)null);
        }));
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 96, 200, 20, new TranslationTextComponent("deathScreen.titleScreen"), (p_213020_1_) -> {
            this.exitToTitleScreen();
        }));

        for(Widget widget : this.buttons) {
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

    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.fillGradient(p_230430_1_, 0, 0, this.width, this.height, 1615855616, -1602211792);
        RenderSystem.pushMatrix();
        RenderSystem.scalef(2.0F, 2.0F, 2.0F);
        drawCenteredString(p_230430_1_, this.font, this.title, this.width / 2 / 2, 30, 16777215);
        RenderSystem.popMatrix();
        drawCenteredString(p_230430_1_, this.font, this.causeOfDeath, this.width / 2, 85, 16777215);

        drawCenteredString(p_230430_1_, this.font, this.deathScore, this.width / 2, 100, 16777215);
        if (p_230430_3_ > 85 && p_230430_3_ < 85 + 9) {
            Style style = this.getClickedComponentStyleAt(p_230430_2_);
            this.renderComponentHoverEffect(p_230430_1_, style, p_230430_2_, p_230430_3_);
        }

        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
    }

    @Nullable
    private Style getClickedComponentStyleAt(int p_238623_1_) {
        int i = this.minecraft.font.width(this.causeOfDeath);
        int j = this.width / 2 - i / 2;
        int k = this.width / 2 + i / 2;
        return p_238623_1_ >= j && p_238623_1_ <= k ? this.minecraft.font.getSplitter().componentStyleAtWidth(this.causeOfDeath, p_238623_1_ - j) : null;
    }

    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        if (p_231044_3_ > 85.0D && p_231044_3_ < (double)(85 + 9)) {
            Style style = this.getClickedComponentStyleAt((int)p_231044_1_);
            if (style != null && style.getClickEvent() != null && style.getClickEvent().getAction() == ClickEvent.Action.OPEN_URL) {
                this.handleComponentClicked(style);
                return false;
            }
        }

        return super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void tick() {
        super.tick();
        ++this.delayTicker;
        if (this.delayTicker == 20) {
            for(Widget widget : this.buttons) {
                widget.active = true;
            }
        }
    }
}
