package argent_matter.gcyr.common.data.worldgen.feature;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.block.GCYRBlocks;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class GCYRConfiguredFeatures {

    // spotless:off
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUNAR_MARE_CRATER = register("lunar_mare_crater");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUNAR_PLAINS_CRATER = register("lunar_plains_crater");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOON_ROCK = register("moon_rock");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
        FeatureUtils.register(ctx, LUNAR_MARE_CRATER, Feature.GEODE, new GeodeConfiguration(
                new GeodeBlockSettings(
                        BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(GCYRBlocks.LUNAR_MARE_REGOLITH.get()),
                        List.of(Blocks.AIR.defaultBlockState()),
                        BlockTags.FEATURES_CANNOT_REPLACE,
                        BlockTags.GEODE_INVALID_BLOCKS
                ),
                new GeodeLayerSettings(16.5, 18.5, 19.5, 23.5),
                new GeodeCrackSettings(0, 0, 0),
                0.35, 0.0, true,
                ConstantInt.of(4),
                UniformInt.of(3, 4),
                UniformInt.of(1, 2),
                -24, 24, 0.005, 2000
        ));
        FeatureUtils.register(ctx, LUNAR_PLAINS_CRATER, Feature.GEODE, new GeodeConfiguration(
                new GeodeBlockSettings(
                        BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(Blocks.AIR),
                        BlockStateProvider.simple(GCYRBlocks.LUNAR_SAND.get()),
                        List.of(Blocks.AIR.defaultBlockState()),
                        BlockTags.FEATURES_CANNOT_REPLACE,
                        BlockTags.GEODE_INVALID_BLOCKS
                ),
                new GeodeLayerSettings(16.5, 18.5, 19.5, 23.5),
                new GeodeCrackSettings(0, 0, 0),
                0.35, 0.0, true,
                ConstantInt.of(4),
                UniformInt.of(3, 4),
                UniformInt.of(1, 2),
                -24, 24, 0.005, 2000
        ));
        FeatureUtils.register(ctx, MOON_ROCK, Feature.FOREST_ROCK, new BlockStateConfiguration(Blocks.OBSIDIAN.defaultBlockState()));
    }
    // spotless:on

    private static ResourceKey<ConfiguredFeature<?, ?>> register(String path) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, GCYR.id(path));
    }
}
