package argent_matter.gcyr.common.gui;

import argent_matter.gcyr.common.entity.RocketEntity;
import argent_matter.gcyr.config.GCYRConfig;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Locale;

public class RocketLandingHUD implements IGuiOverlay {

    @Override
    public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
        if (gui.getMinecraft().options.hideGui || gui.getMinecraft().player == null) return;
        if (!(gui.getMinecraft().player.getVehicle() instanceof RocketEntity rocket)) return;
        if (!isLanding(rocket)) return;

        gui.setupOverlayRenderState(true, false);

        double verticalSpeed = Math.max(0.0D, -rocket.getDeltaMovement().y);
        double crashSpeed = rocket.getCrashSpeed();
        int speedColor = verticalSpeed >= crashSpeed ? ChatFormatting.RED.getColor() :
                verticalSpeed >= crashSpeed * 0.75D ? ChatFormatting.YELLOW.getColor() :
                        ChatFormatting.GREEN.getColor();
        int x = GCYRConfig.INSTANCE.client.oxygenBarX;
        int y = screenHeight - GCYRConfig.INSTANCE.client.oxygenBarY - 44;

        String formattedVerticalSpeed = String.format(Locale.ROOT, "%.2f", verticalSpeed);
        draw(graphics, gui, Component.translatable("hud.gcyr.rocket.descent", formattedVerticalSpeed), x, y,
                speedColor);
        draw(graphics, gui, Component.translatable("hud.gcyr.rocket.fuel", rocket.getFuelAmount(),
                rocket.getFuelCapacity()), x, y + 10, ChatFormatting.WHITE.getColor());
        draw(graphics, gui, Component.translatable("hud.gcyr.rocket.landing",
                Component.translatable(rocket.hasLandingModule() ? "hud.gcyr.rocket.auto" : "hud.gcyr.rocket.manual")),
                x, y + 20, ChatFormatting.WHITE.getColor());
        draw(graphics, gui, Component.translatable("hud.gcyr.rocket.explosions",
                Component.translatable(rocket.crashExplosionsEnabled() ? "hud.gcyr.rocket.enabled" :
                        "hud.gcyr.rocket.disabled")),
                x, y + 30, ChatFormatting.WHITE.getColor());
    }

    private static boolean isLanding(RocketEntity rocket) {
        return !rocket.isRemoved() && !rocket.onGround() &&
                (rocket.getDeltaMovement().y < 0.0D || rocket.hasLandingModule());
    }

    private static void draw(GuiGraphics graphics, ForgeGui gui, Component text, int x, int y, int color) {
        graphics.drawString(gui.getMinecraft().font, text, x, y, color, true);
    }
}
