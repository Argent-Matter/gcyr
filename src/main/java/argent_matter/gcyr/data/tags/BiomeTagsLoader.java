package argent_matter.gcyr.data.tags;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.tag.GCYRTags;
import argent_matter.gcyr.common.data.worldgen.GCYRBiomes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;

import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

public class BiomeTagsLoader extends BiomeTagsProvider {

    public BiomeTagsLoader(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries,
                           @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, registries, GCYR.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(GCYRTags.Biomes.IS_SPACE).add(GCYRBiomes.SPACE);
        tag(GCYRTags.Biomes.IS_MOON).add(GCYRBiomes.LUNAR_PLAINS, GCYRBiomes.LUNAR_MARE);
        tag(GCYRTags.Biomes.IS_MERCURY).add(GCYRBiomes.MERCURY_DELTAS);
        tag(GCYRTags.Biomes.IS_MARS).add(GCYRBiomes.MARTIAN_CANYON_CREEK, GCYRBiomes.MARTIAN_WASTELANDS,
                GCYRBiomes.MARTIAN_POLAR_CAPS);
        tag(GCYRTags.Biomes.IS_VENUS).add(GCYRBiomes.VENUS_ERODED_PLAINS, GCYRBiomes.VENUS_BARREN_PLAINS);
    }
}
