package argent_matter.gcyr.common.data.worldgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import net.minecraftforge.common.world.BiomeSpecialEffectsBuilder;

public class ProximaCentauriBBiomes {

    private static BiomeGenerationSettings.Builder basePRBGenerationSettings(HolderGetter<PlacedFeature> placedFeatures,
                                                                             HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        var builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
        BiomeDefaultFeatures.addDripstone(builder);
        return builder;
    }

    private static Biome basePRBBiome(BiomeGenerationSettings.Builder generationSettings,
                                      float temperature, Biome.TemperatureModifier temperatureModifier,
                                      boolean hasPrecipitation, int skyColor, int fogColor) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        GCYRBiomeDefaultFeatures.proximaCentauriBSpawns(spawnSettings);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(hasPrecipitation)
                .temperature(temperature)
                .temperatureAdjustment(temperatureModifier)
                .downfall(0.0f)
                .specialEffects(BiomeSpecialEffectsBuilder.create(fogColor, 0x1B252D, 0x1A1F23, skyColor)
                        .grassColorOverride(0x265D87)
                        .foliageColorOverride(0x265D87)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }

    public static Biome prbUnderground(HolderGetter<PlacedFeature> placedFeatures,
                                       HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettings = basePRBGenerationSettings(placedFeatures, worldCarvers);
        return basePRBBiome(generationSettings, 0.1f, Biome.TemperatureModifier.NONE,
                false, 0x0B3538, 0x0C4646);
    }

    public static Biome prbLightSide(HolderGetter<PlacedFeature> placedFeatures,
                                     HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettings = basePRBGenerationSettings(placedFeatures, worldCarvers);
        return basePRBBiome(generationSettings, 2.0f, Biome.TemperatureModifier.NONE,
                false, 0x0B3538, 0x0C4646);
    }

    public static Biome prbDarkSide(HolderGetter<PlacedFeature> placedFeatures,
                                    HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettings = basePRBGenerationSettings(placedFeatures, worldCarvers);
        return basePRBBiome(generationSettings, -1.5f, Biome.TemperatureModifier.FROZEN,
                true, 0x081621, 0x0A2029);
    }

    public static Biome prbMiddle(HolderGetter<PlacedFeature> placedFeatures,
                                  HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettings = basePRBGenerationSettings(placedFeatures, worldCarvers);
        return basePRBBiome(generationSettings, 0.0f, Biome.TemperatureModifier.NONE,
                true, 0x08292B, 0x063736);
    }
}
