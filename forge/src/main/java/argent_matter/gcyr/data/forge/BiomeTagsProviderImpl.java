package argent_matter.gcyr.data.forge;

import argent_matter.gcyr.data.tags.IBiomeTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class BiomeTagsProviderImpl extends BiomeTagsProvider implements IBiomeTagsProvider<TagsProvider.TagAppender<Biome>> {

    public BiomeTagsProviderImpl(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.generateTags();
    }

    @Override
    public TagAppender<Biome> tag(TagKey<Biome> tag) {
        return super.tag(tag);
    }
}
