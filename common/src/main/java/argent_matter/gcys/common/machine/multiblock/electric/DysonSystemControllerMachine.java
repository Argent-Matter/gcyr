package argent_matter.gcys.common.machine.multiblock.electric;

import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.api.capability.IDysonSystem;
import argent_matter.gcys.common.data.GCySParticles;
import argent_matter.gcys.config.GcysConfig;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.data.GTDamageTypes;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
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
    public void onStructureInvalid() {
        super.onStructureInvalid();
        if (!isRemote()) {
            IDysonSystem system = GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel(), this.getPos());
            if (system == null || !system.isDysonSphereActive()) return;
            system.activeDysonSphere().setControllerPos(null);
        }
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        if (!isRemote()) {
            IDysonSystem system = GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel(), this.getPos());
            if (system == null) return;
            if (system.isDysonSphereActive() && system.activeDysonSphere().getControllerPos() == null) {
                system.activeDysonSphere().setControllerPos(this.getPos());
            } else if (!system.isDysonSphereActive()) {
                system.addDysonSphere(this.getPos());
            }
        }
    }

    @Override
    protected @Nullable GTRecipe getRealRecipe(GTRecipe recipe) {
        if (this.getLevel().dimensionType().hasCeiling()) return null;
        if (recipe.data.contains("gcys:repair_dyson_sphere")) {
            IDysonSystem system = GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel(), this.getPos());
            if (system != null && system.isDysonSphereActive() && (!system.activeDysonSphere().isNeedsMaintenance() || !this.getPos().equals(system.activeDysonSphere().getControllerPos()))) return null;
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
            entity.hurt(GTDamageTypes.ELECTRIC.source(level), GcysConfig.INSTANCE.machine.dysonControllerBeamDamage);
        }
    }

    @Override
    public void afterWorking() {
        super.afterWorking();
        GTRecipe recipe = this.getRecipeLogic().getLastRecipe();
        if (recipe == null || this.isRemote()) return;
        if (recipe.getInputContents(ItemRecipeCapability.CAP).isEmpty()) return; // assume the recipe is a dyson launch or repair if it has item inputs.

        IDysonSystem system = GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel(), this.getPos());
        if (system == null) return;
        if (!system.isDysonSphereActive() && recipe.data.contains("gcys:launch_dyson_sphere")) {
            system.addDysonSphere(this.getPos());
        } else if (system.isDysonSphereActive() && recipe.data.contains("gcys:repair_dyson_sphere") && system.activeDysonSphere().getControllerPos().equals(this.getPos())) {
            system.activeDysonSphere().fixMaintenance();
        }
    }

    @Override
    public void animateTick(RandomSource random) {
        if (this.isActive() && this.getOffsetTimer() % 20 == 0) {
            var level = this.getLevel();

            Direction frontFacing = this.getFrontFacing();
            Direction backFacing = frontFacing.getOpposite();
            Direction rightFacing = frontFacing.getClockWise();
            BlockPos pos = this.getPos().mutable().move(backFacing, 4).move(rightFacing, 1).move(0, 9, 0).immutable();

            double x = pos.getX() + 0.5;
            double z = pos.getZ() + 0.5;
            for (int y = pos.getY(); y < 512; y += 4) {
                level.addAlwaysVisibleParticle(GCySParticles.DYSON_BEAM, true, x, y, z, 0, 0, 0);
            }
        }
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (!isFormed) return;

        if (!isRemote()) {
            IDysonSystem system = GcysCapabilityHelper.getDysonSystem((ServerLevel) this.getLevel(), this.getPos());
            if (system == null) return;

            if (system.isDysonSphereActive()) {
                if (system.activeDysonSphere().isNeedsMaintenance() && !system.activeDysonSphere().isCollapsed()) {
                    textList.add(Component.translatable("menu.gcys.dyson_sphere.needs_maintenance").withStyle(this.getOffsetTimer() % 10 >= 5 ? ChatFormatting.RED : ChatFormatting.DARK_RED));
                    textList.add(Component.translatable("menu.gcys.dyson_sphere.time_since_needed_maintenance", system.activeDysonSphere().getTimeNeededMaintenance()));
                    textList.add(Component.translatable("menu.gcys.dyson_sphere.implosion_chance", system.activeDysonSphere().getCollapseChance() * 100.0f));
                } else if (system.activeDysonSphere().isCollapsed()) {
                    textList.add(Component.translatable("menu.gcys.dyson_sphere.collapsed"));
                }
            }

            // TODO disable/remove this once release comes
            Component button;
            if (system.isDysonSphereActive()) {
                button = ComponentPanelWidget.withButton(Component.translatable("menu.gcys.dyson_sphere.stop"), "dbg_delete_sphere").copy().withStyle(ChatFormatting.RED);
            } else {
                button = ComponentPanelWidget.withButton(Component.translatable("menu.gcys.dyson_sphere.start"), "dbg_start_sphere").copy().withStyle(ChatFormatting.GREEN);
            }
            textList.add(button);
        }

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
