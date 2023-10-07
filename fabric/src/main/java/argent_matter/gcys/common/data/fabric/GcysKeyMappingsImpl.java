package argent_matter.gcys.common.data.fabric;

import argent_matter.gcys.common.data.GcysKeyMappings;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class GcysKeyMappingsImpl {
    public static void initPlatform() {
        KeyBindingHelper.registerKeyBinding(GcysKeyMappings.START_ROCKET);
    }
}
