package argent_matter.gcyr.common.data.worldgen.biome;

import net.minecraft.world.level.biome.MobSpawnSettings;

public class GCYRBiomeDefaultFeatures {

    public static void moonSpawns(MobSpawnSettings.Builder builder) {
        builder.creatureGenerationProbability(0.0f);
        // builder.addSpawn(MobCategory.MONSTER,
        // new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 4, 4));
    }

    public static void marsSpawns(MobSpawnSettings.Builder builder) {
        builder.creatureGenerationProbability(0.2f);
    }

    public static void mercurySpawns(MobSpawnSettings.Builder builder) {
        builder.creatureGenerationProbability(0.0f);
    }

    public static void venusSpawns(MobSpawnSettings.Builder builder) {
        builder.creatureGenerationProbability(0.0f);
    }

    public static void proximaCentauriBSpawns(MobSpawnSettings.Builder builder) {
        builder.creatureGenerationProbability(0.0f);
    }
}
