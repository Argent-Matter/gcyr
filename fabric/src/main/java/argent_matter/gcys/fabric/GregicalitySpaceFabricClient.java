package argent_matter.gcys.fabric;

import argent_matter.gcys.common.data.GcysKeyMappings;
import net.fabricmc.api.ClientModInitializer;

public class GregicalitySpaceFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GcysKeyMappings.init();
    }
}
