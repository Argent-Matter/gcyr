package screret.gcys.fabric;

import screret.gcys.GregicalitySpace;
import net.fabricmc.api.ModInitializer;

public class GregicalitySpaceFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        GregicalitySpace.init();
    }
}