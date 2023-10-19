package argent_matter.gcys.fabric.mixin;

import argent_matter.gcys.client.data.GcysParticleProviders;
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
    public void gcys$registerParticles(CallbackInfo ci) {
        GcysParticleProviders.init();
        GcysParticleProviders.PARTICLES.forEach(this::register);
    }
}
