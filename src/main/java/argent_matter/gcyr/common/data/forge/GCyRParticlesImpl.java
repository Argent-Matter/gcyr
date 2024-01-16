package argent_matter.gcyr.common.data.forge;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class GCyRParticlesImpl {
    public static SimpleParticleType register(ResourceLocation id, SimpleParticleType particleType) {
        ForgeRegistries.PARTICLE_TYPES.register(id, particleType);
        return particleType;
    }

}
