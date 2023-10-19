package argent_matter.gcys.common.data.forge;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class GcysParticlesImpl {
    public static SimpleParticleType register(ResourceLocation id, SimpleParticleType particleType) {
        ForgeRegistries.PARTICLE_TYPES.register(id, particleType);
        return particleType;
    }

}
