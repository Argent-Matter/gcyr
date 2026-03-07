package argent_matter.gcyr.client.gui.screen;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;

import java.util.List;

public class ExtendedButton extends Button {

    @Getter
    private final int startY;

    private final OnTooltip tooltip;

    private final float colorR, colorG, colorB;

    public ExtendedButton(int x, int y, int width, int height, float colorR, float colorG, float colorB,
                          Component message, OnPress onPress) {
        this(x, y, width, height, colorR, colorG, colorB, message, onPress, null);
    }

    public ExtendedButton(int x, int y, int width, int height, float colorR, float colorG, float colorB,
                          Component message, OnPress onPress, OnTooltip tooltip) {
        super(x, y, width, height, message, onPress, Button.DEFAULT_NARRATION);
        this.startY = y;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.tooltip = tooltip;
    }

    // Override this for the colors
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.setColor(colorR, colorG, colorB, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        guiGraphics.blitNineSliced(WIDGETS_LOCATION, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4,
                200, 20, 0, this.getTextureY());
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.active ? 0xFFFFFF : 0xA0A0A0;
        this.renderString(guiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    public void updateTooltip() {
        boolean hovered = this.isHovered || this.isFocused() && Minecraft.getInstance().getLastInputType().isKeyboard();
        if (hovered != this.wasHoveredOrFocused) {
            if (hovered) {
                this.hoverOrFocusedStartTime = Util.getMillis();
            }

            this.wasHoveredOrFocused = hovered;
        }

        if (hovered && Util.getMillis() - this.hoverOrFocusedStartTime > (long) this.tooltipMsDelay) {
            Screen screen = Minecraft.getInstance().screen;
            if (screen != null && this.tooltip != null) {
                screen.setTooltipForNextRenderPass(this.tooltip.onTooltip(this), this.createTooltipPositioner(),
                        this.isFocused());
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnTooltip {

        List<FormattedCharSequence> onTooltip(Button button);
    }
}
