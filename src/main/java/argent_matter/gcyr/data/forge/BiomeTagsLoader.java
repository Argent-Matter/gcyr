package argent_matter.gcyr.data.forge;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.data.GCyRBiomes;
import argent_matter.gcyr.data.recipe.GCyRTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BiomeTagsLoader extends BiomeTagsProvider {

    public BiomeTagsLoader(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, registries, GCyR.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(GCyRTags.IS_SPACE).addOptional(GCyRBiomes.SPACE.location());
        tag(GCyRTags.IS_MOON).addOptional(GCyRBiomes.MOON.location());
        tag(GCyRTags.IS_MERCURY).addOptional(GCyRBiomes.MERCURY_DELTAS.location());
        tag(GCyRTags.IS_MARS).addOptional(GCyRBiomes.MARTIAN_CANYON_CREEK.location()).addOptional(GCyRBiomes.MARTIAN_WASTELANDS.location()).addOptional(GCyRBiomes.MARTIAN_POLAR_CAPS.location());
        tag(GCyRTags.IS_VENUS).addOptional(GCyRBiomes.VENUS_ERODED_PLAINS.location()).addOptional(GCyRBiomes.VENUS_ERODED_PLAINS.location());
    }
}
