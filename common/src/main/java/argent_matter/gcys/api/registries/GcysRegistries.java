package argent_matter.gcys.api.registries;

import argent_matter.gcys.GCyS;
import argent_matter.gcys.api.space.satellite.SatelliteType;
import com.gregtechceu.gtceu.api.registry.GTRegistry;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

public class GcysRegistries {
    public static final GTRegistrate REGISTRATE = GTRegistrate.create(GCyS.MOD_ID);

    public static final GTRegistry.RL<SatelliteType<?>> SATELLITES = new GTRegistry.RL<>(GCyS.id("satellite"));
}
