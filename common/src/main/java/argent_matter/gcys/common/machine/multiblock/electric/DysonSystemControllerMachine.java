package argent_matter.gcys.common.machine.multiblock.electric;

import argent_matter.gcys.GregicalitySpaceClient;
import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.api.capability.IDysonSystem;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DysonSystemControllerMachine extends WorkableElectricMultiblockMachine {
    public DysonSystemControllerMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    @Override
    protected @Nullable GTRecipe getRealRecipe(GTRecipe recipe) {
        if (this.getLevel().dimensionType().hasCeiling()) return null;
        if (recipe.getInputContents(ItemRecipeCapability.CAP).isEmpty()) {
            IDysonSystem system = GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel());
            if (system == null) return null;
        }
        return super.getRealRecipe(recipe);
    }

    @Override
    public void afterWorking() {
        super.afterWorking();
        GTRecipe recipe = this.getRecipeLogic().getLastRecipe();
        if (recipe == null || this.isRemote()) return;
        if (recipe.getInputContents(ItemRecipeCapability.CAP).isEmpty()) return; // assume the recipe is a dyson launch or repair if it has item inputs.

        IDysonSystem system = GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel());
        if (system == null || system.isDysonSphereActive()) return;
        system.addDysonSphere(this.getPos());
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (!isFormed) return;

        Component button;
        if (isRemote()) {
            if (GregicalitySpaceClient.isDysonSphereActive) {
                button = ComponentPanelWidget.withButton(Component.translatable("gui.gcys.dyson_sphere.stop").withStyle(ChatFormatting.RED), "dbg_delete_sphere");
            } else {
                button = ComponentPanelWidget.withButton(Component.translatable("gui.gcys.dyson_sphere.start").withStyle(ChatFormatting.GREEN), "dbg_start_sphere");
            }
        } else {
            IDysonSystem system = GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel());
            if (system != null && system.isDysonSphereActive()) {
                button = ComponentPanelWidget.withButton(Component.translatable("gui.gcys.dyson_sphere.stop").withStyle(ChatFormatting.RED), "dbg_delete_sphere");
            } else {
                button = ComponentPanelWidget.withButton(Component.translatable("gui.gcys.dyson_sphere.start").withStyle(ChatFormatting.GREEN), "dbg_start_sphere");
            }
        }

        textList.add(button);
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
