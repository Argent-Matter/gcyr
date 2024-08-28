package argent_matter.gcyr.data.forge;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.GCYRBiomes;
import argent_matter.gcyr.common.item.armor.trim.GCYRTrimMaterials;
import argent_matter.gcyr.common.item.armor.trim.GCYRTrimPatterns;
import com.gregtechceu.gtceu.api.registry.registrate.SoundEntryBuilder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput output = generator.getPackOutput();
        var provider = event.getLookupProvider();
        if (event.includeServer()) {
            var set = Set.of(GCYR.MOD_ID);
            generator.addProvider(true, new BiomeTagsLoader(output, provider, existingFileHelper));
            generator.addProvider(true, new SoundEntryBuilder.SoundEntryProvider(output, GCYR.MOD_ID));
            generator.addProvider(true, new DatapackBuiltinEntriesProvider(
                    output, provider, new RegistrySetBuilder()
                    .add(Registries.BIOME, GCYRBiomes::bootstrap)
                    .add(Registries.TRIM_PATTERN, GCYRTrimPatterns::bootstrap)
                    .add(Registries.TRIM_MATERIAL, GCYRTrimMaterials::bootstrap),
                    set));
        }
    }
}
