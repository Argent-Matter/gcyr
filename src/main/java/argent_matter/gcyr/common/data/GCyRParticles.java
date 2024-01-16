package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCyR;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class GCyRParticles {

    public static final SimpleParticleType DYSON_BEAM = register(GCyR.id("dyson_beam"), new SimpleParticleType(true));

    public static void init() {

    }

    public static SimpleParticleType register(ResourceLocation id, SimpleParticleType particleType) {
        ForgeRegistries.PARTICLE_TYPES.register(id, particleType);
        return particleType;
    }
}
