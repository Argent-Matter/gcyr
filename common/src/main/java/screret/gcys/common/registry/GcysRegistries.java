package screret.gcys.common.registry;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.registry.GTRegistry;
import screret.gcys.api.space.satellite.SatelliteType;

public class GcysRegistries {

    public static final GTRegistry.RL<SatelliteType<?>> SATELLITES = new GTRegistry.RL<>(GTCEu.id("satellite"));

}
