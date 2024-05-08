package argent_matter.gcyr.common.machine.electric;

import argent_matter.gcyr.api.space.satellite.capability.SatelliteWorldSavedData;
import argent_matter.gcyr.common.satellite.OreFinderSatellite;
import argent_matter.gcyr.util.Vec2i;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TieredEnergyMachine;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OreFinderScannerMachine extends TieredEnergyMachine {
    public static final int MACHINE_RANGE_MULTIPLIER = 16;
    private final Map<BlockState, String> BLOCK_CACHE = new HashMap<>();

    private final int range;

    public OreFinderScannerMachine(IMachineBlockEntity holder, int tier, Object... args) {
        super(holder, tier, args);
        this.range = this.tier * MACHINE_RANGE_MULTIPLIER;
    }

    public void scanOres(BlockState[][][] storage) {
        if (this.getLevel() instanceof ServerLevel serverLevel) {
            BlockPos myPos = this.getPos();
            storage = new BlockState[OreFinderSatellite.CELL_SIZE][OreFinderSatellite.CELL_SIZE][0];
            var satellites = SatelliteWorldSavedData.getOrCreate(serverLevel).getSatellitesNearPos(new Vec2i(myPos.getX(), myPos.getZ()), range).stream().filter(OreFinderSatellite.class::isInstance).map(OreFinderSatellite.class::cast).collect(Collectors.toList());
            for (OreFinderSatellite satellite : satellites) {
                satellite.scan(storage, serverLevel);
            }
        }
    }

    public int getItemColor(BlockState state) {
        var itemName = BLOCK_CACHE.computeIfAbsent(state, blockState -> {
            var name = BuiltInRegistries.BLOCK.getKey(blockState.getBlock()).toString();
            var entry = ChemicalHelper.getUnificationEntry(blockState.getBlock());
            if (entry != null && entry.material != null) {
                name = "material_" + entry.material;
            }
            return name;
        });
        if (itemName.startsWith("material_")) {
            var mat = GTMaterials.get(itemName.substring(9));
            if (mat != null) {
                return mat.getMaterialRGB();
            }
        }
        return BuiltInRegistries.BLOCK.get(new ResourceLocation(itemName)).defaultMapColor().col;
    }

}
