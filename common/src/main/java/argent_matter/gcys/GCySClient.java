package argent_matter.gcys;

import argent_matter.gcys.api.space.planet.Galaxy;
import argent_matter.gcys.api.space.planet.PlanetRing;
import argent_matter.gcys.api.space.planet.PlanetSkyRenderer;
import argent_matter.gcys.api.space.planet.SolarSystem;
import argent_matter.gcys.common.data.GCySKeyMappings;

import java.util.ArrayList;
import java.util.List;

public class GCySClient {

    public static boolean isDysonSphereActive = false;

    public static boolean hasUpdatedPlanets = false;
    public static List<PlanetSkyRenderer> skyRenderers = new ArrayList<>();
    public static List<SolarSystem> solarSystems = new ArrayList<>();
    public static List<PlanetRing> planetRings = new ArrayList<>();
    public static List<Galaxy> galaxies = new ArrayList<>();

    public static void init() {
        //GCySKeyMappings.init();
    }
}
