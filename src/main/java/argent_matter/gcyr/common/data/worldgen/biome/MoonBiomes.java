package argent_matter.gcyr.common.data.worldgen.biome;

import argent_matter.gcyr.common.data.worldgen.feature.GCYRPlacedFeatures;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import net.minecraftforge.common.world.BiomeSpecialEffectsBuilder;

public class MoonBiomes {

    private static Biome baseMoonBiome(BiomeGenerationSettings.Builder generationSettings) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        GCYRBiomeDefaultFeatures.moonSpawns(spawnSettings);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.0f)
                .temperatureAdjustment(Biome.TemperatureModifier.FROZEN)
                .downfall(0.0f)
                .specialEffects(BiomeSpecialEffectsBuilder.create(0x000000, 0x5C5A70, 0x30333D, 0x000000)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }

    public static Biome lunarMare(HolderGetter<PlacedFeature> placedFeatures,
                                  HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures,
                worldCarvers)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND)
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, GCYRPlacedFeatures.LUNAR_MARE_CRATER);
        return baseMoonBiome(generationSettings);
    }

    public static Biome lunarPlains(HolderGetter<PlacedFeature> placedFeatures,
                                    HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures,
                worldCarvers)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND)
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, GCYRPlacedFeatures.MOON_ROCK)
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, GCYRPlacedFeatures.LUNAR_PLAINS_CRATER);
        return baseMoonBiome(generationSettings);
    }
}
