package argent_matter.gcyr.common.gui;

import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.Objects;
import java.util.function.Supplier;

/** A server-driven label whose text and style can change while the UI is open. */
public class RocketInfoLabelWidget extends LabelWidget {

    private final Supplier<Component> componentSupplier;
    private Component currentComponent;
    private final int centeredWidth;

    public RocketInfoLabelWidget(int xPosition, int yPosition, Supplier<Component> componentSupplier) {
        this(xPosition, yPosition, 0, componentSupplier);
    }

    public RocketInfoLabelWidget(int xPosition, int yPosition, int centeredWidth,
                                 Supplier<Component> componentSupplier) {
        super(xPosition, yPosition, componentSupplier.get());
        this.componentSupplier = componentSupplier;
        this.currentComponent = componentSupplier.get();
        this.centeredWidth = centeredWidth;
    }

    @Override
    public void detectAndSendChanges() {
        Component nextComponent = componentSupplier.get();
        if (!Objects.equals(currentComponent, nextComponent)) {
            currentComponent = nextComponent;
            setComponent(nextComponent);
            writeUpdateInfo(-2, buffer -> buffer.writeComponent(nextComponent));
        }
    }

    @Override
    public void drawInBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (centeredWidth <= 0) {
            super.drawInBackground(graphics, mouseX, mouseY, partialTicks);
            return;
        }
        var position = getPosition();
        int x = position.x + (centeredWidth - Minecraft.getInstance().font.width(currentComponent)) / 2;
        graphics.drawString(Minecraft.getInstance().font, currentComponent, x, position.y, -1, true);
    }
}
