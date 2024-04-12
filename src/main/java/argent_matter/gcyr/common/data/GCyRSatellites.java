package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.api.registries.GCyRRegistries;
import argent_matter.gcyr.api.space.satellite.Satellite;
import argent_matter.gcyr.api.space.satellite.SatelliteType;
import argent_matter.gcyr.common.satellite.*;
import net.minecraft.resources.ResourceLocation;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote GTSatellites
 */
public class GCyRSatellites {

    public static final SatelliteType<EmptySatellite> EMPTY = register("empty", new SatelliteType<>(EmptySatellite::new, EmptySatellite.CODEC));
    public static final SatelliteType<GpsSatellite> GPS = register("gps", new SatelliteType<>(GpsSatellite::new, GpsSatellite.CODEC));
    public static final SatelliteType<OreFinderSatellite> ORE_FINDER = register("ore_finder", new SatelliteType<>(OreFinderSatellite::new, OreFinderSatellite.CODEC));
    public static final SatelliteType<LaserSatellite> LASER = register("laser", new SatelliteType<>(LaserSatellite::new, LaserSatellite.CODEC));
    public static final SatelliteType<DysonSwarmSatellite> DYSON_SWARM = register("dyson_swarm", new SatelliteType<>(DysonSwarmSatellite::new, DysonSwarmSatellite.CODEC));


    public static  <T extends Satellite> SatelliteType<T> register(String name, SatelliteType<T> satellite) {
        ResourceLocation id = GCyR.id(name);
        GCyRRegistries.SATELLITES.register(id, satellite);
        return satellite;
    }

    public static void init() {

    }
}
