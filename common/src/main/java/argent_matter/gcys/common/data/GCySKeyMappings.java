package argent_matter.gcys.common.data;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.KeyMapping;

public class GCySKeyMappings {
    public static KeyMapping START_ROCKET;

    public static void init() {
        START_ROCKET = new KeyMapping("key.startRocket", InputConstants.KEY_J, "key.categories.gcys");
        initPlatform();
    }

    @ExpectPlatform
    public static void initPlatform() {
        throw new AssertionError();
    }
}
