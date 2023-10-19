package argent_matter.gcys.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class DysonBeamParticle extends SimpleAnimatedParticle {
    protected DysonBeamParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
        super(level, x, y, z, sprites, 0.0f);
        this.quadSize *= 4.0f;
        this.lifetime = 100;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        if (this.age++ > this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);
        }
    }

    @Override
    public int getLightColor(float partialTick) {
        return 0xFFFFFF;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new DysonBeamParticle(level, x, y, z, this.sprites);
        }
    }
}
