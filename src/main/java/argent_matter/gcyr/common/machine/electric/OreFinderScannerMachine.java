package argent_matter.gcyr.common.machine.electric;

import argent_matter.gcyr.api.space.satellite.capability.SatelliteWorldSavedData;
import argent_matter.gcyr.client.gui.widget.SatelliteScanWidget;
import argent_matter.gcyr.common.satellite.OreFinderSatellite;
import argent_matter.gcyr.util.Vec2i;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TieredEnergyMachine;
import com.gregtechceu.gtceu.api.machine.feature.IUIMachine;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup;
import com.lowdragmc.lowdraglib.gui.widget.DraggableScrollableWidgetGroup;
import com.lowdragmc.lowdraglib.gui.widget.SwitchWidget;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class OreFinderScannerMachine extends TieredEnergyMachine implements IUIMachine {
    public static final int MACHINE_RANGE = 6;
    public static final long ENERGY_USAGE = 128;

    private final int range;

    public OreFinderScannerMachine(IMachineBlockEntity holder, int tier, Object... args) {
        super(holder, tier, args);
        this.range = MACHINE_RANGE;
    }

    public void scanOres(BlockState[][][] storage, int chunkX, int chunkZ) {
        // don't scan if we don't have enough power.
        if (this.energyContainer.removeEnergy(ENERGY_USAGE * tier) < ENERGY_USAGE * tier) {
            return;
        }
        if (this.getLevel() instanceof ServerLevel serverLevel) {
            Vec2i centerPos = new Vec2i(chunkX * 16, chunkZ * 16);
            var satellites = SatelliteWorldSavedData.getOrCreate(serverLevel).getSatellitesNearPos(centerPos, range * 16).stream().filter(OreFinderSatellite.class::isInstance).map(OreFinderSatellite.class::cast).toList();
            for (OreFinderSatellite satellite : satellites) {
                satellite.scan(storage, serverLevel);
            }
        }
    }

    @Override
    public ModularUI createUI(Player entityPlayer) {
        var map = new SatelliteScanWidget(4, 4, 332 - 8, 200 - 8, this.range, 1, this);
        var draggableGroup = new DraggableScrollableWidgetGroup(4, 4, 332 - 8, 200 - 8);
        draggableGroup.addWidget(map);
        draggableGroup.setUseScissor(false);
        return new ModularUI(332, 200, this, entityPlayer)
                .background(GuiTextures.BACKGROUND)
                .widget(draggableGroup)
                .widget(new SwitchWidget(-20, 4, 18, 18, (cd, pressed) -> map.setDarkMode(pressed))
                        .setSupplier(map::isDarkMode)
                        .setTexture(
                                new GuiTextureGroup(GuiTextures.BUTTON, GuiTextures.PROGRESS_BAR_SOLAR_STEAM.get(true).copy().getSubTexture(0, 0.5, 1, 0.5).scale(0.8f)),
                                new GuiTextureGroup(GuiTextures.BUTTON, GuiTextures.PROGRESS_BAR_SOLAR_STEAM.get(true).copy().getSubTexture(0, 0, 1, 0.5).scale(0.8f))));
    }

}
