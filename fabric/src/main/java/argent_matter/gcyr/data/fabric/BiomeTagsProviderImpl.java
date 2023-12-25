package argent_matter.gcyr.data.fabric;

import argent_matter.gcyr.data.tags.IBiomeTagsProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class BiomeTagsProviderImpl extends FabricTagProvider<Biome> implements IBiomeTagsProvider<FabricTagProvider<Biome>.FabricTagBuilder> {

    protected BiomeTagsProviderImpl(FabricDataOutput dataGenerator, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataGenerator, Registries.BIOME, registriesFuture);
    }

    @Override
    public FabricTagProvider<Biome>.FabricTagBuilder tag(TagKey<Biome> tag) {
        return this.getOrCreateTagBuilder(tag);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        generateTags();
    }
}