package argent_matter.gcys;

import argent_matter.gcys.api.space.planet.Galaxy;
import argent_matter.gcys.api.space.planet.PlanetRing;
import argent_matter.gcys.api.space.planet.SolarSystem;
import argent_matter.gcys.common.data.GcysKeyMappings;

import java.util.ArrayList;
import java.util.List;

public class GregicalitySpaceClient {

    public static boolean hasUpdatedPlanets = false;
    public static List<SolarSystem> solarSystems = new ArrayList<>();
    public static List<PlanetRing> planetRings = new ArrayList<>();
    public static List<Galaxy> galaxies = new ArrayList<>();

    public static void init() {
        GcysKeyMappings.init();
    }
}
