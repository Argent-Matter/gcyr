package argent_matter.gcys.api.space.satellite.data;

import argent_matter.gcys.api.space.satellite.Satellite;
import argent_matter.gcys.util.Vec2i;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.phys.Vec2;

import java.util.UUID;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote SatelliteData
 */
public record SatelliteData(
        Vec2i locationInWorld, int range, UUID owner) {

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

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        CompoundTag pos = new CompoundTag();
        pos.putFloat("x", this.locationInWorld.x());
        pos.putFloat("y", this.locationInWorld.y());
        tag.put("pos", pos);

        tag.putInt("range", range);

        tag.putUUID("ownerId", this.owner);
        return tag;
    }

    public static SatelliteData deserializeNBT(CompoundTag nbt) {
        CompoundTag pos = nbt.getCompound("pos");
        var locationInWorld = new Vec2i(pos.getInt("x"), pos.getInt("y"));
        int range = nbt.getInt("range");
        UUID uuid = nbt.getUUID("ownerId");
        return new SatelliteData(locationInWorld, range, uuid);
    }
}
