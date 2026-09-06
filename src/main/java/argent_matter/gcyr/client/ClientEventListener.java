package argent_matter.gcyr.client;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.client.dimension.ClientModSkies;
import argent_matter.gcyr.client.particle.DysonBeamParticle;
import argent_matter.gcyr.common.data.client.GCYRParticles;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GCYR.MOD_ID, value = Dist.CLIENT)
public class ClientEventListener {

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(GCYRParticles.DYSON_BEAM, DysonBeamParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event) {
        for (var entry : ClientModSkies.DIMENSION_SPECIAL_EFFECTS.entrySet()) {
            event.register(entry.getKey(), entry.getValue());
        }
    }
}
