package argent_matter.gcyr.forge;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.GCyRClient;
import argent_matter.gcyr.common.data.forge.GCyRDimensionTypesImpl;
import argent_matter.gcyr.data.loader.PlanetResources;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GCyR.MOD_ID)
public class GCyRForge {
    public GCyRForge() {
        GCyR.init();
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(this);
        GCyRDimensionTypesImpl.register(bus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> GCyRClient::init);
    }

    @SubscribeEvent
    public void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(PlanetResources.INSTANCE);
    }
}