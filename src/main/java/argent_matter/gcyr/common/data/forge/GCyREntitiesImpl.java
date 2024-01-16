package argent_matter.gcyr.common.data.forge;

import argent_matter.gcyr.common.entity.RocketEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.EntityBuilder;

import java.util.function.Consumer;

public class GCyREntitiesImpl {
    public static Consumer<EntityBuilder<RocketEntity, Registrate>> properties(int clientTrackRange, int updateFrequency, boolean syncVelocity, boolean immuneToFire, float width, float height) {
        return builder -> builder.properties(b -> {
            if (immuneToFire) {
                b.fireImmune();
            }
            b.clientTrackingRange(clientTrackRange).updateInterval(updateFrequency).setShouldReceiveVelocityUpdates(syncVelocity).sized(width, height);
        });
    }
}
