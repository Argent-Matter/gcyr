package argent_matter.gcys.api.space.planet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record SolarSystem(
        ResourceLocation galaxy, ResourceLocation solarSystem, ResourceLocation sun, int sunScale,
        int buttonColor, int ringColour) {

    public static final Codec<SolarSystem> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("galaxy").forGetter(SolarSystem::galaxy),
            ResourceLocation.CODEC.fieldOf("solar_system.json").forGetter(SolarSystem::solarSystem),
            ResourceLocation.CODEC.fieldOf("sun").forGetter(SolarSystem::sun),
            Codec.INT.fieldOf("sun_scale").forGetter(SolarSystem::sunScale),
            Codec.INT.fieldOf("button_color").forGetter(SolarSystem::buttonColor),
            Codec.INT.fieldOf("ring_color").forGetter(SolarSystem::ringColour)
    ).apply(instance, SolarSystem::new));

}