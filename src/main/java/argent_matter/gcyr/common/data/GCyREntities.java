package argent_matter.gcyr.common.data;

import argent_matter.gcyr.client.renderer.entity.RocketEntityRenderer;
import argent_matter.gcyr.common.entity.RocketEntity;
import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.world.entity.MobCategory;

import static argent_matter.gcyr.api.registries.GCyRRegistries.REGISTRATE;


public class GCyREntities {
    public static EntityEntry<RocketEntity> ROCKET = REGISTRATE.entity("rocket", RocketEntity::new, MobCategory.MISC)
            .renderer(() -> RocketEntityRenderer::new)
            .properties(b -> b.fireImmune().clientTrackingRange(8).updateInterval(3).setShouldReceiveVelocityUpdates(true).sized(1, 1))
            .register();

    public static void init() {

    }
}
