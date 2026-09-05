package argent_matter.gcyr.api.space.planet;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import org.joml.Vector3f;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record PlanetSkyRenderer(ResourceKey<Level> dimension, Optional<ResourceLocation> skyShaderLocation,
                                PlanetSkyRenderer.StarsRenderer starsRenderer,
                                PlanetSkyRenderer.DimensionEffects effects,
                                PlanetSkyRenderer.CloudEffects cloudEffects,
                                PlanetSkyRenderer.WeatherEffects weatherEffects, int horizonAngle,
                                boolean fullSky, List<SkyObject> skyObjects) {

    // spotless:off
    public static final Codec<PlanetSkyRenderer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(PlanetSkyRenderer::dimension),
            ResourceLocation.CODEC.optionalFieldOf("custom_shader").forGetter(PlanetSkyRenderer::skyShaderLocation),
            StarsRenderer.CODEC.fieldOf("stars").forGetter(PlanetSkyRenderer::starsRenderer),
            DimensionEffects.CODEC.fieldOf("dimension_effects").forGetter(PlanetSkyRenderer::effects),
            CloudEffects.CODEC.fieldOf("cloud_effects").forGetter(PlanetSkyRenderer::cloudEffects),
            WeatherEffects.CODEC.fieldOf("weather_effects").forGetter(PlanetSkyRenderer::weatherEffects),
            Codec.INT.fieldOf("horizon_angle").forGetter(PlanetSkyRenderer::horizonAngle),
            Codec.BOOL.optionalFieldOf("full_sky", false).forGetter(PlanetSkyRenderer::fullSky),
            SkyObject.CODEC.listOf().optionalFieldOf("sky_objects", Collections.emptyList()).forGetter(PlanetSkyRenderer::skyObjects)
    ).apply(instance, PlanetSkyRenderer::new));
    // spotless:on

    // Post-rendering.
    public enum RenderType implements StringRepresentable {

        STATIC("static"), // Never moves.
        DYNAMIC("dynamic"), // Moves based on the time of day.
        SCALING("scaling"), // Scales based on the position away from the player.
        DEBUG("debug"); // Only for testing while in a debug environment without restarting Minecraft.

        public static final Codec<RenderType> CODEC = StringRepresentable.fromEnum(RenderType::values);

        private final String name;

        RenderType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public enum CloudEffects implements StringRepresentable {

        NONE("none"),
        VANILLA("vanilla");

        public static final Codec<CloudEffects> CODEC = StringRepresentable.fromEnum(CloudEffects::values);

        private final String name;

        CloudEffects(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public enum WeatherEffects implements StringRepresentable {

        NONE("none"),
        VANILLA("vanilla");

        public static final Codec<WeatherEffects> CODEC = StringRepresentable.fromEnum(WeatherEffects::values);

        private final String name;

        WeatherEffects(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public enum DimensionEffectType implements StringRepresentable {

        SIMPLE("simple"),
        NONE("none"),
        FOGGY_REVERSED("foggy_reversed"),
        FOGGY("foggy"),
        COLORED_HORIZON("colored_horizon");

        public static final Codec<DimensionEffectType> CODEC = StringRepresentable
                .fromEnum(DimensionEffectType::values);

        private final String name;

        DimensionEffectType(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public boolean isFoggy() {
            return this == FOGGY || this == FOGGY_REVERSED;
        }
    }

    public record StarsRenderer(int fancyStars, int fastStars, boolean colouredStars, boolean daylightVisible) {

        // spotless:off
        public static final Codec<StarsRenderer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("fancy_count").forGetter(StarsRenderer::fancyStars),
                Codec.INT.fieldOf("fast_count").forGetter(StarsRenderer::fastStars),
                Codec.BOOL.fieldOf("colored_stars").forGetter(StarsRenderer::colouredStars),
                Codec.BOOL.fieldOf("daylight_visible").forGetter(StarsRenderer::daylightVisible)
        ).apply(instance, StarsRenderer::new));
        // spotless:on
    }

    public record SkyObject(ResourceLocation texture, boolean blending, RenderType renderType,
                            float scale, int color, Vector3f rotation) {

        // spotless:off
        public static final Codec<SkyObject> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("texture").forGetter(SkyObject::texture),
                Codec.BOOL.fieldOf("blending").forGetter(SkyObject::blending),
                RenderType.CODEC.fieldOf("render_type").forGetter(SkyObject::renderType),
                Codec.FLOAT.fieldOf("scale").forGetter(SkyObject::scale),
                Codec.INT.fieldOf("color").orElse(0xFFFFFFFF).forGetter(SkyObject::color),
                ExtraCodecs.VECTOR3F.fieldOf("rotation").forGetter(SkyObject::rotation)
        ).apply(instance, SkyObject::new));
        // spotless:on
    }

    public record DimensionEffects(DimensionEffectType type, int color) {

        // spotless:off
        public static final Codec<DimensionEffects> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                DimensionEffectType.CODEC.fieldOf("type").forGetter(DimensionEffects::type),
                Codec.INT.fieldOf("color").orElse(0xFFFFFFFF).forGetter(DimensionEffects::color)
        ).apply(instance, DimensionEffects::new));
        // spotless:on
    }
}
