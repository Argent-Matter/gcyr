package argent_matter.gcyr.data.loader;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.GCYRClient;
import argent_matter.gcyr.api.space.planet.Galaxy;
import argent_matter.gcyr.api.space.planet.PlanetRing;
import argent_matter.gcyr.api.space.planet.PlanetSkyRenderer;
import argent_matter.gcyr.api.space.planet.SolarSystem;
import argent_matter.gcyr.client.dimension.ClientModSkies;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.GsonHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@EventBusSubscriber(modid = GCYR.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class PlanetResources implements ResourceManagerReloadListener {
    public static final PlanetResources INSTANCE = new PlanetResources();

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().setLenient().create();

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        List<PlanetSkyRenderer> skyRenderers = new ArrayList<>();
        Map<ResourceLocation, ShaderInstance> skyShaders = new HashMap<>();
        List<SolarSystem> solarSystems = new ArrayList<>();
        List<PlanetRing> planetRings = new ArrayList<>();
        List<Galaxy> galaxies = new ArrayList<>();

        // Sky Renderers
        for (ResourceLocation id : manager.listResources("gcyr/planet_assets/sky_renderers", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getResourceStack(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        PlanetSkyRenderer renderer = PlanetSkyRenderer.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow();
                        if (renderer.skyShaderLocation().isPresent()) {
                            skyShaders.put(renderer.skyShaderLocation().get(), null);
                        }
                        skyRenderers.add(renderer);
                    }
                }
            } catch (Exception e) {
                GCYR.LOGGER.error("Failed to load Gregicality Rocketry sky rendering assets from: \"{}\"", id.toString(), e);
            }
        }

        // Solar Systems
        for (ResourceLocation id : manager.listResources("gcyr/planet_assets/solar_systems", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getResourceStack(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        solarSystems.add(SolarSystem.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow());
                    }
                }
            } catch (Exception e) {
                GCYR.LOGGER.error("Failed to load Gregicality Rocketry solar system assets from: \"{}\"", id.toString(), e);
            }
        }

        for (ResourceLocation id : manager.listResources("gcyr/planet_assets/planet_rings", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getResourceStack(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        planetRings.add(PlanetRing.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow());
                    }
                }
            } catch (Exception e) {
                GCYR.LOGGER.error("Failed to load Gregicality Rocketry planet ring assets from: \"{}\"", id.toString(), e);
            }
        }

        // Galaxies
        for (ResourceLocation id : manager.listResources("gcyr/planet_assets/galaxies", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getResourceStack(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        galaxies.add(Galaxy.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow());
                    }
                }
            } catch (Exception e) {
                GCYR.LOGGER.error("Failed to load Gregicality Rocketry galaxy assets from: \"{}\"", id.toString(), e);
            }
        }

        solarSystems.sort(Comparator.comparing(SolarSystem::solarSystem));
        galaxies.sort(Comparator.comparing(Galaxy::galaxy));
        GCYRClient.skyRenderers = skyRenderers;
        GCYRClient.skyShaders = skyShaders;
        GCYRClient.solarSystems = solarSystems;
        GCYRClient.planetRings = planetRings;
        GCYRClient.galaxies = galaxies;
        ClientModSkies.register();
    }

    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) {
        for (var entry : GCYRClient.skyShaders.entrySet()) {
            try {
                ShaderInstance shader = new ShaderInstance(event.getResourceProvider(), entry.getKey(), DefaultVertexFormat.POSITION);
                event.registerShader(shader, entry::setValue);
            } catch (IOException e) {
                throw new RuntimeException("Failed to register shader with id " + entry.getKey(), e);
            }
        }
    }
}
