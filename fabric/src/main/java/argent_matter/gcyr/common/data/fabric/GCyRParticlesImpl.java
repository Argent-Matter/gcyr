package argent_matter.gcyr.common.data.fabric;

import com.gregtechceu.gtceu.api.registry.GTRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class GCyRParticlesImpl {
    public static SimpleParticleType register(ResourceLocation id, SimpleParticleType particleType) {
        return GTRegistries.register(BuiltInRegistries.PARTICLE_TYPE, id, particleType);
    }

}
