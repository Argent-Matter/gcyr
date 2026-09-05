package argent_matter.gcyr.api.capability;

import argent_matter.gcyr.api.space.dyson.DysonSystemSavedData;
import argent_matter.gcyr.api.space.satellite.capability.SatelliteWorldSavedData;
import argent_matter.gcyr.api.space.station.StationWorldSavedData;

import net.minecraft.server.level.ServerLevel;

import org.jetbrains.annotations.Nullable;

public class GCYRCapabilityHelper {

    public static @Nullable ISpaceStationHolder getSpaceStations(@Nullable ServerLevel level) {
        return StationWorldSavedData.getOrCreate(level);
    }

    public static @Nullable ISatelliteHolder getSatellites(ServerLevel level) {
        return SatelliteWorldSavedData.getOrCreate(level);
    }

    public static @Nullable IDysonSystem getDysonSystem(ServerLevel level) {
        return DysonSystemSavedData.getOrCreate(level);
    }
}
