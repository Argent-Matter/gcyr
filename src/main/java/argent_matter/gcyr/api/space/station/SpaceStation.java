package argent_matter.gcyr.api.space.station;

import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.util.Vec2i;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.minecraft.world.level.border.WorldBorder;

@Accessors(fluent = true)
public class SpaceStation {
    public static final int ID_MAX = 31, ID_EMPTY = Integer.MIN_VALUE, BLOCK_MULTIPLIER = 256, SIZE_BLOCKS = BLOCK_MULTIPLIER * BLOCK_MULTIPLIER;
    public static final Codec<SpaceStation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Planet.ID_CODEC.fieldOf("planet").forGetter(SpaceStation::orbitPlanet),
            Vec2i.CODEC.fieldOf("pos").forGetter(SpaceStation::position)
    ).apply(instance, SpaceStation::new));

    @Getter
    private final Planet orbitPlanet;
    @Getter
    private final Vec2i position;
    @Getter
    private final WorldBorder border;

    public SpaceStation(Planet orbitPlanet, Vec2i position) {
        this.orbitPlanet = orbitPlanet;
        this.position = position;

        this.border = new WorldBorder();
        border.setCenter(position.x() * BLOCK_MULTIPLIER, position.y() * BLOCK_MULTIPLIER);
        border.setSize(BLOCK_MULTIPLIER);
    }

}
