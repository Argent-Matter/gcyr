package argent_matter.gcys.fabric;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class GcysFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        GregicalitySpace.init();


        // register satellite ticking
        ServerTickEvents.START_WORLD_TICK.register((serverLevel) -> {
            if (!serverLevel.dimensionType().hasCeiling()) {
                var sat = GcysCapabilityHelper.getSatellites(serverLevel);
                if (sat != null) sat.tickSatellites();
            }
        });
    }
}