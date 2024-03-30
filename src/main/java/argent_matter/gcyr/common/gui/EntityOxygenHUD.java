package argent_matter.gcyr.common.gui;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.item.armor.SpaceSuitArmorItem;
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
                int minX = 32;
                int minY = screenHeight - 32;
                graphics.blit(GUI_TEXTURE, minX, minY, 0, 0, 64, 16, 64, 32);

                long oxygenAmount = SpaceSuitArmorItem.oxygenAmount(gui.getMinecraft().player);
                long maxOxygen = SpaceSuitArmorItem.oxygenMax(gui.getMinecraft().player);
                if (maxOxygen == 0) {
                    return;
                }
                int width = (int) (oxygenAmount * 64.0 / maxOxygen);
                graphics.blit(GUI_TEXTURE, minX, minY, 0, 16, width, 16, 64, 32);
            }
        }
    }
}
