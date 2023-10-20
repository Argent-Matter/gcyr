package argent_matter.gcys.forge;

import argent_matter.gcys.GCyS;
import argent_matter.gcys.GCySClient;
import argent_matter.gcys.common.data.forge.GCySBiomesImpl;
import argent_matter.gcys.common.data.forge.GCySDimensionTypesImpl;
import argent_matter.gcys.data.loader.PlanetResources;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GCyS.MOD_ID)
public class GCySForge {
    public GCySForge() {
        GCyS.init();
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(this);
        GCySBiomesImpl.register(bus);
        GCySDimensionTypesImpl.register(bus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> GCySClient::init);
    }

    @SubscribeEvent
    public void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(PlanetResources.INSTANCE);
    }
}