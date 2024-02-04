package argent_matter.gcyr.data.forge;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.data.GCyRBiomes;
import com.gregtechceu.gtceu.api.registry.registrate.SoundEntryBuilder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput output = generator.getPackOutput();
        var provider = event.getLookupProvider();
        if (event.includeServer()) {
            var set = Set.of(GCyR.MOD_ID);
            generator.addProvider(true, new BiomeTagsLoader(output, provider, existingFileHelper));
            generator.addProvider(true, new SoundEntryBuilder.SoundEntryProvider(output, GCyR.MOD_ID));
            generator.addProvider(true, new DatapackBuiltinEntriesProvider(
                    output, provider, new RegistrySetBuilder()
                    .add(Registries.BIOME, GCyRBiomes::bootstrap),
                    set));
        }
    }
}