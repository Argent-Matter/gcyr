package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCyR;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;

public class GCyRParticles {

    public static final SimpleParticleType DYSON_BEAM = register(GCyR.id("dyson_beam"), new SimpleParticleType(true));

    public static void init() {

    }

    @ExpectPlatform
    public static SimpleParticleType register(ResourceLocation id, SimpleParticleType particleType) {
        throw new AssertionError();
    }
}
