package argent_matter.gcys.common.machine.electric;

import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.api.gui.widget.GcysGuiTextures;
import argent_matter.gcys.api.space.satellite.Satellite;
import argent_matter.gcys.util.Vec2i;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.UITemplate;
import com.gregtechceu.gtceu.api.gui.widget.ToggleButtonWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TieredEnergyMachine;
import com.gregtechceu.gtceu.api.machine.feature.IUIMachine;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;
import com.lowdragmc.lowdraglib.gui.widget.DraggableScrollableWidgetGroup;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

import java.util.ArrayList;
import java.util.List;

public class SatelliteJammerMachine extends TieredEnergyMachine implements IControllable, IUIMachine {
    public static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(SatelliteJammerMachine.class);
    public static final int MACHINE_RANGE_MULTIPLIER = 24;

    @Persisted
    @Getter
    @Setter
    private boolean isWorkingEnabled;
    private final int range;
    @Getter
    @DescSynced
    private int lastJammedCount;
    @Getter
    @DescSynced
    private final List<Satellite> lastJammed = new ArrayList<>();

    public SatelliteJammerMachine(IMachineBlockEntity holder, int tier, Object... args) {
        super(holder, tier, args);
        this.range = this.tier * MACHINE_RANGE_MULTIPLIER;
    }

    @Override
    public int tintColor(int index) {
        if (index == 2) {
            return GTValues.VC[getTier()];
        }
        return super.tintColor(index);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (!this.getLevel().dimensionType().hasCeiling()) subscribeServerTick(this::jamSatellites);
        else this.setWorkingEnabled(false);
    }

    @Override
    public void onUnload() {
        super.onUnload();
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    protected void jamSatellites() {
        if (!this.getLevel().isClientSide) {
            BlockPos myPos = this.getPos();
            List<Satellite> toJam = GcysCapabilityHelper.getSatellites((ServerLevel) this.getLevel()).getSatellitesNearPos(new Vec2i(myPos.getX(), myPos.getZ()), range);

            if (toJam.size() > 0) {
                List<Satellite> copy = new ArrayList<>(lastJammed);
                copy.removeAll(toJam);
                copy.forEach(satellite -> satellite.setJammed(false));
                toJam.removeAll(lastJammed);
                if (!this.isWorkingEnabled) {
                    return;
                }

                lastJammed.clear();
                lastJammedCount = toJam.size();
                for (Satellite satellite : toJam) {
                    satellite.setJammed(true);
                    lastJammed.add(satellite);
                }
            }
        }

    }

    private void addDisplayText(List<Component> textList) {
        if (!isWorkingEnabled()) {
            textList.add(Component.translatable("gtceu.multiblock.work_paused"));
        } else {
            textList.add(Component.translatable("gtceu.multiblock.running"));

            for (Satellite sat : lastJammed.subList(0, lastJammedCount >= 10 ? 10 : lastJammedCount)) {
                textList.add(Component.translatable("gcys.machine.satellite_jammer.jammed", Component.translatable(sat.getType().toLangString())).append(Component.translatable("gcys.machine.satellite_jammer.position", vec2ToString(sat.getData().locationInWorld())))
                        .withStyle(ChatFormatting.GREEN));
            }
        }
    }

    private String vec2ToString(Vec2i vector) {
        return "x=" + vector.x() + ",z=" + vector.y();
    }

    @Override
    public ModularUI createUI(Player entityPlayer) {
        var screen = new DraggableScrollableWidgetGroup(7, 4, 162, 121).setBackground(GuiTextures.DISPLAY);
        screen.addWidget(new LabelWidget(4, 5, self().getBlockState().getBlock().getDescriptionId()));
        screen.addWidget(new ComponentPanelWidget(4, 17, this::addDisplayText)
                .setMaxWidthLimit(156));

        return new ModularUI(176, 216, this, entityPlayer)
                .background(GuiTextures.BACKGROUND)
                .widget(screen)
                .widget(new ToggleButtonWidget(7, 113, 18, 18,
                        GcysGuiTextures.BUTTON_ENABLED, this::isWorkingEnabled, this::setWorkingEnabled)
                        .setShouldUseBaseBackground()
                        .setTooltipText("behaviour.soft_hammer"))
                .widget(UITemplate.bindPlayerInventory(entityPlayer.getInventory(), GuiTextures.SLOT, 7, 84, true));
    }
}
