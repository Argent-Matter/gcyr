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

        gui.setupOverlayRenderState(true, false);
        if (rocket.getStartTimer() < 200 && rocket.getEntityData().get(RocketEntity.ROCKET_STARTED)) {
            renderCountdown(gui, graphics, rocket, screenWidth, screenHeight);
        }

        if (!isLanding(rocket)) {
            int x = GCYRConfig.INSTANCE.client.oxygenBarX;
            int y = screenHeight - GCYRConfig.INSTANCE.client.oxygenBarY - 64;
            var destination = rocket.getDestination() != null ? rocket.getDestination() :
                    rocket.getConfiguredDestination();
            drawStats(graphics, gui, rocket, x, y);
            draw(graphics, gui, fuelText(rocket), x, y + 30, ChatFormatting.WHITE.getColor());
            draw(graphics, gui, coloredValue("hud.gcyr.rocket.to_orbit", format(rocket.getLaunchFuelCost()),
                    rocket.getLaunchFuelColor()), x, y + 40, ChatFormatting.WHITE.getColor());
            if (destination != null) {
                draw(graphics, gui,
                        coloredValue("hud.gcyr.rocket.to_dest", format(rocket.getTransferFuelCost(destination)),
                                rocket.getTransferFuelColor(destination)),
                        x, y + 50, ChatFormatting.WHITE.getColor());
            }
            return;
        }

        double verticalSpeed = Math.max(0.0D, -rocket.getDeltaMovement().y);
        double crashSpeed = rocket.getCrashSpeed();
        int speedColor = verticalSpeed >= crashSpeed ? ChatFormatting.RED.getColor() :
                verticalSpeed >= crashSpeed * 0.75D ? ChatFormatting.YELLOW.getColor() :
                        ChatFormatting.GREEN.getColor();
        int x = GCYRConfig.INSTANCE.client.oxygenBarX;
        int y = screenHeight - GCYRConfig.INSTANCE.client.oxygenBarY - 74;

        String formattedVerticalSpeed = String.format(Locale.ROOT, "%.2f", verticalSpeed);
        drawStats(graphics, gui, rocket, x, y);
        draw(graphics, gui, coloredValue("hud.gcyr.rocket.descent", formattedVerticalSpeed, speedColor), x, y + 30,
                ChatFormatting.WHITE.getColor());
        draw(graphics, gui, fuelText(rocket), x, y + 40, ChatFormatting.WHITE.getColor());
        draw(graphics, gui, Component.translatable("hud.gcyr.rocket.landing",
                Component.translatable(rocket.hasLandingModule() ? "hud.gcyr.rocket.auto" : "hud.gcyr.rocket.manual")),
                x, y + 50, ChatFormatting.WHITE.getColor());
        draw(graphics, gui, Component.translatable("hud.gcyr.rocket.explosions",
                Component.translatable(rocket.crashExplosionsEnabled() ? "hud.gcyr.rocket.enabled" :
                        "hud.gcyr.rocket.disabled")),
                x, y + 60, ChatFormatting.WHITE.getColor());
    }

    private static void drawStats(GuiGraphics graphics, ForgeGui gui, RocketEntity rocket, int x, int y) {
        draw(graphics, gui, Component.translatable("hud.gcyr.rocket.weight", format(rocket.getWeight())), x, y,
                ChatFormatting.WHITE.getColor());
        draw(graphics, gui, Component.translatable("hud.gcyr.rocket.thrust", format(rocket.getEffectiveThrust())),
                x, y + 10, ChatFormatting.WHITE.getColor());
        int netColor = rocket.getRocketSpeed() > 0.0D ? ChatFormatting.GREEN.getColor() : ChatFormatting.RED.getColor();
        draw(graphics, gui, coloredValue("hud.gcyr.rocket.net", format(rocket.getRocketSpeed(), 2), netColor), x,
                y + 20, ChatFormatting.WHITE.getColor());
    }

    private static void renderCountdown(ForgeGui gui, GuiGraphics graphics, RocketEntity rocket, int screenWidth,
                                        int screenHeight) {
        int seconds = Math.max(0, (200 - rocket.getStartTimer()) / 20);
        String text = Integer.toString(seconds);
        float scale = 3.0F;
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, scale);
        int x = (int) ((screenWidth / scale - gui.getMinecraft().font.width(text)) / 2.0F);
        int y = (int) (screenHeight / (2.0F * scale) - gui.getMinecraft().font.lineHeight / 2.0F);
        graphics.drawString(gui.getMinecraft().font, text, x, y, ChatFormatting.WHITE.getColor(), true);
        graphics.pose().popPose();
    }

    private static Component coloredValue(String key, String value, int color) {
        return Component.translatable(key, Component.literal(value).withStyle(style -> style.withColor(color)));
    }

    private static Component fuelText(RocketEntity rocket) {
        double percentage = rocket.getFuelCapacity() <= 0 ? 0.0D :
                100.0D * rocket.getFuelAmount() / rocket.getFuelCapacity();
        return Component.translatable("hud.gcyr.rocket.fuel", String.format(Locale.ROOT, "%.0f", percentage));
    }

    private static boolean isLanding(RocketEntity rocket) {
        return !rocket.isRemoved() && !rocket.onGround() &&
                (rocket.getDeltaMovement().y < 0.0D || rocket.hasLandingModule());
    }

    private static void draw(GuiGraphics graphics, ForgeGui gui, Component text, int x, int y, int color) {
        graphics.drawString(gui.getMinecraft().font, text, x, y, color, true);
    }

    private static String format(double value) {
        return format(value, 0);
    }

    private static String format(double value, int decimals) {
        return Double.isFinite(value) ? String.format(Locale.ROOT, "%." + decimals + "f", value) : "-";
    }
}
