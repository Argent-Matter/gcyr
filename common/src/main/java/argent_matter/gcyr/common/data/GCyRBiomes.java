package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCyR;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.Nullable;

public class GCyRBiomes {
    public static final ResourceKey<Biome> SPACE = ResourceKey.create(Registries.BIOME, GCyR.id("space"));


    public static void bootstrap(BootstapContext<Biome> ctx) {
        ctx.register(SPACE, space(ctx.lookup(Registries.PLACED_FEATURE), ctx.lookup(Registries.CONFIGURED_CARVER)));
    }

    public static Biome space(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        return biome(0.0f, 0.5F, 0.5F, new MobSpawnSettings.Builder(), builder, null);
    }

    private static Biome biome(float downfall, float temp, float rainfall, MobSpawnSettings.Builder spawn, BiomeGenerationSettings.Builder gen, @Nullable Music music) {
        return biome(downfall, temp, rainfall, 4159204, 329011, spawn, gen, music);
    }

    private static Biome biome(float downfall, float temp, float rainfall, int waterColor, int waterFogColor, MobSpawnSettings.Builder mobSpawning, BiomeGenerationSettings.Builder generation, @Nullable Music music) {
        return new Biome.BiomeBuilder().downfall(downfall).temperature(temp).downfall(rainfall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(calculateSkyColor(temp)).backgroundMusic(music).build()).mobSpawnSettings(mobSpawning.build()).generationSettings(generation.build()).build();
    }

    protected static int calculateSkyColor(float temperature) {
        float temp = temperature / 3.0F;
        temp = Mth.clamp(temp, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - temp * 0.05F, 0.5F + temp * 0.1F, 1.0F);
    }

}

