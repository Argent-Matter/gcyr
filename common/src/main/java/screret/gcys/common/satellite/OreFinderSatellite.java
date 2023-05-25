package screret.gcys.common.satellite;

import com.gregtechceu.gtceu.api.space.satellite.Satellite;
import com.gregtechceu.gtceu.api.space.satellite.SatelliteType;
import com.gregtechceu.gtceu.api.space.satellite.data.SatelliteData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class OreFinderSatellite extends Satellite {
    public OreFinderSatellite(SatelliteType<?> type, SatelliteData data, ResourceKey<Level> level) {
        super(type, data, level);
    }

    @Override
    public void tickSatellite(Level level) {
        if (isNonWorking()) return;

    }

    @Override
    public boolean runSatelliteFunction(Level level) {
        return false;
    }

    @Override
    public CompoundTag serializeExtraData() {
        return null;
    }

    @Override
    public void deserializeExtraData(Tag tag, Level level) {

    }
}
