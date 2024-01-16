package argent_matter.gcyr.common.satellite;

import argent_matter.gcyr.api.space.satellite.Satellite;
import argent_matter.gcyr.api.space.satellite.SatelliteType;
import argent_matter.gcyr.api.space.satellite.data.SatelliteData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote EmptySatellite
 */
public class EmptySatellite extends Satellite {
    public static final Codec<EmptySatellite> CODEC = RecordCodecBuilder.create(instance -> Satellite.baseCodec(instance).apply(instance, EmptySatellite::new));

    public EmptySatellite(SatelliteType<?> type, SatelliteData data, ResourceKey<Level> level) {
        super(type, data, level);
    }

    @Override
    public void tickSatellite(Level level) {
        if (isNonWorking()) return;

    }

    @Override
    public boolean runSatelliteFunction(Level level) {
        return false;
    }

    @Nullable
    @Override
    public CompoundTag serializeExtraData() {
        return null;
    }

    @Override
    public void deserializeExtraData(Tag tag, Level level) {

    }
}
