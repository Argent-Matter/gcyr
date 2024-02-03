package argent_matter.gcyr.util;

import argent_matter.gcyr.api.capability.GCyRCapabilityHelper;
import argent_matter.gcyr.api.capability.ISpaceStationHolder;
import argent_matter.gcyr.api.space.station.SpaceStation;
import argent_matter.gcyr.data.loader.PlanetData;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.border.WorldBorder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MixinHelpers {

    @SuppressWarnings("ConstantValue") // it can be null if this is loaded early, which Lithium does.
    public static WorldBorder modifySpaceStationBorder(WorldBorder original, @Nullable Entity entity) {
        if (entity != null && entity.level() instanceof ServerLevel serverLevel) {
            if (PlanetData.isOrbitLevel(serverLevel.dimension())) {
                // default to normal world border if somehow not on a space station dimension
                ISpaceStationHolder spaceStationHolder = GCyRCapabilityHelper.getSpaceStations(serverLevel);
                if (spaceStationHolder == null) return original;

                // get nearest space station
                List<Integer> stationIds = spaceStationHolder.getStationsNearWorldPos(entity.blockPosition(), SpaceStation.BLOCK_MULTIPLIER / 2);
                if (!stationIds.isEmpty()) {
                    int nearest = stationIds.get(0);
                    SpaceStation station = spaceStationHolder.getStation(nearest);
                    // get border of selected station, or fall back to normal border if invalid
                    WorldBorder border = station != null ? station.border() : original;

                    // sync new border to client
                    if (entity instanceof ServerPlayer player && player.connection != null) {
                        player.connection.send(new ClientboundSetBorderCenterPacket(border));
                        player.connection.send(new ClientboundSetBorderSizePacket(border));
                    }
                    return border;
                }
            } else {
                // reset client border if not on station
                if (entity instanceof ServerPlayer player && player.connection != null) {
                    player.connection.send(new ClientboundSetBorderCenterPacket(original));
                    player.connection.send(new ClientboundSetBorderSizePacket(original));
                }
            }

        }
        return original;

    }
}
