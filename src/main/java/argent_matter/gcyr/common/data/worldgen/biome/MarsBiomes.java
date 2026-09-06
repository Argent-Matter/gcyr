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

public class MarsBiomes {

    private static BiomeGenerationSettings.Builder baseMarsGenerationSettings(HolderGetter<PlacedFeature> placedFeatures,
                                                                              HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        var builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
        BiomeDefaultFeatures.addDripstone(builder);
        return builder;
    }

    private static Biome baseMarsBiome(BiomeGenerationSettings.Builder generationSettings,
                                       float temperature, float downfall, boolean hasPrecipitation) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        GCYRBiomeDefaultFeatures.marsSpawns(spawnSettings);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(hasPrecipitation)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(BiomeSpecialEffectsBuilder.create(0xE6AC84, 0x3F76E4, 0x050533, 0xE6AC84)
                        .grassColorOverride(0x91BD59)
                        .foliageColorOverride(0x77AB2F)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }

    public static Biome basicMarsBiome(HolderGetter<PlacedFeature> placedFeatures,
                                       HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettings = baseMarsGenerationSettings(placedFeatures, worldCarvers);
        return baseMarsBiome(generationSettings, 0.8f, 0.0f, false);
    }

    public static Biome martianPolarCaps(HolderGetter<PlacedFeature> placedFeatures,
                                         HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettings = baseMarsGenerationSettings(placedFeatures, worldCarvers);
        return baseMarsBiome(generationSettings, -0.7f, 1.0f, true);
    }
}
