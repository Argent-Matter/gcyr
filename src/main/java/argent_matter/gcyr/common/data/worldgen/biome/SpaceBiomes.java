package argent_matter.gcyr.common.data.worldgen.biome;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;

import net.minecraftforge.common.world.BiomeSpecialEffectsBuilder;

public class SpaceBiomes {

    public static Biome space() {
        return new Biome.BiomeBuilder()
                .downfall(0.0f)
                .temperature(0.0f)
                .hasPrecipitation(false)
                .specialEffects(BiomeSpecialEffectsBuilder.create(0xC0D8FF, 0x3F76E4, 0x050533, 0xFFFF80).build())
                .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                .generationSettings(new BiomeGenerationSettings.PlainBuilder().build())
                .build();
    }
}
