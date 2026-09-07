package argent_matter.gcyr.common.data.worldgen.biome;

import argent_matter.gcyr.GCYR;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class GCYRBiomes {

    // spotless:off
    public static final ResourceKey<Biome> SPACE = register("space");
    public static final ResourceKey<Biome> LUNAR_MARE = register("lunar_mare");
    public static final ResourceKey<Biome> LUNAR_PLAINS = register("lunar_plains");
    public static final ResourceKey<Biome> MARTIAN_CANYON_CREEK = register("martian_canyon_creek");
    public static final ResourceKey<Biome> MARTIAN_POLAR_CAPS = register("martian_polar_caps");
    public static final ResourceKey<Biome> MARTIAN_WASTELANDS = register("martian_wastelands");
    public static final ResourceKey<Biome> MERCURY_DELTAS = register("mercury_deltas");
    public static final ResourceKey<Biome> VENUS_BARREN_PLAINS = register("venus_barren_plains");
    public static final ResourceKey<Biome> VENUS_ERODED_PLAINS = register("venus_eroded_plains");
    public static final ResourceKey<Biome> PROXIMA_CENTAURI_B_MIDDLE = register("proxima_centauri_b_middle");
    public static final ResourceKey<Biome> PROXIMA_CENTAURI_B_DARK_SIDE = register("proxima_centauri_b_dark_side");
    public static final ResourceKey<Biome> PROXIMA_CENTAURI_B_LIGHT_SIDE = register("proxima_centauri_b_light_side");
    public static final ResourceKey<Biome> PROXIMA_CENTAURI_B_UNDERGROUND = register("proxima_centauri_b_underground");

    public static void bootstrap(BootstapContext<Biome> ctx) {
        HolderGetter<PlacedFeature> placedFeatures = ctx.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> worldCarvers = ctx.lookup(Registries.CONFIGURED_CARVER);

        ctx.register(SPACE, SpaceBiomes.space());

        ctx.register(LUNAR_MARE, MoonBiomes.lunarMare(placedFeatures, worldCarvers));
        ctx.register(LUNAR_PLAINS, MoonBiomes.lunarPlains(placedFeatures, worldCarvers));

        ctx.register(MARTIAN_CANYON_CREEK, MarsBiomes.basicMarsBiome(placedFeatures, worldCarvers));
        ctx.register(MARTIAN_POLAR_CAPS, MarsBiomes.martianPolarCaps(placedFeatures, worldCarvers));
        ctx.register(MARTIAN_WASTELANDS, MarsBiomes.basicMarsBiome(placedFeatures, worldCarvers));

        ctx.register(MERCURY_DELTAS, MercuryBiomes.mercuryDeltas(placedFeatures, worldCarvers));

        ctx.register(VENUS_BARREN_PLAINS, VenusBiomes.basicVenusBiome(placedFeatures, worldCarvers));
        ctx.register(VENUS_ERODED_PLAINS, VenusBiomes.basicVenusBiome(placedFeatures, worldCarvers));

        ctx.register(PROXIMA_CENTAURI_B_DARK_SIDE, ProximaCentauriBBiomes.prbDarkSide(placedFeatures, worldCarvers));
        ctx.register(PROXIMA_CENTAURI_B_LIGHT_SIDE, ProximaCentauriBBiomes.prbLightSide(placedFeatures, worldCarvers));
        ctx.register(PROXIMA_CENTAURI_B_MIDDLE, ProximaCentauriBBiomes.prbMiddle(placedFeatures, worldCarvers));
        ctx.register(PROXIMA_CENTAURI_B_UNDERGROUND, ProximaCentauriBBiomes.prbUnderground(placedFeatures, worldCarvers));
    }
    // spotless:on

    private static ResourceKey<Biome> register(String path) {
        return ResourceKey.create(Registries.BIOME, GCYR.id(path));
    }
}
