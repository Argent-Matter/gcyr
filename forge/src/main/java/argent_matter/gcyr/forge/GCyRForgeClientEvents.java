package argent_matter.gcyr.forge;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.client.data.GCyRParticleProviders;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GCyR.MOD_ID, value = Dist.CLIENT)
public class GCyRForgeClientEvents {

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        GCyRParticleProviders.init();
        GCyRParticleProviders.PARTICLES.forEach(event::registerSpriteSet);
    }

}
