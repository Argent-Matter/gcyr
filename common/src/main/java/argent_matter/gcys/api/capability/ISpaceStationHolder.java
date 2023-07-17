package argent_matter.gcys.api.capability;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.phys.Vec2;

import javax.annotation.Nullable;
import java.util.List;

public interface ISpaceStationHolder {

    /**
     * @return all satellites of this capability (= level)
     */
    Int2ObjectMap<Vec2> getStations();

    /**
     * @param position the position from which distance is measured from
     * @return the closest satellite to this position, or null if none
     */
    int getClosestStationId(Vec2 position);

    /**
     *
     * @param id the station's id
     * @return the center of this station
     */
    Vec2 getStationPos(int id);

    /**
     *
     * @param position position that is searched around
     * @param range range that is searched
     * @return all satellites in area, sorted by distance (smallest first)
     */
    @Nullable
    List<Integer> getStationsNearPos(Vec2 position, int range);

    void addStation(int stationId, Vec2 pos);

    void destroyStation(int id);

    int getFreeStationId();

    Vec2 getFreeStationPos(int stationId);
}
