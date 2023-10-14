package argent_matter.gcys.api.space.station;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.api.capability.ISpaceStationHolder;
import argent_matter.gcys.common.data.GcysDimensionTypes;
import argent_matter.gcys.common.worldgen.SpaceLevelSource;
import argent_matter.gcys.util.Vec2i;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class StationWorldSavedData extends SavedData implements ISpaceStationHolder {
    @Nullable
    public static StationWorldSavedData getOrCreate(ServerLevel serverLevel) {
        if (serverLevel.dimension() != GcysDimensionTypes.SPACE_LEVEL) return null;
        return serverLevel.getDataStorage().computeIfAbsent(tag -> new StationWorldSavedData(serverLevel, tag), () -> new StationWorldSavedData(serverLevel), GregicalitySpace.MOD_ID + "_space_stations");
    }

    private final Int2ObjectMap<SpaceStation> stations = new Int2ObjectLinkedOpenHashMap<>(1);
    private final Int2ObjectMap<Vec2i> freeStationPositions = new Int2ObjectLinkedOpenHashMap<>(SpaceStation.ID_MAX * SpaceStation.ID_MAX);
    private final ServerLevel level;

    public StationWorldSavedData(ServerLevel level) {
        this.level = level;
        for (int x = 0; x < SpaceStation.ID_MAX; ++x) {
            for (int y = 0; y < SpaceStation.ID_MAX; ++y) {
                freeStationPositions.put(y + x * SpaceStation.ID_MAX, new Vec2i(x, y));
            }
        }
    }

    public StationWorldSavedData(ServerLevel level, CompoundTag tag) {
        this(level);
        this.load(tag);
    }

    @Override
    public Int2ObjectMap<SpaceStation> getStations() {
        return stations;
    }

    @Override
    public int getClosestStationId(Vec2i position) {
        var result = stations.int2ObjectEntrySet().stream().min(Comparator.comparingDouble(obj -> obj.getValue().position().distanceToSqr(position))).orElse(null);
        return result == null ? -1 : result.getIntKey();
    }

    @Override
    public Vec2i getStationPos(int id) {
        var station = stations.get(id);
        if (station == null) return Vec2i.MAX_NEGATIVE;
        return station.position();
    }

    @Override
    public BlockPos getStationWorldPos(int id) {
        Vec2i stationPos = getStationPos(id);
        return new BlockPos(stationPos.x() * 16, SpaceLevelSource.PLATFORM_HEIGHT, stationPos.y() * 16);
    }

    @Nullable
    @Override
    public List<Integer> getStationsNearPos(Vec2i position, int range) {
        return stations.int2ObjectEntrySet().stream().filter(obj -> obj.getValue().position().distanceToSqr(position) <= range * range).sorted(Comparator.comparingDouble(obj -> obj.getValue().position().distanceToSqr(position))).map(Int2ObjectMap.Entry::getIntKey).collect(Collectors.toList());
    }

    @Override
    public void addStation(int stationId, SpaceStation pos) {
        stations.put(stationId, pos);
        freeStationPositions.remove(stationId, pos);
        setDirty();
    }

    @Override
    public void destroyStation(int id) {
        freeStationPositions.put(id, stations.get(id).position());
        stations.remove(id);
        setDirty();
    }

    @Override
    public int getFreeStationId() {
        return stations.size();
    }

    @Override
    public Vec2i getFreeStationPos(int stationId) {
        return freeStationPositions.getOrDefault(stationId, Vec2i.MAX_NEGATIVE);
    }

    public void load(CompoundTag arg) {
        CompoundTag stationsTag = arg.getCompound("stations");
        for (String name : stationsTag.getAllKeys()) {
            CompoundTag tag = stationsTag.getCompound(name);
            int id = Integer.parseInt(name);
            SpaceStation station = SpaceStation.CODEC.parse(NbtOps.INSTANCE, tag.getCompound("station")).result().orElseThrow();
            stations.put(id, station);
            freeStationPositions.remove(id);
        }
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        CompoundTag tag = new CompoundTag();
        for (Int2ObjectMap.Entry<SpaceStation> entry : stations.int2ObjectEntrySet()) {
            Tag station = SpaceStation.CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue()).result().orElseThrow();
            tag.put(Integer.toString(entry.getIntKey()), station);
        }
        compoundTag.put("stations", tag);
        return compoundTag;
    }
}
