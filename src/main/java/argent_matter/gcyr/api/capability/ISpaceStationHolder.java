package argent_matter.gcyr.api.capability;

import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.api.space.station.SpaceStation;
import argent_matter.gcyr.util.Vec2i;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec2;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Set;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface ISpaceStationHolder {

    /**
     * @return all space stations of this capability (= level)
     */
    Int2ObjectMap<SpaceStation> getStations();

    /**
     * @param position the position from which distance is measured from
     * @return the closest satellite to this position, or null if none
     */
    int getClosestStationId(Vec2i position);

    /**
     *
     * @param id the station's id
     * @return the center of this station
     */
    @Nullable
    Vec2i getStationPos(int id);

    /**
     *
     * @param id the station's id
     * @return this station.
     */
    @Nullable
    SpaceStation getStation(@Nullable Integer id);

    @Nullable
    BlockPos getStationWorldPos(int id);

    /**
     *
     * @param position position that is searched around
     * @param range range that is searched
     * @return all satellites in area, sorted by distance (smallest first)
     */
    List<Integer> getStationsNearPos(Vec2i position, int range);

    default List<Integer> getStationsNearWorldPos(BlockPos pos, int range) {
        return getStationsNearPos(new Vec2i(pos.getX() / SpaceStation.BLOCK_MULTIPLIER, pos.getZ() / SpaceStation.BLOCK_MULTIPLIER), range / SpaceStation.BLOCK_MULTIPLIER);
    }

    /**
     * Allocates (& saves) a new station to this space station holder.
     * @param orbitPlanet the planet this new station will orbit.
     * @return a {@link Pair} containing 1. the new station's id, 2. the new station object.
     */
    @Nullable
    default Pair<Integer, SpaceStation> allocateStation(Planet orbitPlanet) {
        int id = getFreeStationId();
        Vec2i pos = getFreeStationPos(id);
        if (pos == Vec2i.MAX_NEGATIVE) return null;
        SpaceStation station = new SpaceStation(orbitPlanet, pos);
        return new Pair<>(id, station);
    }

    void addStation(int stationId, SpaceStation pos);

    void destroyStation(int id);

    int getFreeStationId();

    Vec2i getFreeStationPos(int stationId);
}
