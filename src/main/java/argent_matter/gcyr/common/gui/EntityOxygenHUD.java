package argent_matter.gcyr.common.gui;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.item.armor.SpaceSuitArmorItem;
import argent_matter.gcyr.config.GCYRConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;

public class EntityOxygenHUD implements LayeredDraw.Layer {
    public static final ResourceLocation GUI_TEXTURE = GCYR.id("textures/gui/oxygen_bar.png");

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (!Minecraft.getInstance().options.hideGui) {
            if (SpaceSuitArmorItem.hasFullSet(Minecraft.getInstance().player)) {
                int x = GCYRConfig.INSTANCE.client.oxygenBarX;
                int y = guiGraphics.guiHeight() - GCYRConfig.INSTANCE.client.oxygenBarY;
                guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, 64, 16, 64, 32);

                long oxygenAmount = SpaceSuitArmorItem.oxygenAmount(Minecraft.getInstance().player);
                long maxOxygen = SpaceSuitArmorItem.oxygenMax(Minecraft.getInstance().player);
                if (maxOxygen == 0) {
                    return;
                }
                int width = (int) (oxygenAmount * 64.0 / maxOxygen);
                guiGraphics.blit(GUI_TEXTURE, x, y, 0, 16, width, 16, 64, 32);
            }
        }

    }
}
