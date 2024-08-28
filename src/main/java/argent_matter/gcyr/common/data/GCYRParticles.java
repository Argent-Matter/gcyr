package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class GCYRParticles {

    public static final SimpleParticleType DYSON_BEAM = register(GCYR.id("dyson_beam"), new SimpleParticleType(true));

    public static void init() {

    }

    public static SimpleParticleType register(ResourceLocation id, SimpleParticleType particleType) {
        BuiltInRegistries.PARTICLE_TYPE.register(id, particleType);
        return particleType;
    }
}
