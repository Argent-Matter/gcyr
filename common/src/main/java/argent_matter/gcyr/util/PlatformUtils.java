package argent_matter.gcyr.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class PlatformUtils {

    @ExpectPlatform
    public static Entity changeDimension(Entity entity, ServerLevel newDim) {
        throw new AssertionError();
    }
}
