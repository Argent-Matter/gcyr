package argent_matter.gcys.common.data.fabric;

import argent_matter.gcys.common.data.GCySKeyMappings;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class GCySKeyMappingsImpl {
    public static void initPlatform() {
        KeyBindingHelper.registerKeyBinding(GCySKeyMappings.START_ROCKET);
    }
}
