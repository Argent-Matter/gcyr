package argent_matter.gcys.common.data;

import argent_matter.gcys.GCyS;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;

public class GCySParticles {

    public static final SimpleParticleType DYSON_BEAM = register(GCyS.id("dyson_beam"), new SimpleParticleType(true));

    public static void init() {

    }

    @ExpectPlatform
    public static SimpleParticleType register(ResourceLocation id, SimpleParticleType particleType) {
        throw new AssertionError();
    }
}
