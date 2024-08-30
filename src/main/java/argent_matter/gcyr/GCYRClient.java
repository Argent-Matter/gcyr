package argent_matter.gcyr;

import argent_matter.gcyr.api.space.planet.Galaxy;
import argent_matter.gcyr.api.space.planet.PlanetRing;
import argent_matter.gcyr.api.space.planet.PlanetSkyRenderer;
import argent_matter.gcyr.api.space.planet.SolarSystem;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class GCYRClient {

    public static boolean isDysonSphereActive = false;

    public static boolean hasUpdatedPlanets = false;
    public static List<PlanetSkyRenderer> skyRenderers = new ArrayList<>();
    public static Map<ResourceLocation, ShaderInstance> skyShaders = new HashMap<>();
    public static List<SolarSystem> solarSystems = new ArrayList<>();
    public static List<PlanetRing> planetRings = new ArrayList<>();
    public static List<Galaxy> galaxies = new ArrayList<>();

    public static void init() {
        //GCYRKeyMappings.init();
    }
}
