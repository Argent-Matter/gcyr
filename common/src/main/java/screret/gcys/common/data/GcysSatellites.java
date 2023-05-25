package screret.gcys.common.data;

import com.gregtechceu.gtceu.GTCEu;
import net.minecraft.resources.ResourceLocation;
import screret.gcys.api.space.satellite.Satellite;
import screret.gcys.api.space.satellite.SatelliteType;
import screret.gcys.common.registry.GcysRegistries;
import screret.gcys.common.satellite.DysonSwarmSatellite;
import screret.gcys.common.satellite.EmptySatellite;
import screret.gcys.common.satellite.GpsSatellite;
import screret.gcys.common.satellite.LaserSatellite;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote GTSatellites
 */
public class GcysSatellites {

    public static final SatelliteType<EmptySatellite> EMPTY = register("empty", new SatelliteType<>(EmptySatellite::new));
    public static final SatelliteType<GpsSatellite> GPS = register("gps", new SatelliteType<>(GpsSatellite::new));
    public static final SatelliteType<LaserSatellite> LASER = register("laser", new SatelliteType<>(LaserSatellite::new));
    public static final SatelliteType<DysonSwarmSatellite> DYSON_SWARM = register("dyson_swarm", new SatelliteType<>(DysonSwarmSatellite::new));


    public static  <T extends Satellite> SatelliteType<T> register(String name, SatelliteType<T> satellite) {
        ResourceLocation id = GTCEu.id(name);
        GcysRegistries.SATELLITES.register(id, satellite);
        return satellite;
    }

    public static void init() {

    }
}
