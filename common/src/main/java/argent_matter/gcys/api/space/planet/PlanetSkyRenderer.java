package argent_matter.gcys.api.space.planet;

import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Locale;

public record PlanetSkyRenderer(ResourceKey<Level> dimension, PlanetSkyRenderer.StarsRenderer starsRenderer,
                                PlanetSkyRenderer.DimensionEffects effects,
                                PlanetSkyRenderer.CloudEffects cloudEffects,
                                PlanetSkyRenderer.WeatherEffects weatherEffects, int horizonAngle,
                                List<SkyObject> skyObjects) {

    public static final Codec<PlanetSkyRenderer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(Registry.DIMENSION_REGISTRY).fieldOf("world").forGetter(PlanetSkyRenderer::dimension),
            StarsRenderer.CODEC.fieldOf("stars").forGetter(PlanetSkyRenderer::starsRenderer),
            DimensionEffects.CODEC.fieldOf("dimension_effects").forGetter(PlanetSkyRenderer::effects),
            CloudEffects.CODEC.fieldOf("cloud_effects").forGetter(PlanetSkyRenderer::cloudEffects),
            WeatherEffects.CODEC.fieldOf("weather_effects").forGetter(PlanetSkyRenderer::weatherEffects),
            Codec.INT.fieldOf("horizon_angle").forGetter(PlanetSkyRenderer::horizonAngle),
            SkyObject.CODEC.listOf().fieldOf("sky_objects").forGetter(PlanetSkyRenderer::skyObjects)
    ).apply(instance, PlanetSkyRenderer::new));


    // Post-rendering.
    public enum RenderType implements StringRepresentable {
        STATIC, // Never moves.
        DYNAMIC, // Moves based on the time of day.
        SCALING, // Scales based on the position away from the player.
        DEBUG; // Only for testing while in a debug environment without restarting Minecraft.

        public static final Codec<RenderType> CODEC = StringRepresentable.fromEnum(RenderType::values);

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public enum CloudEffects implements StringRepresentable {
        NONE, VANILLA;

        public static final Codec<CloudEffects> CODEC = StringRepresentable.fromEnum(CloudEffects::values);

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public enum WeatherEffects implements StringRepresentable {
        NONE, VANILLA;

        public static final Codec<WeatherEffects> CODEC = StringRepresentable.fromEnum(WeatherEffects::values);

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }

    public enum DimensionEffectType implements StringRepresentable {
        SIMPLE, NONE, FOGGY_REVERSED, FOGGY, COLORED_HORIZON;

        public static final Codec<DimensionEffectType> CODEC = StringRepresentable.fromEnum(DimensionEffectType::values);

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        public boolean isFoggy() {
            return this == FOGGY || this == FOGGY_REVERSED;
        }
    }

    public record StarsRenderer(int fancyStars, int fastStars, boolean colouredStars, boolean daylightVisible) {

        public static final Codec<StarsRenderer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("fancy_count").forGetter(StarsRenderer::fancyStars),
                Codec.INT.fieldOf("fast_count").forGetter(StarsRenderer::fastStars),
                Codec.BOOL.fieldOf("colored_stars").forGetter(StarsRenderer::colouredStars),
                Codec.BOOL.fieldOf("daylight_visible").forGetter(StarsRenderer::daylightVisible)
        ).apply(instance, StarsRenderer::new));
    }

    public record SkyObject(
            ResourceLocation texture, boolean blending, RenderType renderType, float scale,
            int colour, Vector3f rotation) {

        public static final Codec<SkyObject> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("texture").forGetter(SkyObject::texture),
                Codec.BOOL.fieldOf("blending").forGetter(SkyObject::blending),
                RenderType.CODEC.fieldOf("render_type").forGetter(SkyObject::renderType),
                Codec.FLOAT.fieldOf("scale").forGetter(SkyObject::scale),
                Codec.INT.fieldOf("color").orElse(0xFFFFFFFF).forGetter(SkyObject::colour),
                Vector3f.CODEC.fieldOf("rotation").forGetter(SkyObject::rotation)
        ).apply(instance, SkyObject::new));
    }

    public record DimensionEffects(DimensionEffectType type, int colour) {

        public static final Codec<DimensionEffects> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                DimensionEffectType.CODEC.fieldOf("type").forGetter(DimensionEffects::type),
                Codec.INT.fieldOf("color").orElse(0xFFFFFFFF).forGetter(DimensionEffects::colour)
        ).apply(instance, DimensionEffects::new));
    }
}