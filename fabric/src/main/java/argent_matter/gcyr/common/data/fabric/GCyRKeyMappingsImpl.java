package argent_matter.gcyr.common.data.fabric;

import argent_matter.gcyr.common.data.GCyRKeyMappings;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class GCyRKeyMappingsImpl {
    public static void initPlatform() {
        KeyBindingHelper.registerKeyBinding(GCyRKeyMappings.START_ROCKET);
    }
}
