package argent_matter.gcys.common.data;

import argent_matter.gcys.GCyS;
import argent_matter.gcys.api.registries.GcysRegistries;
import argent_matter.gcys.api.space.satellite.Satellite;
import argent_matter.gcys.api.space.satellite.SatelliteType;
import argent_matter.gcys.common.satellite.DysonSwarmSatellite;
import argent_matter.gcys.common.satellite.EmptySatellite;
import argent_matter.gcys.common.satellite.GpsSatellite;
import argent_matter.gcys.common.satellite.LaserSatellite;
import net.minecraft.resources.ResourceLocation;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote GTSatellites
 */
public class GCySSatellites {

    public static final SatelliteType<EmptySatellite> EMPTY = register("empty", new SatelliteType<>(EmptySatellite::new, EmptySatellite.CODEC));
    public static final SatelliteType<GpsSatellite> GPS = register("gps", new SatelliteType<>(GpsSatellite::new, GpsSatellite.CODEC));
    public static final SatelliteType<LaserSatellite> LASER = register("laser", new SatelliteType<>(LaserSatellite::new, LaserSatellite.CODEC));
    public static final SatelliteType<DysonSwarmSatellite> DYSON_SWARM = register("dyson_swarm", new SatelliteType<>(DysonSwarmSatellite::new, DysonSwarmSatellite.CODEC));


    public static  <T extends Satellite> SatelliteType<T> register(String name, SatelliteType<T> satellite) {
        ResourceLocation id = GCyS.id(name);
        GcysRegistries.SATELLITES.register(id, satellite);
        return satellite;
    }

    public static void init() {

    }
}
