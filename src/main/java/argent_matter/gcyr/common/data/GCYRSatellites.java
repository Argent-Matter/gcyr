package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.api.registries.GCYRRegistries;
import argent_matter.gcyr.api.space.satellite.Satellite;
import argent_matter.gcyr.api.space.satellite.SatelliteType;
import argent_matter.gcyr.common.satellite.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote GTSatellites
 */
public class GCYRSatellites {
    public static final DeferredRegister<SatelliteType<?>> SATELLITES = DeferredRegister.create(GCYRRegistries.SATELLITES, GCYR.MOD_ID);

    public static final Supplier<SatelliteType<EmptySatellite>> EMPTY = SATELLITES.register("empty", () -> new SatelliteType<>(EmptySatellite::new, EmptySatellite.CODEC));
    public static final Supplier<SatelliteType<GpsSatellite>> GPS = SATELLITES.register("gps", () ->  new SatelliteType<>(GpsSatellite::new, GpsSatellite.CODEC));
    public static final Supplier<SatelliteType<OreFinderSatellite>> ORE_FINDER = SATELLITES.register("ore_finder", () ->  new SatelliteType<>(OreFinderSatellite::new, OreFinderSatellite.CODEC));
    public static final Supplier<SatelliteType<LaserSatellite>> LASER = SATELLITES.register("laser", () ->  new SatelliteType<>(LaserSatellite::new, LaserSatellite.CODEC));
    public static final Supplier<SatelliteType<DysonSwarmSatellite>> DYSON_SWARM = SATELLITES.register("dyson_swarm", () ->  new SatelliteType<>(DysonSwarmSatellite::new, DysonSwarmSatellite.CODEC));

    public static void init(IEventBus bus) {
        SATELLITES.register(bus);

        GCYRRegistries.SATELLITES.freeze();
    }
}
