package argent_matter.gcyr.data.forge;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.data.GCyRBiomes;
import com.gregtechceu.gtceu.api.registry.registrate.SoundEntryBuilder;
import com.gregtechceu.gtceu.data.forge.GTRegistriesDatapackGenerator;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.data.worldgen.biome.BiomeData;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        var registryAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        var registries = createProvider(registryAccess);
        if (event.includeServer()) {
            var set = Set.of(GCyR.MOD_ID);
            generator.addProvider(true, new SoundEntryBuilder.SoundEntryProvider(generator.getPackOutput(), GCyR.MOD_ID));
            generator.addProvider(true, bindRegistries((output, provider) -> new GTRegistriesDatapackGenerator(
                    output, registries, new RegistrySetBuilder()
                    .add(Registries.BIOME, GCyRBiomes::bootstrap),
                    set, "Worldgen Data"), registries));
        }
    }

    private static <T extends DataProvider> DataProvider.Factory<T> bindRegistries(
            BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, T> factory,
            CompletableFuture<HolderLookup.Provider> factories) {
        return packOutput -> factory.apply(packOutput, factories);
    }

    private static CompletableFuture<HolderLookup.Provider> createProvider(RegistryAccess registryAccess) {

        var vanillaLookup = CompletableFuture.supplyAsync(VanillaRegistries::createLookup, Util.backgroundExecutor());

        return vanillaLookup.thenApply(provider -> {
            var builder = new RegistrySetBuilder()
                    .add(Registries.NOISE, NoiseData::bootstrap)
                    .add(Registries.BIOME, BiomeData::bootstrap);

            return builder.buildPatch(registryAccess, provider);
        });
    }
}