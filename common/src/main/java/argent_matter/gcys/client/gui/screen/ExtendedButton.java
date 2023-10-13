package argent_matter.gcys.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class ExtendedButton extends Button {

    @Getter
    private final int startY;

    private final float colorR, colorG, colorB;

    public ExtendedButton(int x, int y, int width, int height, float colorR, float colorG, float colorB, Component message, OnPress onPress) {
        this(x, y, width, height, colorR, colorG, colorB, message, onPress, NO_TOOLTIP);
    }

    public ExtendedButton(int x, int y, int width, int height, float colorR, float colorG, float colorB, Component message, OnPress onPress, OnTooltip onTooltip) {
        super(x, y, width, height, message, onPress, onTooltip);
        this.startY = y;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    // Override this for the colors
    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(colorR, colorG, colorB, this.alpha);
        int i = this.getYImage(this.isHoveredOrFocused());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(poseStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        this.blit(poseStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        this.renderBg(poseStack, minecraft, mouseX, mouseY);
        int j = this.active ? 0xFFFFFF : 0xA0A0A0;
        drawCenteredString(poseStack, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);

        if (this.isHoveredOrFocused()) {
            this.renderToolTip(poseStack, mouseX, mouseY);
        }
    }
}
