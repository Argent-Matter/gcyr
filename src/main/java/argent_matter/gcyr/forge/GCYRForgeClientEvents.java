package argent_matter.gcyr.forge;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.client.dimension.ClientModSkies;
import argent_matter.gcyr.client.particle.DysonBeamParticle;
import argent_matter.gcyr.common.data.GCYRParticles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@SuppressWarnings("unused")
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = GCYR.MOD_ID, value = Dist.CLIENT)
public class GCYRForgeClientEvents {

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(GCYRParticles.DYSON_BEAM.get(), DysonBeamParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
        for (var entry : ClientModSkies.DIMENSION_SPECIAL_EFFECTS.entrySet()) {
            event.register(entry.getKey(), entry.getValue());
        }
    }
}
