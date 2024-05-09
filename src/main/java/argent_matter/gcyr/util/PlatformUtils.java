package argent_matter.gcyr.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.ITeleporter;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PlatformUtils {

    public static Entity changeDimension(final Entity originalEntity, final ServerLevel newDim) {
        List<Entity> passengers = originalEntity.getPassengers();
        Entity dimensionChanged = originalEntity.changeDimension(newDim, new ITeleporter() {
            @Override
            public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
                return false;
            }
        });
        final Entity entity = Objects.requireNonNullElse(dimensionChanged, originalEntity);
        passengers.forEach(passenger -> {
            Entity newPassenger = PlatformUtils.changeDimension(passenger, newDim);
            Objects.requireNonNullElse(newPassenger, passenger).startRiding(entity);
        });
        return entity;
    }
}
