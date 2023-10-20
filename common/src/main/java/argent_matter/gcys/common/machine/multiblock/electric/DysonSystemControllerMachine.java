package argent_matter.gcys.common.machine.multiblock.electric;

import argent_matter.gcys.GCySClient;
import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.api.capability.IDysonSystem;
import argent_matter.gcys.config.GcysConfig;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.data.damagesource.DamageSources;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
            IDysonSystem system = GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel(), this.getPos());
            if (system == null) return null;
        }
        return super.getRealRecipe(recipe);
    }

    @Override
    public void onWorking() {
        super.onWorking();
        Level level = this.getLevel();
        // THE RECEIVER IS A DEADLY LAZER
        Direction frontFacing = this.getFrontFacing();
        Direction backFacing = frontFacing.getOpposite();
        Direction rightFacing = frontFacing.getClockWise();
        BlockPos pos = this.getPos().mutable().move(backFacing, 4).move(rightFacing, 1).move(0, 7 + 256 /*pre offset up by half distance*/, 0).immutable();
        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(Vec3.atCenterOf(pos), 3, 512, 3), EntitySelector.LIVING_ENTITY_STILL_ALIVE)) {
            entity.hurt(DamageSources.getElectricDamage(), GcysConfig.INSTANCE.machine.dysonControllerBeamDamage);
        }
    }

    @Override
    public void afterWorking() {
        super.afterWorking();
        GTRecipe recipe = this.getRecipeLogic().getLastRecipe();
        if (recipe == null || this.isRemote()) return;
        if (recipe.getInputContents(ItemRecipeCapability.CAP).isEmpty()) return; // assume the recipe is a dyson launch or repair if it has item inputs.

        IDysonSystem system = GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel(), this.getPos());
        if (system == null || system.isDysonSphereActive()) return;
        system.addDysonSphere(this.getPos());
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (!isFormed) return;

        // TODO disable/remove this once release comes
        Component button;
        if (isRemote()) {
            if (GCySClient.isDysonSphereActive) {
                button = ComponentPanelWidget.withButton(Component.translatable("gui.gcys.dyson_sphere.stop").withStyle(ChatFormatting.RED), "dbg_delete_sphere");
            } else {
                button = ComponentPanelWidget.withButton(Component.translatable("gui.gcys.dyson_sphere.start").withStyle(ChatFormatting.GREEN), "dbg_start_sphere");
            }
        } else {
            IDysonSystem system = GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel(), this.getPos());
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
                GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel(), this.getPos()).addDysonSphere(this.getPos());
            } else if (componentData.equals("dbg_delete_sphere")) {
                GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel(), this.getPos()).disableDysonSphere(this.getPos());
            }
        }
    }
}
