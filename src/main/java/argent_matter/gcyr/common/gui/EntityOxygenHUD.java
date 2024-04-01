package argent_matter.gcyr.common.gui;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.item.armor.SpaceSuitArmorItem;
import argent_matter.gcyr.config.GCyRConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class EntityOxygenHUD implements IGuiOverlay {
    public static final ResourceLocation GUI_TEXTURE = GCyR.id("textures/gui/oxygen_bar.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
        if (!gui.getMinecraft().options.hideGui) {
            if (SpaceSuitArmorItem.hasFullSet(gui.getMinecraft().player)) {
                gui.setupOverlayRenderState(true, false);
                int x = GCyRConfig.INSTANCE.client.oxygenBarX;
                int y = screenHeight - GCyRConfig.INSTANCE.client.oxygenBarY;
                graphics.blit(GUI_TEXTURE, x, y, 0, 0, 64, 16, 64, 32);

                long oxygenAmount = SpaceSuitArmorItem.oxygenAmount(gui.getMinecraft().player);
                long maxOxygen = SpaceSuitArmorItem.oxygenMax(gui.getMinecraft().player);
                if (maxOxygen == 0) {
                    return;
                }
                int width = (int) (oxygenAmount * 64.0 / maxOxygen);
                graphics.blit(GUI_TEXTURE, x, y, 0, 16, width, 16, 64, 32);
            }
        }
    }
}
