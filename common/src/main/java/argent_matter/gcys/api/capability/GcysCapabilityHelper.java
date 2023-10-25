package argent_matter.gcys.api.capability;

import argent_matter.gcys.api.space.dyson.DysonSystemSavedData;
import argent_matter.gcys.api.space.satellite.capability.SatelliteWorldSavedData;
import argent_matter.gcys.api.space.station.StationWorldSavedData;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;

public class GcysCapabilityHelper {
    @Nullable
    public static ISpaceStationHolder getSpaceStations(@Nullable ServerLevel level) {
        return StationWorldSavedData.getOrCreate(level);
    }

    @Nullable
    public static ISatelliteHolder getSatellites(ServerLevel level) {
        return SatelliteWorldSavedData.getOrCreate(level);
    }

    @Nullable
    public static IDysonSystem getDysonSystem(ServerLevel level) {
        return DysonSystemSavedData.getOrCreate(level);
    }
}
