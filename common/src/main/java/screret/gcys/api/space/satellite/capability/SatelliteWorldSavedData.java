package screret.gcys.api.space.satellite.capability;

import com.gregtechceu.gtceu.GTCEu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;
import screret.gcys.api.capability.ISatelliteHolder;
import screret.gcys.api.space.satellite.Satellite;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SatelliteWorldSavedData extends SavedData implements ISatelliteHolder {
    @Nullable
    public static SatelliteWorldSavedData getOrCreate(ServerLevel serverLevel) {
        if (serverLevel.dimensionType().hasCeiling()) return null;
        return serverLevel.getDataStorage().computeIfAbsent(tag -> new SatelliteWorldSavedData(serverLevel, tag), () -> new SatelliteWorldSavedData(serverLevel), GTCEu.MOD_ID + "_satellites");
    }

    private final List<Satellite> satellites = new ArrayList<>();
    private final ServerLevel level;

    public SatelliteWorldSavedData(ServerLevel level) {
        this.level = level;
    }

    public SatelliteWorldSavedData(ServerLevel level, CompoundTag tag) {
        this.level = level;
        this.load(tag);
    }

    @Override
    public List<Satellite> getSatellites() {
        return satellites;
    }

    public void tickSatellites() {
        getSatellites().forEach(satellite -> satellite.tickSatellite(level));
    }

    @Override
    public Satellite getClosestSatellite(Vec2 position) {
        return satellites.stream().min(Comparator.comparingDouble(obj -> obj.getData().locationInWorld().distanceToSqr(position))).orElse(null);
    }

    @Nullable
    @Override
    public List<Satellite> getSatellitesNearPos(Vec2 position, int range) {
        return satellites.stream().filter(sat -> sat.getData().locationInWorld().distanceToSqr(position) <= range * range).sorted(Comparator.comparingDouble(sat -> sat.getData().locationInWorld().distanceToSqr(position))).collect(Collectors.toList());
    }

    @Override
    public void addSatellite(@Nullable Satellite satellite) {
        if (satellite != null) satellites.add(satellite);
    }

    @Override
    public void destroySatellite(Satellite satellite) {
        satellites.remove(satellite);
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        ListTag tag = new ListTag();
        for (Satellite satellite : satellites) {
            tag.add(satellite.serializeNBT());
        }
        compoundTag.put("satellites", tag);
        return compoundTag;
    }

    public void load(CompoundTag compoundTag) {
        for (Tag tag : compoundTag.getList("satellites", Tag.TAG_COMPOUND)) {
            satellites.add(Satellite.deserializeNBT((CompoundTag) tag, level));
        }
    }

}
