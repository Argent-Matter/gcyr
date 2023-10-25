package argent_matter.gcyr.client.gui;

import argent_matter.gcyr.GCyR;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public record Category(ResourceLocation id, Category parent) {

    public static final Category BACK = new Category(GCyR.id("back"), null);

    public static final Category GALAXY_CATEGORY = new Category(GCyR.id("galaxy"), null);
    public static final Category MILKY_WAY_CATEGORY = new Category(GCyR.id("milky_way"), GALAXY_CATEGORY);
    public static final Category SOLAR_SYSTEM_CATEGORY = new Category(GCyR.id("solar_system.json"), MILKY_WAY_CATEGORY);
    public static final Category EARTH_CATEGORY = new Category(GCyR.id("earth"), SOLAR_SYSTEM_CATEGORY);
    public static final Category MARS_CATEGORY = new Category(GCyR.id("mars"), SOLAR_SYSTEM_CATEGORY);
    public static final Category VENUS_CATEGORY = new Category(GCyR.id("venus"), SOLAR_SYSTEM_CATEGORY);
    public static final Category MERCURY_CATEGORY = new Category(GCyR.id("mercury"), SOLAR_SYSTEM_CATEGORY);
    public static final Category PROXIMA_CENTAURI_CATEGORY = new Category(GCyR.id("proxima_centauri"), MILKY_WAY_CATEGORY);
    public static final Category GLACIO_CATEGORY = new Category(GCyR.id("glacio"), PROXIMA_CENTAURI_CATEGORY);
}