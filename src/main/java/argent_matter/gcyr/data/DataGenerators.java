package argent_matter.gcyr.data;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.dimension.GCYRDimensionTypes;
import argent_matter.gcyr.common.data.dimension.GCYRDimensions;
import argent_matter.gcyr.common.data.item.GCYRTrimMaterials;
import argent_matter.gcyr.common.data.item.GCYRTrimPatterns;
import argent_matter.gcyr.common.data.worldgen.biome.GCYRBiomes;
import argent_matter.gcyr.common.data.worldgen.feature.GCYRConfiguredFeatures;
import argent_matter.gcyr.common.data.worldgen.feature.GCYRPlacedFeatures;
import argent_matter.gcyr.data.tags.BiomeTagsLoader;

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

        var registries = event.getLookupProvider();
        if (event.includeServer()) {
            var provider = generator.addProvider(true, new DatapackBuiltinEntriesProvider(output, registries,
                    new RegistrySetBuilder()
                            .add(Registries.TRIM_PATTERN, GCYRTrimPatterns::bootstrap)
                            .add(Registries.TRIM_MATERIAL, GCYRTrimMaterials::bootstrap)
                            .add(Registries.CONFIGURED_FEATURE, GCYRConfiguredFeatures::bootstrap)
                            .add(Registries.PLACED_FEATURE, GCYRPlacedFeatures::bootstrap)
                            .add(Registries.BIOME, GCYRBiomes::bootstrap)
                             // .add(Registries.NOISE_SETTINGS, GCYRNoiseSettings::bootstrap)
                            .add(Registries.DIMENSION_TYPE, GCYRDimensionTypes::bootstrap)
                            .add(Registries.LEVEL_STEM, GCYRDimensions::bootstrap)
                    ,
                    Set.of(GCYR.MOD_ID)));
            registries = provider.getRegistryProvider();

            generator.addProvider(true, new BiomeTagsLoader(output, registries, existingFileHelper));
        }

        if (event.includeClient()) {
            generator.addProvider(true, new SoundEntryBuilder.SoundEntryProvider(output, GCYR.MOD_ID));
        }
    }
}
