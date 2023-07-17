package argent_matter.gcys.data;

import argent_matter.gcys.api.registries.GcysRegistries;
import argent_matter.gcys.data.lang.LangHandler;
import argent_matter.gcys.data.tags.BlockTagLoader;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.data.GregTechDatagen;
import com.gregtechceu.gtceu.data.tags.TagsHandler;
import com.tterrag.registrate.providers.ProviderType;

public class GcysDatagen {
    public static void init() {
        //GcysRegistries.REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, TagsHandler::initItem);
        GcysRegistries.REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, BlockTagLoader::init);
        GcysRegistries.REGISTRATE.addDataGenerator(ProviderType.LANG, LangHandler::init);
        GregTechDatagen.init();
    }
}
