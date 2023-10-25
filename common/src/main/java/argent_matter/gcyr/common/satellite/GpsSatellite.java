package argent_matter.gcyr.common.satellite;

import argent_matter.gcyr.api.capability.IGpsTracked;
import argent_matter.gcyr.api.space.satellite.Satellite;
import argent_matter.gcyr.api.space.satellite.SatelliteType;
import argent_matter.gcyr.api.space.satellite.data.SatelliteData;
import argent_matter.gcyr.util.Vec2i;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Screret
 * @date 2023/4/15
 * @implNote GpsSatellite
 */
public class GpsSatellite extends Satellite {

    public static final Codec<GpsSatellite> CODEC = RecordCodecBuilder.create(instance -> Satellite.baseCodec(instance).apply(instance, GpsSatellite::new));

    public static Map<Level, Set<Entity>> trackedEntities = new LinkedHashMap<>();

    private Set<Entity> lastTrackedEntities = new HashSet<>();

    public GpsSatellite(SatelliteType<?> type, SatelliteData data, ResourceKey<Level> level) {
        super(type, data, level);
    }

    @Override
    public void tickSatellite(Level level) {
        if (isNonWorking()) return;
        Vec2i pos = this.data.locationInWorld();
        var set = level.getEntities(null, AABB.ofSize(new Vec3(pos.x(), level.getSeaLevel(), pos.y()), this.data.range(), 100, this.data.range())).stream().filter(ent -> ((IGpsTracked)ent).isGpsTracked()).collect(Collectors.toSet());
        lastTrackedEntities.removeAll(set);
        var tracked = trackedEntities.get(level);
        tracked.removeAll(lastTrackedEntities);
        tracked.addAll(set);
        lastTrackedEntities = set;
    }

    @Override
    public boolean runSatelliteFunction(Level level) {
        return false;
    }

    @Nullable
    @Override
    public ListTag serializeExtraData() {
        ListTag tracked = new ListTag();
        for (Entity entity : lastTrackedEntities) {
            tracked.add(StringTag.valueOf(entity.getUUID().toString()));
        }
        return tracked;
    }

    @Override
    public void deserializeExtraData(Tag nbt, Level level) {
        if (level instanceof ServerLevel serverLevel) {
            ListTag list = (ListTag) nbt;
            for (Tag tag : list) {
                StringTag str = (StringTag) tag;
                UUID uuid = UUID.fromString(str.getAsString());
                Entity ent = serverLevel.getEntity(uuid);
                if (ent != null) {
                    lastTrackedEntities.add(ent);
                }
            }

        }
    }

}
