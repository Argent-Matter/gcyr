package argent_matter.gcys.data.fabric;

import argent_matter.gcys.api.registries.GcysRegistries;
import argent_matter.gcys.api.registries.registrate.GcysSoundEntryProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GcysDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        // registrate
        var rootPath = FabricLoader.getInstance().getGameDir().normalize().getParent().getParent();
        ExistingFileHelper helper = ExistingFileHelper.withResources(
                rootPath.resolve("common").resolve("src").resolve("main").resolve("resources"),
                rootPath.resolve("fabric").resolve("src").resolve("main").resolve("resources"));
        GcysRegistries.REGISTRATE.setupDatagen(generator, helper);
        // sound
        var provider = new GcysSoundEntryProvider(generator);
        generator.addProvider(true, provider);
    }
}