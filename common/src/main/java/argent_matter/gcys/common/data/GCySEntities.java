package argent_matter.gcys.common.data;

import argent_matter.gcys.client.renderer.entity.RocketEntityRenderer;
import argent_matter.gcys.common.entity.RocketEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.EntityBuilder;
import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Consumer;

import static argent_matter.gcys.api.registries.GcysRegistries.REGISTRATE;


public class GCySEntities {
    public static EntityEntry<RocketEntity> ROCKET = register("rocket", RocketEntity::new, MobCategory.MISC, () -> RocketEntityRenderer::new,
            GCySEntities.properties(8, 3, true, true, 1, 1));
    public static <T extends Entity> EntityEntry<T> register(String name,
                                                             EntityType.EntityFactory<T> factory,
                                                             MobCategory classification,
                                                             NonNullSupplier<NonNullFunction<EntityRendererProvider.Context, EntityRenderer<? super T>>> renderer,
                                                             Consumer<EntityBuilder<T, Registrate>> builderConsumer) {
        var builder = REGISTRATE.entity(name, factory, classification);
        builder.renderer(renderer);
        builderConsumer.accept(builder);
        return builder.register();
    }

    @ExpectPlatform
    public static Consumer<EntityBuilder<RocketEntity, Registrate>> properties(int clientTrackRange, int updateFrequency, boolean syncVelocity, boolean immuneToFire, float width, float height) {
        throw new AssertionError();
    }

    public static void init() {

    }
}
