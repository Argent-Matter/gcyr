package argent_matter.gcys.client.data;

import argent_matter.gcys.client.particle.DysonBeamParticle;
import argent_matter.gcys.common.data.GCySParticles;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.HashMap;
import java.util.Map;

public class GcysParticleProviders {

    public static final Map<ParticleType<SimpleParticleType>, ParticleEngine.SpriteParticleRegistration<SimpleParticleType>> PARTICLES = new HashMap<>();

    public static void init() {
        PARTICLES.put(GCySParticles.DYSON_BEAM, DysonBeamParticle.Provider::new);
    }
}
