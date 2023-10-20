package argent_matter.gcys.data.fabric;

import argent_matter.gcys.GCyS;
import argent_matter.gcys.api.registries.GcysRegistries;
import argent_matter.gcys.api.registries.registrate.GcysSoundEntryProvider;
import argent_matter.gcys.common.data.GCySBiomes;
import argent_matter.gcys.common.data.GCySRecipeTypes;
import argent_matter.gcys.common.data.GCySSoundEntries;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.data.fabric.GTRegistriesDatapackGenerator;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.data.worldgen.biome.BiomeData;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class GCySDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        // registrate
        var rootPath = FabricLoader.getInstance().getGameDir().normalize().getParent().getParent();
        ExistingFileHelper helper = ExistingFileHelper.withResources(
                rootPath.resolve("common").resolve("src").resolve("main").resolve("resources"),
                rootPath.resolve("fabric").resolve("src").resolve("main").resolve("resources"));
        var pack = generator.createPack();
        GcysRegistries.REGISTRATE.setupDatagen(pack, helper);
        // sound
        pack.addProvider((FabricDataGenerator.Pack.Factory<DataProvider>) GcysSoundEntryProvider::new);
        // worldgen
        var set = Set.of(GCyS.MOD_ID);
        var registryAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        var registries = createProvider(registryAccess);
        pack.addProvider(bindRegistries((output, provider) -> new FixedGTRegistriesDatapackGenerator(
                output, registries, new RegistrySetBuilder()
                .add(Registries.BIOME, GCySBiomes::bootstrap),
                set, "Worldgen Data"), registries));
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