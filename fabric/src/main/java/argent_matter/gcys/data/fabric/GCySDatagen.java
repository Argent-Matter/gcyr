package argent_matter.gcys.data.fabric;

import argent_matter.gcys.GCyS;
import argent_matter.gcys.api.registries.GcysRegistries;
import com.gregtechceu.gtceu.api.registry.registrate.SoundEntryBuilder;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GCySDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        // registrate
        var rootPath = FabricLoader.getInstance().getGameDir().normalize().getParent().getParent();
        ExistingFileHelper helper = ExistingFileHelper.withResources(
                rootPath.resolve("common").resolve("src").resolve("main").resolve("resources"),
                rootPath.resolve("fabric").resolve("src").resolve("main").resolve("resources"));
        GcysRegistries.REGISTRATE.setupDatagen(generator, helper);
        // sound
        var provider = new SoundEntryBuilder.SoundEntryProvider(generator, GCyS.MOD_ID);
        generator.addProvider(true, provider);
    }
}