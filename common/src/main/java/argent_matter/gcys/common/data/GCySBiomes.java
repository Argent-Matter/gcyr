package argent_matter.gcys.common.data;

import argent_matter.gcys.GCyS;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import org.jetbrains.annotations.Nullable;

public class GCySBiomes {

    public static final ResourceKey<Biome> SPACE = ResourceKey.create(Registry.BIOME_REGISTRY, GCyS.id("space"));

    @ExpectPlatform
    public static void init() {
        throw new AssertionError();
    }

    public static Biome space() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
        return biome(Biome.Precipitation.NONE, 0.5F, 0.5F, new MobSpawnSettings.Builder(), builder, null);
    }

    private static Biome biome(Biome.Precipitation precipitation, float temp, float rainfall, MobSpawnSettings.Builder spawn, BiomeGenerationSettings.Builder gen, @Nullable Music music) {
        return biome(precipitation, temp, rainfall, 4159204, 329011, spawn, gen, music);
    }

    private static Biome biome(Biome.Precipitation precipitation, float temp, float rainfall, int waterColor, int waterFogColor, MobSpawnSettings.Builder mobSpawning, BiomeGenerationSettings.Builder generation, @Nullable Music music) {
        return new Biome.BiomeBuilder().precipitation(precipitation).temperature(temp).downfall(rainfall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(calculateSkyColor(temp)).backgroundMusic(music).build()).mobSpawnSettings(mobSpawning.build()).generationSettings(generation.build()).build();
    }

    protected static int calculateSkyColor(float temperature) {
        float temp = temperature / 3.0F;
        temp = Mth.clamp(temp, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - temp * 0.05F, 0.5F + temp * 0.1F, 1.0F);
    }

}

