package argent_matter.gcyr.common.data.worldgen.feature;

import argent_matter.gcyr.GCYR;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class GCYRPlacedFeatures {

    // spotless:off
    public static final ResourceKey<PlacedFeature> LUNAR_MARE_CRATER = register("lunar_mare_crater");
    public static final ResourceKey<PlacedFeature> LUNAR_PLAINS_CRATER = register("lunar_plains_crater");
    public static final ResourceKey<PlacedFeature> MOON_ROCK = register("moon_rock");

    public static void bootstrap(BootstapContext<PlacedFeature> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> features = ctx.lookup(Registries.CONFIGURED_FEATURE);
        HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);

        PlacementUtils.register(ctx, LUNAR_MARE_CRATER, features.getOrThrow(GCYRConfiguredFeatures.LUNAR_MARE_CRATER),
                RarityFilter.onAverageOnceEvery(25),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                RandomOffsetPlacement.of(
                        BiasedToBottomInt.of(4, 12),
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(12), 2)
                                .add(ConstantInt.of(10), 3)
                                .add(ConstantInt.of(9), 2)
                                .build())),
                BiomeFilter.biome()
        );
        PlacementUtils.register(ctx, LUNAR_PLAINS_CRATER, features.getOrThrow(GCYRConfiguredFeatures.LUNAR_PLAINS_CRATER),
                RarityFilter.onAverageOnceEvery(7),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                RandomOffsetPlacement.of(
                        BiasedToBottomInt.of(4, 12),
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(12), 2)
                                .add(ConstantInt.of(10), 3)
                                .add(ConstantInt.of(9), 2)
                                .build())),
                BiomeFilter.biome()
        );
        PlacementUtils.register(ctx, MOON_ROCK, features.getOrThrow(GCYRConfiguredFeatures.MOON_ROCK),
                CountPlacement.of(2),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                BiomeFilter.biome()
        );
    }
    // spotless:on

    private static ResourceKey<PlacedFeature> register(String path) {
        return ResourceKey.create(Registries.PLACED_FEATURE, GCYR.id(path));
    }
}
