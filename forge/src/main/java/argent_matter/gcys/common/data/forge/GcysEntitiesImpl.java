package argent_matter.gcys.common.data.forge;

import argent_matter.gcys.common.entity.RocketEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.EntityBuilder;
import net.minecraft.world.entity.EntityDimensions;

import java.util.function.Consumer;

public class GcysEntitiesImpl {
    public static Consumer<EntityBuilder<RocketEntity, Registrate>> properties(int clientTrackRange, int updateFrequency, boolean syncVelocity, boolean immuneToFire, float width, float height) {
        return builder -> builder.properties(b -> {
            if (immuneToFire) {
                b.fireImmune();
            }
            b.clientTrackingRange(clientTrackRange).updateInterval(updateFrequency).setShouldReceiveVelocityUpdates(syncVelocity).sized(width, height);
        });
    }
}
