package argent_matter.gcyr.common.data;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.KeyMapping;

public class GCyRKeyMappings {
    public static KeyMapping START_ROCKET;

    public static void init() {
        START_ROCKET = new KeyMapping("key.startRocket", InputConstants.KEY_J, "key.categories.gcyr");
        initPlatform();
    }

    @ExpectPlatform
    public static void initPlatform() {
        throw new AssertionError();
    }
}
