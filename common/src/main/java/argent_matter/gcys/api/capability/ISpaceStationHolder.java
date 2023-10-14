package argent_matter.gcys.api.capability;

import argent_matter.gcys.api.space.planet.Planet;
import argent_matter.gcys.api.space.station.SpaceStation;
import argent_matter.gcys.util.Vec2i;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec2;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface ISpaceStationHolder {

    /**
     * @return all satellites of this capability (= level)
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
    Vec2i getStationPos(int id);

    BlockPos getStationWorldPos(int id);

    /**
     *
     * @param position position that is searched around
     * @param range range that is searched
     * @return all satellites in area, sorted by distance (smallest first)
     */
    @Nullable
    List<Integer> getStationsNearPos(Vec2i position, int range);

    @Nullable
    default List<Integer> getStationsNearWorldPos(BlockPos pos, int range) {
        return getStationsNearPos(new Vec2i(pos.getX() / 16, pos.getZ() / 16), range / 16);
    }

    @Nullable
    default Pair<Integer, SpaceStation> allocateStation(Planet orbitPlanet) {
        int id = getFreeStationId();
        Vec2i pos = getFreeStationPos(id);
        if (pos == Vec2i.MAX_NEGATIVE) return null;
        return new Pair<>(id, new SpaceStation(orbitPlanet, pos));
    }

    void addStation(int stationId, SpaceStation pos);

    void destroyStation(int id);

    int getFreeStationId();

    Vec2i getFreeStationPos(int stationId);
}
