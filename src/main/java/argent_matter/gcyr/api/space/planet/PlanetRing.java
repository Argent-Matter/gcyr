package argent_matter.gcyr.api.space.planet;

import net.minecraft.resources.ResourceLocation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record PlanetRing(ResourceLocation galaxy, ResourceLocation solarSystem, ResourceLocation texture,
                         int speed, int scale, double radius) {

    // spotless:off
    public static final Codec<PlanetRing> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("galaxy").forGetter(PlanetRing::galaxy),
            ResourceLocation.CODEC.fieldOf("solar_system").forGetter(PlanetRing::solarSystem),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(PlanetRing::texture),
            Codec.INT.fieldOf("speed").forGetter(PlanetRing::speed),
            Codec.INT.fieldOf("scale").forGetter(PlanetRing::scale),
            Codec.DOUBLE.fieldOf("radius").forGetter(PlanetRing::radius)
    ).apply(instance, PlanetRing::new));
    // spotless:on
}
