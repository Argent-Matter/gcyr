package argent_matter.gcyr.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.DimensionTransition;

import java.util.Objects;

public class PlatformUtils {

    public static Entity changeDimension(final Entity originalEntity, final ServerLevel newDim) {
        Entity dimensionChanged = originalEntity.changeDimension(new DimensionTransition(newDim,
                originalEntity.position(),
                originalEntity.getDeltaMovement(),
                originalEntity.getYRot(),
                originalEntity.getXRot(),
                DimensionTransition.DO_NOTHING));
        return Objects.requireNonNullElse(dimensionChanged, originalEntity);
    }
}
