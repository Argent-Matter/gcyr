package screret.gcys.common.config;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;

@Config(id = "gcys")
public class GcysConfigHolder {
    public static GcysConfigHolder INSTANCE;
    @Configurable
    public WorldGenConfigs worldgen = new WorldGenConfigs();
    @Configurable
    public MachineConfigs machines = new MachineConfigs();

    public static void init() {
        INSTANCE = Configuration.registerConfig(GcysConfigHolder.class, ConfigFormats.yaml()).getConfigInstance();
    }

    public static class WorldGenConfigs {
        @Configurable
        @Configurable.Comment({"Should all Stone Types drop unique Ore Item Blocks?", "Default: false (meaning only Stone, Netherrack, and Endstone"})
        public boolean allUniqueStoneTypes;
    }

    public static class MachineConfigs {
        @Configurable
        @Configurable.Comment({"Time in ticks between laser satellites mining a block"})
        public int laserSatelliteMiningTickStep;
        @Configurable
        @Configurable.Comment({"Laser satellite damage per tick step"})
        public float laserSatelliteDamagePerTickStep;
    }
}
