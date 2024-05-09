package argent_matter.gcyr.api.space.satellite;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.api.space.satellite.data.SatelliteData;
import com.mojang.datafixers.Products;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote Satellite
 */
public abstract class Satellite {
    //public static EmptySatellite EMPTY = GTSatellites.EMPTY.getDefaultInstance();

    @Getter
    protected SatelliteData data;
    @Getter
    protected ResourceKey<Level> level;
    @Getter
    protected final SatelliteType<?> type;
    @Getter
    @Setter
    protected boolean jammed;
    @Getter
    @Setter
    protected boolean needsRepair;

    public Satellite(SatelliteType<?> type, SatelliteData data, ResourceKey<Level> level) {
        this.type = type;
        this.data = data;
        this.level = level;
    }

    public static <S extends Satellite> Products.P2<RecordCodecBuilder.Mu<S>, SatelliteData, ResourceKey<Level>> baseCodec(RecordCodecBuilder.Instance<S> instance) {
        return instance.group(
                SatelliteData.CODEC.fieldOf("data").forGetter(Satellite::getData),
                ResourceKey.codec(Registries.DIMENSION).fieldOf("level").forGetter(Satellite::getLevel)
        );
    }

    public Satellite copy() {
        Satellite copy = this.type.getDefaultInstance().get();
        copy.data = this.data.copy();
        copy.level = this.level;
        return copy;
    }

    public boolean isNonWorking() {
        return jammed || needsRepair;
    }

    public abstract void tickSatellite(Level level);

    public boolean runSatellite(Level level) {
        if (isNonWorking()) return false;
        return runSatelliteFunction(level);
    }

    public abstract boolean runSatelliteFunction(Level level);

    public CompoundTag serializeNBT() {
        CompoundTag tag = (CompoundTag) SatelliteType.CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow(false, GCyR.LOGGER::error);
        Tag extra = serializeExtraData();
        if (extra != null) tag.put("extra", extra);
        return tag;
    }
    @Nullable
    public abstract Tag serializeExtraData();

    public static Satellite deserializeNBT(CompoundTag nbt, Level level) {
        Satellite satellite = SatelliteType.CODEC.parse(NbtOps.INSTANCE, nbt).getOrThrow(false, GCyR.LOGGER::error);
        if (nbt.contains("extra")) satellite.deserializeExtraData(nbt.get("extra"), level);
        return satellite;
    }

    public abstract void deserializeExtraData(Tag tag, Level level);
}
