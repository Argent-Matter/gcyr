package argent_matter.gcys.api.space.station;

import argent_matter.gcys.api.space.planet.Planet;
import argent_matter.gcys.util.Vec2i;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SpaceStation(Planet orbitPlanet, Vec2i position) {

    public static final Codec<SpaceStation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Planet.ID_CODEC.fieldOf("planet").forGetter(SpaceStation::orbitPlanet),
            Vec2i.CODEC.fieldOf("pos").forGetter(SpaceStation::position)
    ).apply(instance, SpaceStation::new));

    public static final int ID_MAX = 31, ID_EMPTY = Integer.MIN_VALUE;

}
