package argent_matter.gcyr.common.satellite;

import argent_matter.gcyr.api.space.satellite.Satellite;
import argent_matter.gcyr.api.space.satellite.data.SatelliteData;
import argent_matter.gcyr.common.data.GCyRSatellites;
import com.gregtechceu.gtceu.api.data.tag.TagUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.apache.commons.lang3.ArrayUtils;

public class OreFinderSatellite extends Satellite {
    public static final Codec<OreFinderSatellite> CODEC = RecordCodecBuilder.create(instance -> Satellite.baseCodec(instance).apply(instance, OreFinderSatellite::new));
    public static final int CELL_SIZE = 32;

    private BlockPos centerPos;
    public OreFinderSatellite(SatelliteData data, ResourceKey<Level> level) {
        super(GCyRSatellites.ORE_FINDER, data, level);
    }

    @Override
    public void tickSatellite(Level level) {
        if (isNonWorking()) return;

    }

    @Override
    public boolean runSatelliteFunction(Level level) {
        return false;
    }

    public void scan(BlockState[][][] storage, Level level) {
        LevelChunk chunk = level.getChunkAt(this.centerPos);

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        var oreTag = TagUtil.createBlockTag("ores");
        for (int x = 0; x < CELL_SIZE; x++) {
            if (x >= storage.length) {
                break;
            }
            for (int z = 0; z < CELL_SIZE; z++) {
                if (z >= storage[x].length) {
                    break;
                }
                if (storage[x][z] != null) continue;
                for (int y = chunk.getMaxBuildHeight() - 1; y >= chunk.getMinBuildHeight(); y--) {
                    pos.set(x, y, z);
                    var state = chunk.getBlockState(pos);
                    if (state.is(oreTag)) {
                        storage[x][z] = ArrayUtils.add(storage[x][z], state);
                    }
                }
            }
        }

    }

    @Override
    public CompoundTag serializeExtraData() {
        CompoundTag tag = new CompoundTag();
        tag.put("centerPos", NbtUtils.writeBlockPos(this.centerPos));
        return tag;
    }

    @Override
    public void deserializeExtraData(Tag tag, Level level) {
        if (tag instanceof CompoundTag compoundTag) {
            this.centerPos = NbtUtils.readBlockPos(compoundTag.getCompound("centerPos"));
        }
    }
}
