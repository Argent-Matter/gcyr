package argent_matter.gcyr.fabric.mixin;

import argent_matter.gcyr.client.data.GCyRParticleProviders;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public abstract class ParticleEngineMixin {

    @Shadow
    public abstract <T extends ParticleOptions> void register(ParticleType<T> particleType, ParticleEngine.SpriteParticleRegistration<T> particleMetaFactory);

    @Inject(method = "registerProviders", at = @At("TAIL"))
    public void gcyr$registerParticles(CallbackInfo ci) {
        GCyRParticleProviders.init();
        GCyRParticleProviders.PARTICLES.forEach(this::register);
    }
}
