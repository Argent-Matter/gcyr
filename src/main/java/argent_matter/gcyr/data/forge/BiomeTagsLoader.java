package argent_matter.gcyr.data.forge;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.GCYRBiomes;
import argent_matter.gcyr.data.recipe.GCYRTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BiomeTagsLoader extends BiomeTagsProvider {

    public BiomeTagsLoader(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, registries, GCYR.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(GCYRTags.IS_SPACE).addOptional(GCYRBiomes.SPACE.location());
        tag(GCYRTags.IS_MOON).addOptional(GCYRBiomes.MOON.location());
        tag(GCYRTags.IS_MERCURY).addOptional(GCYRBiomes.MERCURY_DELTAS.location());
        tag(GCYRTags.IS_MARS).addOptional(GCYRBiomes.MARTIAN_CANYON_CREEK.location()).addOptional(GCYRBiomes.MARTIAN_WASTELANDS.location()).addOptional(GCYRBiomes.MARTIAN_POLAR_CAPS.location());
        tag(GCYRTags.IS_VENUS).addOptional(GCYRBiomes.VENUS_ERODED_PLAINS.location()).addOptional(GCYRBiomes.VENUS_ERODED_PLAINS.location());
    }
}
