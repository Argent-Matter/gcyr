package argent_matter.gcyr.common.data.dimension;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.worldgen.biome.GCYRBiomes;
import argent_matter.gcyr.common.data.worldgen.noise.GCYRNoiseSettings;
import argent_matter.gcyr.common.worldgen.SpaceLevelSource;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import com.mojang.datafixers.util.Pair;

import java.util.List;

import static net.minecraft.core.registries.Registries.levelToLevelStem;
import static net.minecraft.world.level.biome.Climate.Parameter.*;

public class GCYRDimensions {

    // spotless:off
    public static final ResourceKey<Level> BLACK_HOLE_ORBIT = register("black_hole_orbit");
    public static final ResourceKey<Level> OVERWORLD_ORBIT = register("overworld_orbit");
    public static final ResourceKey<Level> LUNA = register("luna");
    public static final ResourceKey<Level> LUNA_ORBIT = register("luna_orbit");
    public static final ResourceKey<Level> MARS = register("mars");
    public static final ResourceKey<Level> MARS_ORBIT = register("mars_orbit");
    public static final ResourceKey<Level> MERCURY = register("mercury");
    public static final ResourceKey<Level> MERCURY_ORBIT = register("mercury_orbit");
    public static final ResourceKey<Level> VENUS = register("venus");
    public static final ResourceKey<Level> VENUS_ORBIT = register("venus_orbit");
    public static final ResourceKey<Level> PROXIMA_CENTAURI_B = register("proxima_centauri_b");
    public static final ResourceKey<Level> PROXIMA_CENTAURI_B_ORBIT = register("proxima_centauri_b_orbit");

    public static void bootstrap(BootstapContext<LevelStem> ctx) {
        HolderGetter<DimensionType> dimensionTypes = ctx.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGeneratorSettings = ctx.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);

        registerSpaceDimension(ctx, BLACK_HOLE_ORBIT);
        registerSpaceDimension(ctx, OVERWORLD_ORBIT);

        ctx.register(levelToLevelStem(LUNA), new LevelStem(dimensionTypes.getOrThrow(GCYRDimensionTypes.LUNA),
                new NoiseBasedChunkGenerator(
                        MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(
                                Pair.of(Climate.parameters(point(0.0f), point(0.0f), point(0.0f), span(0.35f, 1.0f), point(0.0f), point(0.0f), 0.0f), biomes.getOrThrow(GCYRBiomes.LUNAR_MARE)),
                                Pair.of(Climate.parameters(point(0.0f), point(0.0f), point(0.0f), span(-1.0f, -0.35f), point(0.0f), point(0.0f), 0.0f), biomes.getOrThrow(GCYRBiomes.LUNAR_PLAINS))
                        ))),
                        noiseGeneratorSettings.getOrThrow(GCYRNoiseSettings.LUNA)
                )
        ));
        registerSpaceDimension(ctx, LUNA_ORBIT);

        ctx.register(levelToLevelStem(MARS), new LevelStem(dimensionTypes.getOrThrow(GCYRDimensionTypes.MARS),
                new NoiseBasedChunkGenerator(
                        MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(
                                Pair.of(Climate.parameters(-0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.175f), biomes.getOrThrow(GCYRBiomes.MARTIAN_WASTELANDS)),
                                Pair.of(Climate.parameters(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.25f, 0.0f), biomes.getOrThrow(GCYRBiomes.MARTIAN_CANYON_CREEK)),
                                Pair.of(Climate.parameters(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f), biomes.getOrThrow(GCYRBiomes.MARTIAN_POLAR_CAPS))
                        ))),
                        noiseGeneratorSettings.getOrThrow(GCYRNoiseSettings.MARS)
                )
        ));
        registerSpaceDimension(ctx, MARS_ORBIT);

        ctx.register(levelToLevelStem(MERCURY), new LevelStem(dimensionTypes.getOrThrow(GCYRDimensionTypes.MERCURY),
                new NoiseBasedChunkGenerator(
                        MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(
                                Pair.of(Climate.parameters(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f), biomes.getOrThrow(GCYRBiomes.MERCURY_DELTAS))
                        ))),
                        noiseGeneratorSettings.getOrThrow(GCYRNoiseSettings.MERCURY)
                )
        ));
        registerSpaceDimension(ctx, MERCURY_ORBIT);

        ctx.register(levelToLevelStem(VENUS), new LevelStem(dimensionTypes.getOrThrow(GCYRDimensionTypes.VENUS),
                new NoiseBasedChunkGenerator(
                        MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(
                                Pair.of(Climate.parameters(0.8f, -1.0f, 0.3f, -0.5f, 0.0f, -0.5f, 0.0f), biomes.getOrThrow(GCYRBiomes.VENUS_ERODED_PLAINS)),
                                Pair.of(Climate.parameters(0.0f, 0.0f, -0.65f, 0.5f, 0.0f, -1.5f, 0.0f), biomes.getOrThrow(GCYRBiomes.VENUS_BARREN_PLAINS))
                        ))),
                        noiseGeneratorSettings.getOrThrow(GCYRNoiseSettings.VENUS)
                )
        ));
        registerSpaceDimension(ctx, VENUS_ORBIT);

        ctx.register(levelToLevelStem(PROXIMA_CENTAURI_B), new LevelStem(dimensionTypes.getOrThrow(GCYRDimensionTypes.PROXIMA_CENTAURI_B),
                new NoiseBasedChunkGenerator(
                        MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(
                                Pair.of(Climate.parameters(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f), biomes.getOrThrow(GCYRBiomes.PROXIMA_CENTAURI_B_MIDDLE)),
                                Pair.of(Climate.parameters(0.4f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f), biomes.getOrThrow(GCYRBiomes.PROXIMA_CENTAURI_B_LIGHT_SIDE)),
                                Pair.of(Climate.parameters(-0.4f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f), biomes.getOrThrow(GCYRBiomes.PROXIMA_CENTAURI_B_DARK_SIDE)),
                                Pair.of(Climate.parameters(span(-0.05f, 0.09f), point(0.0f), point(0.0f), point(0.0f), span(0.09f, 0.6f), point(0.0f), 0.0f), biomes.getOrThrow(GCYRBiomes.PROXIMA_CENTAURI_B_UNDERGROUND))
                        ))),
                        noiseGeneratorSettings.getOrThrow(GCYRNoiseSettings.PROXIMA_CENTAURI_B)
                )
        ));
        registerSpaceDimension(ctx, PROXIMA_CENTAURI_B_ORBIT);
    }

    public static void registerSpaceDimension(BootstapContext<LevelStem> ctx, ResourceKey<Level> key) {
        HolderGetter<DimensionType> dimensionTypes = ctx.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);

        ctx.register(levelToLevelStem(key), new LevelStem(
                dimensionTypes.getOrThrow(GCYRDimensionTypes.SPACE),
                new SpaceLevelSource(biomes.getOrThrow(GCYRBiomes.SPACE))
        ));
    }
    // spotless:on

    private static ResourceKey<Level> register(String path) {
        return ResourceKey.create(Registries.DIMENSION, GCYR.id(path));
    }
}
