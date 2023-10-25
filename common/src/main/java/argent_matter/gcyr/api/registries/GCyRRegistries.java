package argent_matter.gcyr.api.registries;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.api.space.satellite.SatelliteType;
import com.gregtechceu.gtceu.api.registry.GTRegistry;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

public class GCyRRegistries {
    public static final GTRegistrate REGISTRATE = GTRegistrate.create(GCyR.MOD_ID);

    public static final GTRegistry.RL<SatelliteType<?>> SATELLITES = new GTRegistry.RL<>(GCyR.id("satellite"));
}
