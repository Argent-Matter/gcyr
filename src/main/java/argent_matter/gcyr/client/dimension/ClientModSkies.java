package argent_matter.gcyr.client.dimension;

import argent_matter.gcyr.GCyRClient;
import argent_matter.gcyr.api.space.planet.PlanetSkyRenderer;
import argent_matter.gcyr.client.dimension.renderer.DimensionEffects;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class ClientModSkies {
    public static final Map<ResourceLocation, DimensionSpecialEffects> DIMENSION_SPECIAL_EFFECTS = new HashMap<>();

    public static void register() {
        for (PlanetSkyRenderer skyRenderer : GCyRClient.skyRenderers) {
            registerDimensionEffects(skyRenderer.dimension(), new DimensionEffects(skyRenderer));
        }
    }

    public static void registerDimensionEffects(ResourceKey<Level> id, DimensionEffects effects) {
        DIMENSION_SPECIAL_EFFECTS.put(id.location(), effects);
    }
}