package screret.gcys.api.capability;

import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;
import screret.gcys.api.space.satellite.Satellite;
import screret.gcys.api.space.satellite.SatelliteType;

import java.util.List;

public interface ISatelliteHolder {

    /**
     * @return all satellites of this capability (= level)
     */
    List<Satellite> getSatellites();

    /**
     * ticks all satellites this capability has
     */
    void tickSatellites();

    /**
     * @param type satellite type
     * @return satellites of type {@code type}
     */
    @SuppressWarnings("unchecked")
    default <T extends Satellite> List<T> getSatellitesOfType(SatelliteType<T> type) {
        return (List<T>) getSatellites().stream().filter(obj -> obj.getType() == type).toList();
    }

    /**
     * @param type satellite type
     * @return amount of satellites of type {@code type}
     */
    default <T extends Satellite> int getSatelliteCount(SatelliteType<T> type) {
        return getSatellites().stream().filter(obj -> obj.getType() == type).mapToInt(obj -> 1).sum();
    }

    /**
     *
     * @param position the position from which distance is measured from
     * @return the closest satellite to this position, or null if none
     */
    @Nullable
    Satellite getClosestSatellite(Vec2 position);

    /**
     *
     * @param position position that is searched around
     * @param range range that is searched
     * @return all satellites in area, sorted by distance (smallest first)
     */
    @Nullable
    List<Satellite> getSatellitesNearPos(Vec2 position, int range);

    void addSatellite(@Nullable Satellite satellite);

    void destroySatellite(Satellite satellite);
}
