package argent_matter.gcyr.data.tags;

import argent_matter.gcyr.common.data.GCyRBiomes;
import argent_matter.gcyr.common.data.GCyRBlocks;
import argent_matter.gcyr.data.recipe.GCyRTags;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public interface IBiomeTagsProvider<T extends TagsProvider.TagAppender<Biome>> {

    default void generateTags() {
        tag(GCyRTags.IS_SPACE).addOptional(GCyRBiomes.SPACE.location());
        tag(GCyRTags.IS_MOON).addOptional(GCyRBiomes.MOON.location());
        tag(GCyRTags.IS_MERCURY).addOptional(GCyRBiomes.MERCURY_DELTAS.location());
        tag(GCyRTags.IS_MARS).addOptional(GCyRBiomes.MARTIAN_CANYON_CREEK.location()).addOptional(GCyRBiomes.MARTIAN_WASTELANDS.location()).addOptional(GCyRBiomes.MARTIAN_POLAR_CAPS.location());
        tag(GCyRTags.IS_VENUS).addOptional(GCyRBiomes.VENUS_WASTELANDS.location()).addOptional(GCyRBiomes.INFERNAL_VENUS_BARRENS.location());
    }

    @SafeVarargs
    private void tag(ResourceKey<Biome> biome, TagKey<Biome>... tags) {
        for (TagKey<Biome> key : tags) {
            this.tag(key).add(biome);
        }
    }

    @SafeVarargs
    private void tag(TagKey<Biome> key, ResourceKey<Biome>... biomes) {
        this.tag(key).add(biomes);
    }


    T tag(TagKey<Biome> tag);
}