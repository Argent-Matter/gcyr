package argent_matter.gcyr.client.dimension;

import argent_matter.gcyr.GCyRClient;
import argent_matter.gcyr.api.space.planet.PlanetSkyRenderer;
import argent_matter.gcyr.client.dimension.renderer.DimensionEffects;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class ClientModSkies {

    public static void register() {
        for (PlanetSkyRenderer skyRenderer : GCyRClient.skyRenderers) {
            registerDimensionEffects(skyRenderer.dimension(), new DimensionEffects(skyRenderer));
        }
    }

    @ExpectPlatform
    public static void registerDimensionEffects(ResourceKey<Level> id, DimensionEffects effects) {
        throw new AssertionError();
    }
}