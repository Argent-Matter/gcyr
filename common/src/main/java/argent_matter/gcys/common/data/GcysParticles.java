package argent_matter.gcys.common.data;

import argent_matter.gcys.GregicalitySpace;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;

public class GcysParticles {

    public static final SimpleParticleType DYSON_BEAM = register(GregicalitySpace.id("dyson_beam"), new SimpleParticleType(true));

    public static void init() {

    }

    @ExpectPlatform
    public static SimpleParticleType register(ResourceLocation id, SimpleParticleType particleType) {
        throw new AssertionError();
    }
}
