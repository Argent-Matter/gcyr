package argent_matter.gcyr.data.fabric;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.api.registries.GCyRRegistries;
import com.gregtechceu.gtceu.api.registry.registrate.SoundEntryBuilder;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GCyRDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        // registrate
        var rootPath = FabricLoader.getInstance().getGameDir().normalize().getParent().getParent();
        ExistingFileHelper helper = ExistingFileHelper.withResources(
                rootPath.resolve("common").resolve("src").resolve("main").resolve("resources"),
                rootPath.resolve("fabric").resolve("src").resolve("main").resolve("resources"));
        GCyRRegistries.REGISTRATE.setupDatagen(generator, helper);
        // sound
        generator.addProvider(true, new SoundEntryBuilder.SoundEntryProvider(generator, GCyR.MOD_ID));
    }
}