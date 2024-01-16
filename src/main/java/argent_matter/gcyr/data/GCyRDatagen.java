package argent_matter.gcyr.data;

import argent_matter.gcyr.api.registries.GCyRRegistries;
import argent_matter.gcyr.data.lang.LangHandler;
import argent_matter.gcyr.data.tags.BlockTagLoader;
import argent_matter.gcyr.data.tags.FluidTagLoader;
import com.tterrag.registrate.providers.ProviderType;

public class GCyRDatagen {
    public static void init() {
        //GCyRRegistries.REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, TagsHandler::initItem);
        GCyRRegistries.REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, BlockTagLoader::init);
        GCyRRegistries.REGISTRATE.addDataGenerator(ProviderType.FLUID_TAGS, FluidTagLoader::init);
        GCyRRegistries.REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
    }
}
