package argent_matter.gcys.data;

import argent_matter.gcys.api.registries.GcysRegistries;
import argent_matter.gcys.data.lang.LangHandler;
import argent_matter.gcys.data.tags.BlockTagLoader;
import argent_matter.gcys.data.tags.FluidTagLoader;
import com.gregtechceu.gtceu.data.GregTechDatagen;
import com.tterrag.registrate.providers.ProviderType;

public class GCySDatagen {
    public static void init() {
        //GcysRegistries.REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, TagsHandler::initItem);
        GcysRegistries.REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, BlockTagLoader::init);
        GcysRegistries.REGISTRATE.addDataGenerator(ProviderType.FLUID_TAGS, FluidTagLoader::init);
        GcysRegistries.REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
        GregTechDatagen.init();
    }
}
