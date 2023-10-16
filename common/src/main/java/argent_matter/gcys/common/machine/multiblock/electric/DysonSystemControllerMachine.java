package argent_matter.gcys.common.machine.multiblock.electric;

import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class DysonSystemControllerMachine extends WorkableElectricMultiblockMachine {
    public DysonSystemControllerMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    @Override
    public boolean isWorkingEnabled() {
        return super.isWorkingEnabled() && (this.getLevel().isClientSide || GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel()).isDysonSphereActive());
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (!isFormed) return;


        textList.add(ComponentPanelWidget.withButton(Component.translatable("gui.gcys.dyson_sphere.start").withStyle(ChatFormatting.GREEN), "dbg_start_sphere"));
        textList.add(ComponentPanelWidget.withButton(Component.translatable("gui.gcys.dyson_sphere.stop").withStyle(ChatFormatting.GREEN), "dbg_delete_sphere"));
    }

    @Override
    public void handleDisplayClick(String componentData, ClickData clickData) {
        if (!clickData.isRemote) {
            if (componentData.equals("dbg_start_sphere")) {
                GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel()).addDysonSphere(this.getPos());
            } else if (componentData.equals("dbg_delete_sphere")) {
                GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel()).disableDysonSphere(this.getPos());
            }
        }
    }
}
