package argent_matter.gcyr.client.gui;

import argent_matter.gcyr.GCYR;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record Category(ResourceLocation id, Category parent) {

    public static final Category BACK = new Category(GCYR.id("back"), null);

    public static final Category GALAXY_CATEGORY = new Category(GCYR.id("galaxy"), null);
    public static final Category MILKY_WAY_CATEGORY = new Category(GCYR.id("milky_way"), GALAXY_CATEGORY);
    public static final Category SOLAR_SYSTEM_CATEGORY = new Category(GCYR.id("solar_system.json"), MILKY_WAY_CATEGORY);
    public static final Category EARTH_CATEGORY = new Category(GCYR.id("earth"), SOLAR_SYSTEM_CATEGORY);
    public static final Category MARS_CATEGORY = new Category(GCYR.id("mars"), SOLAR_SYSTEM_CATEGORY);
    public static final Category VENUS_CATEGORY = new Category(GCYR.id("venus"), SOLAR_SYSTEM_CATEGORY);
    public static final Category MERCURY_CATEGORY = new Category(GCYR.id("mercury"), SOLAR_SYSTEM_CATEGORY);
    public static final Category PROXIMA_CENTAURI_CATEGORY = new Category(GCYR.id("proxima_centauri"), MILKY_WAY_CATEGORY);
}
