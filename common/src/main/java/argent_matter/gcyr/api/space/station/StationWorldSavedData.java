package argent_matter.gcyr.api.space.station;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.api.capability.ISpaceStationHolder;
import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.common.worldgen.SpaceLevelSource;
import argent_matter.gcyr.data.loader.PlanetData;
import argent_matter.gcyr.util.Vec2i;
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
    public static StationWorldSavedData getOrCreate(@Nullable ServerLevel serverLevel) {
        if (serverLevel == null) return null;
        if (!PlanetData.isOrbitLevel(serverLevel.dimension())) {
            Planet planet = PlanetData.getPlanetFromLevel(serverLevel.dimension()).orElse(null);
            if (planet == null) return null;
            ServerLevel orbit = serverLevel.getServer().getLevel(planet.orbitWorld());
            return getOrCreate(orbit);
        }
        return serverLevel.getDataStorage().computeIfAbsent(tag -> new StationWorldSavedData(serverLevel, tag), () -> new StationWorldSavedData(serverLevel), GCyR.MOD_ID + "_space_stations");
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

    @Nullable
    @Override
    public Vec2i getStationPos(int id) {
        var station = stations.get(id);
        if (station == null) return null;
        return station.position();
    }

    @Nullable
    @Override
    public SpaceStation getStation(@Nullable Integer id) {
        if (id == null) return null;
        return stations.get(id);
    }

    @Nullable
    @Override
    public BlockPos getStationWorldPos(int id) {
        Vec2i stationPos = getStationPos(id);
        if (stationPos == null) return null;
        return new BlockPos(stationPos.x() * 16 * 16, SpaceLevelSource.PLATFORM_HEIGHT, stationPos.y() * 16 * 16);
    }

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
            SpaceStation station = SpaceStation.CODEC.parse(NbtOps.INSTANCE, tag).result().orElseThrow();
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
