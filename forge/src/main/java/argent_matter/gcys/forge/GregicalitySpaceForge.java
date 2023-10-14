package argent_matter.gcys.forge;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.GregicalitySpaceClient;
import argent_matter.gcys.common.data.GcysKeyMappings;
import argent_matter.gcys.common.data.forge.GcysBiomesImpl;
import argent_matter.gcys.common.data.forge.GcysDimensionTypesImpl;
import argent_matter.gcys.data.loader.PlanetResources;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GregicalitySpace.MOD_ID)
public class GregicalitySpaceForge {
    public GregicalitySpaceForge() {
        GregicalitySpace.init();
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        GcysBiomesImpl.register(bus);
        GcysDimensionTypesImpl.register(bus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> GregicalitySpaceClient::init);
    }

    @SubscribeEvent
    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(PlanetResources.INSTANCE);
    }
}