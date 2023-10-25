package argent_matter.gcyr.common.data.fabric;

import argent_matter.gcyr.common.entity.RocketEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.EntityBuilder;
import net.minecraft.world.entity.EntityDimensions;

import java.util.function.Consumer;

public class GCyREntitiesImpl {
    public static Consumer<EntityBuilder<RocketEntity, Registrate>> properties(int clientTrackRange, int updateFrequency, boolean syncVelocity, boolean immuneToFire, float width, float height) {
        return builder -> builder.properties(b -> {
            if (immuneToFire) {
                b.fireImmune();
            }
            b.trackRangeChunks(clientTrackRange).trackedUpdateRate(updateFrequency).forceTrackedVelocityUpdates(syncVelocity).dimensions(EntityDimensions.scalable(width, height));
        });
    }
}
