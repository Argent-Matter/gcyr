package argent_matter.gcyr.api.registries;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.api.space.satellite.SatelliteType;
import com.gregtechceu.gtceu.api.registry.GTRegistry;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

public class GCYRRegistries {
    public static final GTRegistrate REGISTRATE = GTRegistrate.create(GCYR.MOD_ID);

    public static final GTRegistry.RL<SatelliteType<?>> SATELLITES = new GTRegistry.RL<>(GCYR.id("satellite"));
}
