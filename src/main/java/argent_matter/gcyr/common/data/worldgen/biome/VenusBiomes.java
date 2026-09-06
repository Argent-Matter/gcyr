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

public class VenusBiomes {

    private static BiomeGenerationSettings.Builder baseVenusGenerationSettings(HolderGetter<PlacedFeature> placedFeatures,
                                                                               HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        var builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
        BiomeDefaultFeatures.addDripstone(builder);
        return builder;
    }

    private static Biome baseVenusBiome(BiomeGenerationSettings.Builder generationSettings) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        GCYRBiomeDefaultFeatures.venusSpawns(spawnSettings);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(1.6f)
                .downfall(1.0f)
                .specialEffects(BiomeSpecialEffectsBuilder.create(0xFFD375, 0x4F4017, 0x050533, 0xD4C29D)
                        .grassColorOverride(0x91BD59)
                        .foliageColorOverride(0x77AB2F)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }

    public static Biome basicVenusBiome(HolderGetter<PlacedFeature> placedFeatures,
                                        HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder generationSettings = baseVenusGenerationSettings(placedFeatures, worldCarvers);
        return baseVenusBiome(generationSettings);
    }
}
