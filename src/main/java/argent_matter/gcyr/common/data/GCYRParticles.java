package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class GCYRParticles {

    public static final SimpleParticleType DYSON_BEAM = register(GCYR.id("dyson_beam"), new SimpleParticleType(true));

    public static void init() {

    }

    public static SimpleParticleType register(ResourceLocation id, SimpleParticleType particleType) {
        ForgeRegistries.PARTICLE_TYPES.register(id, particleType);
        return particleType;
    }
}
