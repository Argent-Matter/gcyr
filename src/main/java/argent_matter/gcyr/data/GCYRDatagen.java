package argent_matter.gcyr.data;

import argent_matter.gcyr.api.registries.GCYRRegistries;
import argent_matter.gcyr.data.lang.LangHandler;
import argent_matter.gcyr.data.tags.BlockTagLoader;
import argent_matter.gcyr.data.tags.FluidTagLoader;
import argent_matter.gcyr.data.tags.ItemTagLoader;
import com.tterrag.registrate.providers.ProviderType;

public class GCYRDatagen {
    public static void init() {
        GCYRRegistries.REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, ItemTagLoader::init);
        GCYRRegistries.REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, BlockTagLoader::init);
        GCYRRegistries.REGISTRATE.addDataGenerator(ProviderType.FLUID_TAGS, FluidTagLoader::init);
        GCYRRegistries.REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
    }
}
