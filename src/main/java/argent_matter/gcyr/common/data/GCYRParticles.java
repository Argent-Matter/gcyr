package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GCYRParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, GCYR.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DYSON_BEAM = PARTICLE_TYPES.register("dyson_beam", () -> new SimpleParticleType(true));

    public static void register(IEventBus modBus) {
        PARTICLE_TYPES.register(modBus);
    }
}
