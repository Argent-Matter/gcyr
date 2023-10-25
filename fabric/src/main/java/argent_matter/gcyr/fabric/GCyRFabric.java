package argent_matter.gcyr.fabric;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.api.capability.GCyRCapabilityHelper;
import argent_matter.gcyr.api.capability.IDysonSystem;
import argent_matter.gcyr.common.data.GCyRMachines;
import argent_matter.gcyr.common.data.GCyRNetworking;
import argent_matter.gcyr.common.data.GCyRRecipeTypes;
import argent_matter.gcyr.common.data.GCyRSoundEntries;
import argent_matter.gcyr.common.networking.s2c.PacketSyncDysonSphereStatus;
import argent_matter.gcyr.data.loader.PlanetData;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.lowdragmc.lowdraglib.Platform;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class GCyRFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        if (Platform.isDatagen()) { // Force these to load for data generation
            ConfigHolder.init(); // force init GTCEu ConfigHolder
            GCyRSoundEntries.init();
            GCyRRecipeTypes.init();
            GCyRMachines.init();
        }
        GCyR.init();


        // register satellite ticking
        ServerTickEvents.START_WORLD_TICK.register((serverLevel) -> GCyR.onLevelTick(serverLevel, true));

        ServerTickEvents.END_WORLD_TICK.register((serverLevel) -> GCyR.onLevelTick(serverLevel, false));

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new IdentifiableResourceReloadListener() {
            private final PlanetData data = new PlanetData();
            @Override
            public ResourceLocation getFabricId() {
                return GCyR.id("planet_data");
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
                return data.reload(preparationBarrier, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor);
            }
        });

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            IDysonSystem system = GCyRCapabilityHelper.getDysonSystem(player.serverLevel());
            if (system != null && system.isDysonSphereActive() && !system.activeDysonSphere().isCollapsed()) {
                GCyRNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(true), player);
            } else {
                GCyRNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(false), player);
            }
        });

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof ServerPlayer player) {
                IDysonSystem system = GCyRCapabilityHelper.getDysonSystem(player.serverLevel());
                if (system != null && system.isDysonSphereActive() && !system.activeDysonSphere().isCollapsed()) {
                    GCyRNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(true), player);
                } else {
                    GCyRNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(false), player);
                }
            }
        });
    }
}