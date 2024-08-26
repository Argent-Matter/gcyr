package argent_matter.gcyr.config;

import argent_matter.gcyr.GCyR;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;

@Config(id = GCyR.MOD_ID)
public class GCyRConfig {
    public static GCyRConfig INSTANCE;
    public static void init() {
        INSTANCE = Configuration.registerConfig(GCyRConfig.class, ConfigFormats.yaml()).getConfigInstance();
    }

    @Configurable
    public SatelliteConfigs satellites = new SatelliteConfigs();
    @Configurable
    public ServerConfigs server = new ServerConfigs();
    @Configurable
    public MachineConfigs machine = new MachineConfigs();
    @Configurable
    public RocketConfigs rocket = new RocketConfigs();
    @Configurable
    public ClientConfigs client = new ClientConfigs();

    public static class SatelliteConfigs {
        @Configurable
        @Configurable.Comment({"Damage of the laser satellite to entities per tick.", "Default: 2.0"})
        public float laserSatelliteDamagePerTickStep = 2.0F;
        @Configurable
        @Configurable.Comment({"Time between laser satellite mining attempts.", "Default: 2"})
        public int laserSatelliteMiningTickStep = 2;
    }

    public static class ServerConfigs {
        @Configurable
        @Configurable.Comment({"Wether the oxygen mechanics are enabled.", "Default: true"})
        public boolean enableOxygen = true;
        @Configurable
        @Configurable.Comment({"Damage to entities per tick without oxygen.", "Default: 1.0"})
        public float oxygenDamage = 1.0F;
        @Configurable
        @Configurable.Comment({"Damage to overheated entities per tick", "Default: 2.0"})
        public float heatDamage = 2.0F;
        @Configurable
        @Configurable.Comment({"Damage to freezing entities per tick.", "Default: 2.0"})
        public float freezeDamage = 2.0F;
        @Configurable
        @Configurable.Comment({"Maximum distance the oxygen spreader can spread oxygen in.", "Default: 128"})
        public int maxOxygenatedBlockChecks = 128;
        @Configurable
        @Configurable.Comment({"The temperature in space.", "Default: -270.0"})
        public float spaceTemperature = -270.0F;
        @Configurable
        @Configurable.Comment({"The Maximum size of a single space station.",
                "DO NOT CHANGE AFTER LOADING THE SPACE STATION WORLD, THAT WILL BREAK THE WORLD BORDER",
                "Default: 512"})
        public int spaceStationMaxSize = 512;
    }


    public static class MachineConfigs {
        @Configurable
        @Configurable.Comment({"Damage caused by standing in an active dyson system controller's beam. (per tick)", "Default: 5.0"})
        public float dysonControllerBeamDamage = 5.0f;

    }

    public static class RocketConfigs {
        @Configurable
        @Configurable.Comment({"How much fuel is required to travel to a moon or back?", "note: this is platform-specific.", "Default: 8 buckets."})
        public long moonFuelAmount = 8 * FluidHelper.getBucket();
        @Configurable
        @Configurable.Comment({"How much fuel is required to travel to a planet in the same solar system?", "note: this is platform-specific.", "Default: 14 buckets."})
        public long solarSystemFuelAmount = 14 * FluidHelper.getBucket();
        @Configurable
        @Configurable.Comment({"How much fuel is required to travel to a planet in the same galaxy?", "note: this is platform-specific.", "Default: 26 buckets."})
        public long galaxyFuelAmount = 26 * FluidHelper.getBucket();
        @Configurable
        @Configurable.Comment({"How much fuel is required to travel to a planet anywhere?", "note: this is platform-specific.", "Default: 48 buckets."})
        public long anywhereFuelAmount = 48 * FluidHelper.getBucket();
    }

    public static class ClientConfigs {
        @Configurable
        @Configurable.Comment({"Offset of the oxygen bar from the left side of the screen.", "Default: 32"})
        public int oxygenBarX = 32;
        @Configurable
        @Configurable.Comment({"Offset of the oxygen bar from the bottom of the screen.", "Default: 32"})
        public int oxygenBarY = 32;
    }
}
