package argent_matter.gcyr.forge;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.client.data.GCYRParticleProviders;
import argent_matter.gcyr.client.dimension.ClientModSkies;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GCYR.MOD_ID, value = Dist.CLIENT)
public class GCYRForgeClientEvents {

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        GCYRParticleProviders.init();
        GCYRParticleProviders.PARTICLES.forEach(event::registerSpriteSet);
    }

    @SubscribeEvent
    public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
        for (var entry : ClientModSkies.DIMENSION_SPECIAL_EFFECTS.entrySet()) {
            event.register(entry.getKey(), entry.getValue());
        }
    }
}
