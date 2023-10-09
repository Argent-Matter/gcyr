package argent_matter.gcys.client.gui;

import argent_matter.gcys.GregicalitySpace;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public record Category(ResourceLocation id, Category parent) {

    public static final Category BACK = new Category(GregicalitySpace.id("back"), null);

    public static final Category GALAXY_CATEGORY = new Category(GregicalitySpace.id("galaxy"), null);
    public static final Category MILKY_WAY_CATEGORY = new Category(GregicalitySpace.id("milky_way"), GALAXY_CATEGORY);
    public static final Category SOLAR_SYSTEM_CATEGORY = new Category(GregicalitySpace.id("solar_system"), MILKY_WAY_CATEGORY);
    public static final Category EARTH_CATEGORY = new Category(GregicalitySpace.id("earth"), SOLAR_SYSTEM_CATEGORY);
    public static final Category MARS_CATEGORY = new Category(GregicalitySpace.id("mars"), SOLAR_SYSTEM_CATEGORY);
    public static final Category VENUS_CATEGORY = new Category(GregicalitySpace.id("venus"), SOLAR_SYSTEM_CATEGORY);
    public static final Category MERCURY_CATEGORY = new Category(GregicalitySpace.id("mercury"), SOLAR_SYSTEM_CATEGORY);
    public static final Category PROXIMA_CENTAURI_CATEGORY = new Category(GregicalitySpace.id("proxima_centauri"), MILKY_WAY_CATEGORY);
    public static final Category GLACIO_CATEGORY = new Category(GregicalitySpace.id("glacio"), PROXIMA_CENTAURI_CATEGORY);
}