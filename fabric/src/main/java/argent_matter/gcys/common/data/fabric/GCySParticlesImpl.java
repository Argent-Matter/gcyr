package argent_matter.gcys.common.data.fabric;

import com.gregtechceu.gtceu.api.registry.GTRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;

public class GCySParticlesImpl {
    public static SimpleParticleType register(ResourceLocation id, SimpleParticleType particleType) {
        return GTRegistries.register(Registry.PARTICLE_TYPE, id, particleType);
    }

}
