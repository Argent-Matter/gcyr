package argent_matter.gcyr;

import argent_matter.gcyr.api.space.planet.Galaxy;
import argent_matter.gcyr.api.space.planet.PlanetRing;
import argent_matter.gcyr.api.space.planet.PlanetSkyRenderer;
import argent_matter.gcyr.api.space.planet.SolarSystem;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GCyRClient {
    public static final ResourceLocation DYSON_SUN_LOCATION = GCyR.id("textures/sky/sun_dyson.png");


    public static boolean isDysonSphereActive = false;

    public static boolean hasUpdatedPlanets = false;
    public static List<PlanetSkyRenderer> skyRenderers = new ArrayList<>();
    public static List<SolarSystem> solarSystems = new ArrayList<>();
    public static List<PlanetRing> planetRings = new ArrayList<>();
    public static List<Galaxy> galaxies = new ArrayList<>();

    public static void init() {
        //GCyRKeyMappings.init();
    }
}
