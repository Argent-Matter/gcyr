package argent_matter.gcyr.api.space.planet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record Galaxy(
        ResourceLocation galaxy, ResourceLocation texture, int buttonColor, int scale) {
    public static final Codec<Galaxy> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("galaxy").forGetter(Galaxy::galaxy),
        ResourceLocation.CODEC.fieldOf("texture").forGetter(Galaxy::texture),
        Codec.INT.fieldOf("button_color").forGetter(Galaxy::buttonColor),
        Codec.INT.fieldOf("scale").orElse(1).forGetter(Galaxy::scale)
    ).apply(instance, Galaxy::new));
}