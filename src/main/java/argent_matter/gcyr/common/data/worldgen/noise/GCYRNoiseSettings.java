package argent_matter.gcyr.common.data.worldgen.noise;

import argent_matter.gcyr.GCYR;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.*;

public class GCYRNoiseSettings {

    // spotless:off
    public static final ResourceKey<NoiseGeneratorSettings> LUNA = register("luna");
    public static final ResourceKey<NoiseGeneratorSettings> MARS = register("mars");
    public static final ResourceKey<NoiseGeneratorSettings> MERCURY = register("mercury");
    public static final ResourceKey<NoiseGeneratorSettings> VENUS = register("venus");
    public static final ResourceKey<NoiseGeneratorSettings> PROXIMA_CENTAURI_B = register("proxima_centauri_b");

    private static ResourceKey<NoiseGeneratorSettings> register(String path) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, GCYR.id(path));
    }

// spotless:off
// Remove the spotless lines if this class is ever uncommented
/*
    protected static final NoiseSettings BASIC_NOISE_SETTINGS = NoiseSettings.create(-64, 384, 1, 2);

    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> ctx) {
        HolderGetter<DensityFunction> densityFunctions = ctx.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noises = ctx.lookup(Registries.NOISE);

        ctx.register(LUNA, new NoiseGeneratorSettings(
                BASIC_NOISE_SETTINGS,
                GCYRBlocks.LUNAR_STONE.getDefaultState(),
                Blocks.LAVA.defaultBlockState(),
                NoiseRouterData.overworld(densityFunctions, noises, false, false),
                SurfaceRuleData.overworld(),
                new OverworldBiomeBuilder().spawnTarget(),
                30,
                true,
                false,
                false,
                false)
        );
    }
    // spotless:on
     * 
     * // spotless:off
    protected static NoiseRouter overworld(HolderGetter<DensityFunction> densityFunctions,
                                           HolderGetter<NormalNoise.NoiseParameters> noiseParameters) {
        DensityFunction aquiferBarrier = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_BARRIER), 0.5D);
        DensityFunction aquiferFluidLevelFloodedness = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 2.0 / 3.0);
        DensityFunction aquiferFluidLevelSpread = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), Math.sqrt(2.0) / 2);
        DensityFunction aquiferLava = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_LAVA));
        DensityFunction shiftX = getFunction(densityFunctions, NoiseRouterData.SHIFT_X);
        DensityFunction shiftZ = getFunction(densityFunctions, NoiseRouterData.SHIFT_Z);
        DensityFunction temperature = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25D, noiseParameters.getOrThrow(Noises.TEMPERATURE));
        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25D, noiseParameters.getOrThrow(Noises.VEGETATION));
        DensityFunction factor = getFunction(densityFunctions, NoiseRouterData.FACTOR);
        DensityFunction depth = getFunction(densityFunctions, NoiseRouterData.DEPTH);
        DensityFunction density = NoiseRouterData.noiseGradientDensity(DensityFunctions.cache2d(factor), depth);
        DensityFunction slopedCheese = getFunction(densityFunctions, NoiseRouterData.SLOPED_CHEESE);
        DensityFunction caveEntrances = DensityFunctions.min(
                slopedCheese,
                DensityFunctions.mul(DensityFunctions.constant(5.0D), getFunction(densityFunctions, NoiseRouterData.ENTRANCES))
        );
        DensityFunction caves = DensityFunctions.rangeChoice(
                slopedCheese,
                DensityFunctions.MAX_REASONABLE_NOISE_VALUE,
                NoiseRouterData.SURFACE_DENSITY_THRESHOLD,
                caveEntrances,
                NoiseRouterData.underground(densityFunctions, noiseParameters, slopedCheese)
        );
        DensityFunction finalDensity = DensityFunctions.min(NoiseRouterData.postProcess(NoiseRouterData.slideOverworld(false, caves)), getFunction(densityFunctions, NoiseRouterData.NOODLE));
        DensityFunction y = getFunction(densityFunctions, NoiseRouterData.Y);

        int minVeinY = Stream.of(OreVeinifier.VeinType.values())
                .mapToInt((vein) -> vein.minY)
                .min()
                .orElse(-DimensionType.MIN_Y * 2);
        int maxVeinY = Stream.of(OreVeinifier.VeinType.values())
                .mapToInt((vein) -> vein.maxY)
                .max()
                .orElse(-DimensionType.MIN_Y * 2);
        DensityFunction oreVeininess = NoiseRouterData.yLimitedInterpolatable(
                y,
                DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEININESS),
                        NoiseRouterData.VEININESS_FREQUENCY, NoiseRouterData.VEININESS_FREQUENCY
                ),
                minVeinY, maxVeinY, 0
        );
        double oreVeinNoiseScale = 4.0D;
        DensityFunction oreVeinA = NoiseRouterData.yLimitedInterpolatable(
                y,
                DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEIN_A),
                        oreVeinNoiseScale, oreVeinNoiseScale
                ), minVeinY, maxVeinY, 0
        ).abs();
        DensityFunction oreVeinB = NoiseRouterData.yLimitedInterpolatable(
                y,
                DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEIN_B),
                        oreVeinNoiseScale, oreVeinNoiseScale
                ), minVeinY, maxVeinY, 0
        ).abs();
        DensityFunction oreVein = DensityFunctions.add(DensityFunctions.constant(NoiseRouterData.ORE_THICKNESS), DensityFunctions.max(oreVeinA, oreVeinB));
        DensityFunction oreGap = DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_GAP));
        return new NoiseRouter(aquiferBarrier, aquiferFluidLevelFloodedness, aquiferFluidLevelSpread, aquiferLava,
                temperature, vegetation, getFunction(densityFunctions, NoiseRouterData.CONTINENTS), getFunction(densityFunctions, NoiseRouterData.EROSION), depth,
                getFunction(densityFunctions, NoiseRouterData.RIDGES),
                NoiseRouterData.slideOverworld(
                        false,
                        DensityFunctions.add(density, DensityFunctions.constant(NoiseRouterData.CHEESE_NOISE_TARGET))
                                .clamp(-NoiseRouterData.ISLAND_CHUNK_DISTANCE, NoiseRouterData.ISLAND_CHUNK_DISTANCE)
                ),
                finalDensity, oreVeininess, oreVein, oreGap);
    }
    // remove the spaces if this class is ever unncommented
    // spotless : on

    private static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctions, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
    }
*/
// spotless:on
}
