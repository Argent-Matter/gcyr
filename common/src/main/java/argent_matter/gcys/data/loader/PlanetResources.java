package argent_matter.gcys.data.loader;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.GregicalitySpaceClient;
import argent_matter.gcys.api.space.planet.Galaxy;
import argent_matter.gcys.api.space.planet.PlanetRing;
import argent_matter.gcys.api.space.planet.PlanetSkyRenderer;
import argent_matter.gcys.api.space.planet.SolarSystem;
import argent_matter.gcys.client.dimension.ClientModSkies;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.GsonHelper;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlanetResources implements ResourceManagerReloadListener {
    public static final PlanetResources INSTANCE = new PlanetResources();

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().setLenient().create();

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        List<PlanetSkyRenderer> skyRenderers = new ArrayList<>();
        List<SolarSystem> solarSystems = new ArrayList<>();
        List<PlanetRing> planetRings = new ArrayList<>();
        List<Galaxy> galaxies = new ArrayList<>();

        // Sky Renderers
        for (ResourceLocation id : manager.listResources("gcys/planet_assets/sky_renderers", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getResourceStack(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        skyRenderers.add(PlanetSkyRenderer.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, GregicalitySpace.LOGGER::error));
                    }
                }
            } catch (Exception e) {
                GregicalitySpace.LOGGER.error("Failed to load Gregicality Space sky rendering assets from: \"" + id.toString() + "\"", e);
                e.printStackTrace();
            }
        }

        // Solar Systems
        for (ResourceLocation id : manager.listResources("gcys/planet_assets/solar_systems", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getResourceStack(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        solarSystems.add(SolarSystem.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, GregicalitySpace.LOGGER::error));
                    }
                }
            } catch (Exception e) {
                GregicalitySpace.LOGGER.error("Failed to load Gregicality Space solar system assets from: \"" + id.toString() + "\"", e);
                e.printStackTrace();
            }
        }

        for (ResourceLocation id : manager.listResources("gcys/planet_assets/planet_rings", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getResourceStack(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        planetRings.add(PlanetRing.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, GregicalitySpace.LOGGER::error));
                    }
                }
            } catch (Exception e) {
                GregicalitySpace.LOGGER.error("Failed to load Gregicality Space planet ring assets from: \"" + id.toString() + "\"", e);
                e.printStackTrace();
            }
        }

        // Galaxies
        for (ResourceLocation id : manager.listResources("gcys/planet_assets/galaxies", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getResourceStack(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        galaxies.add(Galaxy.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, GregicalitySpace.LOGGER::error));
                    }
                }
            } catch (Exception e) {
                GregicalitySpace.LOGGER.error("Failed to load Gregicality Space galaxy assets from: \"" + id.toString() + "\"", e);
                e.printStackTrace();
            }
        }

        solarSystems.sort(Comparator.comparing(SolarSystem::solarSystem));
        galaxies.sort(Comparator.comparing(Galaxy::galaxy));
        GregicalitySpaceClient.skyRenderers = skyRenderers;
        GregicalitySpaceClient.solarSystems = solarSystems;
        GregicalitySpaceClient.planetRings = planetRings;
        GregicalitySpaceClient.galaxies = galaxies;
        ClientModSkies.register();
    }
}