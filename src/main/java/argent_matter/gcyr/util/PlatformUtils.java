package argent_matter.gcyr.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.DimensionTransition;

import java.util.List;
import java.util.Objects;

public class PlatformUtils {

    public static Entity changeDimension(final Entity originalEntity, final ServerLevel newDim) {
        List<Entity> passengers = originalEntity.getPassengers();
        Entity dimensionChanged = originalEntity.changeDimension(new DimensionTransition(newDim,
                originalEntity.position(),
                originalEntity.getDeltaMovement(),
                originalEntity.getYRot(),
                originalEntity.getXRot(),
                DimensionTransition.DO_NOTHING));
        final Entity entity = Objects.requireNonNullElse(dimensionChanged, originalEntity);
        passengers.forEach(passenger -> {
            Entity newPassenger = PlatformUtils.changeDimension(passenger, newDim);
            Objects.requireNonNullElse(newPassenger, passenger).startRiding(entity);
        });
        return entity;
    }
}
