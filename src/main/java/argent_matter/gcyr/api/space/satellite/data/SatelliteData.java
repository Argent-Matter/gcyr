package argent_matter.gcyr.api.space.satellite.data;

import argent_matter.gcyr.util.Vec2i;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.util.ExtraCodecs;

import java.util.UUID;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote SatelliteData
 */
public record SatelliteData(Vec2i locationInWorld, int range, UUID owner) {
    public static final int DEFAULT_RANGE_BLOCKS = 256;
    public static Codec<SatelliteData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec2i.CODEC.fieldOf("pos").forGetter(SatelliteData::locationInWorld),
            ExtraCodecs.POSITIVE_INT.fieldOf("range").forGetter(SatelliteData::range),
            UUIDUtil.CODEC.fieldOf("owner").forGetter(SatelliteData::owner)
    ).apply(instance, SatelliteData::new));

    private static final UUID EMPTY_UUID = new UUID(0, 0);

    public SatelliteData copy() {
        return new SatelliteData(new Vec2i(this.locationInWorld.x(), this.locationInWorld.y()), this.range, this.owner);
    }

    public static SatelliteData DEFAULT = new SatelliteData(Vec2i.ZERO, 0, EMPTY_UUID);
}
