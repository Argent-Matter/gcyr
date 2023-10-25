package argent_matter.gcyr.api.space.satellite;

import argent_matter.gcyr.api.registries.GCyRRegistries;
import argent_matter.gcyr.api.space.satellite.data.SatelliteData;
import com.mojang.datafixers.Products;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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

    public static <S extends Satellite> Products.P3<RecordCodecBuilder.Mu<S>, SatelliteType<?>, SatelliteData, ResourceKey<Level>> baseCodec(RecordCodecBuilder.Instance<S> instance) {
        return instance.group(
                GCyRRegistries.SATELLITES.codec().fieldOf("type").forGetter(Satellite::getType),
                SatelliteData.CODEC.fieldOf("data").forGetter(Satellite::getData),
                ResourceKey.codec(Registry.DIMENSION_REGISTRY).fieldOf("level").forGetter(Satellite::getLevel)
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
        if (jammed || needsRepair) return false;
        return runSatelliteFunction(level);
    }
    public abstract boolean runSatelliteFunction(Level level);

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", GCyRRegistries.SATELLITES.getKey(type).toString());

        tag.put("data", this.data.serializeNBT());

        tag.putString("level", this.level.location().toString());

        Tag extra = serializeExtraData();
        if (extra != null) tag.put("extra", extra);
        return tag;
    }
    @Nullable
    public abstract Tag serializeExtraData();

    public static Satellite deserializeNBT(CompoundTag nbt, Level level) {
        SatelliteType<?> type = GCyRRegistries.SATELLITES.get(new ResourceLocation(nbt.getString("id")));
        SatelliteType.SatelliteFactory<?> satellite = type.getFactory();

        SatelliteData data = SatelliteData.deserializeNBT(nbt.getCompound("data"));

        ResourceKey<Level> levelResourceKey = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(nbt.getString("level")));

        Satellite sat = satellite.create(type, data, levelResourceKey);
        if (nbt.contains("extra")) sat.deserializeExtraData(nbt.get("extra"), level);
        return sat;
    }

    public abstract void deserializeExtraData(Tag tag, Level level);
}
