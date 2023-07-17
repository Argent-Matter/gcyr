package argent_matter.gcys.api.space.station.capability;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.api.capability.ISpaceStationHolder;
import argent_matter.gcys.common.data.GcysDimensionTypes;
import argent_matter.gcys.common.item.IdChipBehaviour;
import com.gregtechceu.gtceu.GTCEu;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec2;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StationWorldSavedData extends SavedData implements ISpaceStationHolder {
    @Nullable
    public static StationWorldSavedData getOrCreate(ServerLevel serverLevel) {
        if (serverLevel.dimension() != GcysDimensionTypes.SPACE_LEVEL) return null;
        return serverLevel.getDataStorage().computeIfAbsent(tag -> new StationWorldSavedData(serverLevel, tag), () -> new StationWorldSavedData(serverLevel), GregicalitySpace.MOD_ID + "_space_stations");
    }

    private final Int2ObjectMap<Vec2> stations = new Int2ObjectLinkedOpenHashMap<>(1);
    private final Int2ObjectMap<Vec2> freeStationPositions = new Int2ObjectLinkedOpenHashMap<>(IdChipBehaviour.ID_MAX * IdChipBehaviour.ID_MAX);
    private final ServerLevel level;

    public StationWorldSavedData(ServerLevel level) {
        this.level = level;
        for (int x = 0; x < IdChipBehaviour.ID_MAX; ++x) {
            for (int y = 0; y < IdChipBehaviour.ID_MAX; ++y) {
                freeStationPositions.put(y + x * IdChipBehaviour.ID_MAX, new Vec2(x, y));
            }
        }
    }

    public StationWorldSavedData(ServerLevel level, CompoundTag tag) {
        this(level);
        this.load(tag);
    }

    @Override
    public Int2ObjectMap<Vec2> getStations() {
        return stations;
    }

    @Override
    public int getClosestStationId(Vec2 position) {
        var result = stations.int2ObjectEntrySet().stream().min(Comparator.comparingDouble(obj -> obj.getValue().distanceToSqr(position))).orElse(null);
        return result == null ? -1 : result.getIntKey();
    }

    @Override
    public Vec2 getStationPos(int id) {
        return stations.getOrDefault(id, Vec2.NEG_UNIT_Y);
    }

    @Nullable
    @Override
    public List<Integer> getStationsNearPos(Vec2 position, int range) {
        return stations.int2ObjectEntrySet().stream().filter(obj -> obj.getValue().distanceToSqr(position) <= range * range).sorted(Comparator.comparingDouble(obj -> obj.getValue().distanceToSqr(position))).map(Int2ObjectMap.Entry::getIntKey).collect(Collectors.toList());
    }

    @Override
    public void addStation(int stationId, Vec2 pos) {
        stations.put(stationId, pos);
        freeStationPositions.remove(stationId, pos);
    }

    @Override
    public void destroyStation(int id) {
        freeStationPositions.put(id, stations.get(id));
        stations.remove(id);
    }

    @Override
    public int getFreeStationId() {
        return stations.size();
    }

    @Override
    public Vec2 getFreeStationPos(int stationId) {
        return freeStationPositions.getOrDefault(stationId, Vec2.NEG_UNIT_Y);
    }

    public void load(CompoundTag arg) {
        CompoundTag stationsTag = arg.getCompound("stations");
        for (String name : stationsTag.getAllKeys()) {
            CompoundTag tag = stationsTag.getCompound(name);
            int id = Integer.parseInt(name);
            Vec2 pos = new Vec2(tag.getInt("x"), tag.getInt("z"));
            stations.put(id, pos);
            freeStationPositions.remove(id);
        }
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        CompoundTag tag = new CompoundTag();
        for (Int2ObjectMap.Entry<Vec2> station : stations.int2ObjectEntrySet()) {
            CompoundTag pos = new CompoundTag();
            pos.putInt("x", (int) station.getValue().x);
            pos.putInt("z", (int) station.getValue().y);
            tag.put(Integer.toString(station.getIntKey()), pos);
        }
        compoundTag.put("stations", tag);
        return compoundTag;
    }
}
