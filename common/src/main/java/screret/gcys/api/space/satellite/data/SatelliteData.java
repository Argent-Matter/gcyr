package screret.gcys.api.space.satellite.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec2;

import java.util.UUID;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote SatelliteData
 */
public record SatelliteData(Vec2 locationInWorld, int range, UUID owner) {

    private static final UUID EMPTY_UUID = new UUID(0, 0);

    public SatelliteData copy() {
        return new SatelliteData(new Vec2(this.locationInWorld.x, this.locationInWorld.y), this.range, this.owner);
    }

    public static SatelliteData DEFAULT = new SatelliteData(Vec2.ZERO, 0, EMPTY_UUID);

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        CompoundTag pos = new CompoundTag();
        pos.putFloat("x", this.locationInWorld.x);
        pos.putFloat("y", this.locationInWorld.y);
        tag.put("pos", pos);

        tag.putInt("range", range);

        tag.putUUID("ownerId", this.owner);
        return tag;
    }

    public static SatelliteData deserializeNBT(CompoundTag nbt) {
        CompoundTag pos = nbt.getCompound("pos");
        var locationInWorld = new Vec2(pos.getFloat("x"), pos.getFloat("y"));
        int range = nbt.getInt("range");
        UUID uuid = nbt.getUUID("ownerId");
        return new SatelliteData(locationInWorld, range, uuid);
    }
}
