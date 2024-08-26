package argent_matter.gcyr.client.data;

import argent_matter.gcyr.client.particle.DysonBeamParticle;
import argent_matter.gcyr.common.data.GCYRParticles;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.HashMap;
import java.util.Map;

public class GCYRParticleProviders {

    public static final Map<ParticleType<SimpleParticleType>, ParticleEngine.SpriteParticleRegistration<SimpleParticleType>> PARTICLES = new HashMap<>();

    public static void init() {
        PARTICLES.put(GCYRParticles.DYSON_BEAM, DysonBeamParticle.Provider::new);
    }
}
