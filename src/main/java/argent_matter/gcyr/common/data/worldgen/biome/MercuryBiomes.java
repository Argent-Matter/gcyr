package argent_matter.gcyr.common.data.worldgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import net.minecraftforge.common.world.BiomeSpecialEffectsBuilder;

public class MercuryBiomes {

    private static Biome baseMercuryBiome(BiomeGenerationSettings.Builder generationSettings) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        GCYRBiomeDefaultFeatures.mercurySpawns(spawnSettings);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(1.6f)
                .downfall(0.0f)
                .specialEffects(BiomeSpecialEffectsBuilder.create(0x000000, 0x3F76E4, 0x050533, 0x000000)
                        .grassColorOverride(0x91BD59)
                        .foliageColorOverride(0x77AB2F)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }

    public static Biome mercuryDeltas(HolderGetter<PlacedFeature> placedFeatures,
                                      HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        var generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CANYON)
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, NetherPlacements.BASALT_PILLAR)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BASALT_BLOBS)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BLACKSTONE_BLOBS);
        return baseMercuryBiome(generationSettings);
    }
}
