package argent_matter.gcys.common.satellite;

import argent_matter.gcys.api.space.satellite.Satellite;
import argent_matter.gcys.api.space.satellite.SatelliteType;
import argent_matter.gcys.api.space.satellite.data.SatelliteData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote EmptySatellite
 */
public class EmptySatellite extends Satellite {
    public EmptySatellite(SatelliteType<?> type, SatelliteData data, ResourceKey<DimensionType> level) {
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

    @Nullable
    @Override
    public CompoundTag serializeExtraData() {
        return null;
    }

    @Override
    public void deserializeExtraData(Tag tag, Level level) {

    }
}
