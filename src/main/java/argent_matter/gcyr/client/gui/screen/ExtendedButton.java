package argent_matter.gcyr.client.gui.screen;

import argent_matter.gcyr.mixin.AbstractWidgetAccessor;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetTooltipHolder;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.BelowOrAboveWidgetTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.MenuTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.time.Duration;
import java.util.List;

public class ExtendedButton extends Button {

    @Getter
    private final int startY;

    private final OnTooltip tooltip;

    private final float colorR, colorG, colorB;

    public ExtendedButton(int x, int y, int width, int height, float colorR, float colorG, float colorB, Component message, OnPress onPress) {
        this(x, y, width, height, colorR, colorG, colorB, message, onPress, null);
    }

    public ExtendedButton(int x, int y, int width, int height, float colorR, float colorG, float colorB, Component message, OnPress onPress, OnTooltip tooltip) {
        super(x, y, width, height, message, onPress, Button.DEFAULT_NARRATION);
        this.startY = y;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.tooltip = tooltip;
        ((AbstractWidgetAccessor) this).setTooltip(new ExtendedWidgetTooltipHolder());
    }

    // Override this for the colors
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.setColor(colorR, colorG, colorB, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        guiGraphics.blitSprite(SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.active ? 0xFFFFFF : 0xA0A0A0;
        this.renderString(guiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @OnlyIn(Dist.CLIENT)
    public interface OnTooltip {
        List<FormattedCharSequence> onTooltip(Button button);
    }

    public class ExtendedWidgetTooltipHolder extends WidgetTooltipHolder {

        @Setter
        private Duration delay = Duration.ZERO;
        private long displayStartTime;
        private boolean wasDisplayed;

        @Override
        public void refreshTooltipForNextRenderPass(boolean hovering, boolean focused, ScreenRectangle screenRectangle) {
            boolean hovered = hovering || focused && Minecraft.getInstance().getLastInputType().isKeyboard();
            if (hovered != wasDisplayed) {
                if (hovered) {
                    this.displayStartTime = Util.getMillis();
                }

                this.wasDisplayed = hovered;
            }

            if (hovered && Util.getMillis() - this.displayStartTime > this.delay.toMillis()) {
                Screen screen = Minecraft.getInstance().screen;
                if (screen != null && ExtendedButton.this.tooltip != null) {
                    screen.setTooltipForNextRenderPass(ExtendedButton.this.tooltip.onTooltip(ExtendedButton.this),
                            this.createTooltipPositioner(screenRectangle, hovering, focused),
                            focused);
                }
            }

        }

        private ClientTooltipPositioner createTooltipPositioner(ScreenRectangle screenRectangle, boolean hovering, boolean focused) {
            return !hovering && focused && Minecraft.getInstance().getLastInputType().isKeyboard()
                    ? new BelowOrAboveWidgetTooltipPositioner(screenRectangle)
                    : new MenuTooltipPositioner(screenRectangle);
        }
    }
}
