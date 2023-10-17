package argent_matter.gcys.api.space.dyson;

import com.gregtechceu.gtceu.api.gui.widget.EnumSelectorWidget;
import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;

public record DysonSphere(
        BlockPos controllerPos) {

    public enum Component implements StringRepresentable, EnumSelectorWidget.SelectableEnum {
        FRAME("frame", "gcys.dyson_component.frame"),
        CELL("cell", "gcys.dyson_component.cell"),
        PANEL("panel", "gcys.dyson_component.panel"),
        PORT("port", "gcys.dyson_component.port")
        ;

        private final String id, tooltipKey;

        Component(String id, String tooltipKey) {
            this.id = id;
            this.tooltipKey = tooltipKey;
        }

        @Override
        public String getSerializedName() {
            return id;
        }

        @Override
        public String getTooltip() {
            return tooltipKey;
        }

        @Override
        public IGuiTexture getIcon() {
            return null;
        }
    }
}
