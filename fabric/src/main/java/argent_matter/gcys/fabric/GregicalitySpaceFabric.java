package argent_matter.gcys.fabric;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.data.loader.PlanetData;
import com.gregtechceu.gtceu.data.loader.fabric.OreDataLoaderImpl;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class GregicalitySpaceFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        GregicalitySpace.init();


        // register satellite ticking
        ServerTickEvents.START_WORLD_TICK.register((serverLevel) -> {
            if (!serverLevel.dimensionType().hasCeiling()) {
                var sat = GcysCapabilityHelper.getSatellites(serverLevel);
                if (sat != null) sat.tickSatellites();
            }
        });

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new IdentifiableResourceReloadListener() {
            private final PlanetData data = new PlanetData();
            @Override
            public ResourceLocation getFabricId() {
                return GregicalitySpace.id("planet_data");
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
                return data.reload(preparationBarrier, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor);
            }
        });
    }
}