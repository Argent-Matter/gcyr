package argent_matter.gcys.config;

import argent_matter.gcys.GregicalitySpace;
import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;

@Config(id = GregicalitySpace.MOD_ID)
public class GcysConfig {
    public static GcysConfig INSTANCE;
    public static void init() {
        INSTANCE = Configuration.registerConfig(GcysConfig.class, ConfigFormats.yaml()).getConfigInstance();
    }

    @Configurable
    public SatelliteConfigs satellites = new SatelliteConfigs();
    @Configurable
    public ServerConfigs server = new ServerConfigs();

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
        @Configurable.Comment({"Maximum skylight in a dimension whose sun is covered by a dyson sphere.", "Default: 3"})
        public int maxSphereSkylight = 3;
    }

}
