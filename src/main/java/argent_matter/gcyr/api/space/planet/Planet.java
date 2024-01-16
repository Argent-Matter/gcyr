package argent_matter.gcyr.api.space.planet;

import argent_matter.gcyr.data.loader.PlanetData;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.function.Function;

public record Planet(String translation, ResourceLocation galaxy, ResourceLocation solarSystem,
                     ResourceKey<Level> level, ResourceKey<Level> orbitWorld, ResourceKey<Level> parentWorld,
                     int rocketTier, float gravity,
                     boolean hasAtmosphere, int daysInYear, float temperature, long solarPower,
                     boolean hasOxygen, int buttonColor) {

    public static final Codec<Planet> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("translation").forGetter(Planet::translation),
            ResourceLocation.CODEC.fieldOf("galaxy").forGetter(Planet::galaxy),
            ResourceLocation.CODEC.fieldOf("solar_system").forGetter(Planet::solarSystem),
            ResourceKey.codec(Registries.DIMENSION).fieldOf("world").forGetter(Planet::level),
            ResourceKey.codec(Registries.DIMENSION).fieldOf("orbit_world").forGetter(Planet::orbitWorld),
            ResourceKey.codec(Registries.DIMENSION).optionalFieldOf("parent_world").forGetter(Planet::getParentlevel),
            Codec.INT.fieldOf("rocket_tier").forGetter(Planet::rocketTier),
            Codec.FLOAT.fieldOf("gravity").forGetter(Planet::gravity),
            Codec.BOOL.fieldOf("has_atmosphere").forGetter(Planet::hasAtmosphere),
            Codec.INT.fieldOf("days_in_year").forGetter(Planet::daysInYear),
            Codec.FLOAT.fieldOf("temperature").forGetter(Planet::temperature),
            Codec.LONG.fieldOf("solar_power").forGetter(Planet::solarPower),
            Codec.BOOL.fieldOf("has_oxygen").forGetter(Planet::hasOxygen),
            Codec.INT.fieldOf("button_color").forGetter(Planet::buttonColor)
    ).apply(instance, Planet::new));

    public static final Codec<Planet> ID_CODEC = ResourceLocation.CODEC
                    .flatXmap(rl -> Optional.ofNullable(PlanetData.getPlanet(rl))
                                    .map(DataResult::success)
                                    .orElseGet(() -> DataResult.error(() -> "No Planet with id " + rl + " registered")),
                            obj -> Optional.ofNullable(PlanetData.getPlanetId(obj))
                                    .map(DataResult::success)
                                    .orElseGet(() -> DataResult.error(() -> "Planet " + obj + " not registered")));

    public static final Codec<Planet> CODEC = Codec.either(
            ID_CODEC,
            DIRECT_CODEC
    ).xmap(either -> either.map(Function.identity(), Function.identity()), Either::left);

    public Planet(String translation, ResourceLocation galaxy, ResourceLocation solarSystem, ResourceKey<Level> level, ResourceKey<Level> orbitWorld, Optional<ResourceKey<Level>> parentWorld, int rocketTier, float gravity, boolean hasAtmosphere, int daysInYear, float temperature, long solarPower, boolean hasOxygen, int buttonColor) {
        this(translation, galaxy, solarSystem, level, orbitWorld, parentWorld.orElse(null), rocketTier, gravity, hasAtmosphere, daysInYear, temperature, solarPower, hasOxygen, buttonColor);
    }

    private Optional<ResourceKey<Level>> getParentlevel() {
        return Optional.ofNullable(parentWorld);
    }
}