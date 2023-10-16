package argent_matter.gcys.api.space.dyson;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.api.capability.IDysonSystem;
import argent_matter.gcys.common.data.GcysNetworking;
import argent_matter.gcys.common.data.GcysSatellites;
import argent_matter.gcys.common.networking.s2c.PacketSyncDysonSphereStatus;
import argent_matter.gcys.common.satellite.DysonSwarmSatellite;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class DysonSystemSavedData extends SavedData implements IDysonSystem {
    @Nullable
    public static DysonSystemSavedData getOrCreate(ServerLevel serverLevel) {
        if (serverLevel.dimensionType().hasCeiling()) return null;
        return serverLevel.getDataStorage().computeIfAbsent(tag -> new DysonSystemSavedData(serverLevel, tag), () -> new DysonSystemSavedData(serverLevel), GregicalitySpace.MOD_ID + "_dyson_systems");
    }

    @Nullable
    private DysonSphere currentActiveSunBlock;
    private final Long2ObjectMap<Set<DysonSwarmSatellite>> swarmSatellites = new Long2ObjectOpenHashMap<>();

    private final ServerLevel level;

    public DysonSystemSavedData(ServerLevel level) {
        this.level = level;
    }

    public DysonSystemSavedData(ServerLevel level, CompoundTag tag) {
        this(level);
        this.load(tag);
    }

    @Override
    public boolean isDysonSphereActive() {
        return currentActiveSunBlock != null;
    }

    @Override
    public int activeDysonSwarmSatelliteCount() {
        return swarmSatellites.size();
    }

    @Override
    public void addDysonSphere(BlockPos controllerLocation) {
        if (this.currentActiveSunBlock != null) return;
        currentActiveSunBlock = new DysonSphere(controllerLocation);
        this.setDirty();
        GcysNetworking.NETWORK.sendToAll(new PacketSyncDysonSphereStatus(true));
    }

    @Override
    public void disableDysonSphere(BlockPos controllerPos) {
        if (currentActiveSunBlock != null && controllerPos.equals(currentActiveSunBlock.controllerPos())) {
            currentActiveSunBlock = null;
            this.setDirty();
            GcysNetworking.NETWORK.sendToAll(new PacketSyncDysonSphereStatus(false));
        }
    }

    @Override
    public void addDysonSatellite(BlockPos controllerPos, DysonSwarmSatellite satellite) {
        swarmSatellites.computeIfAbsent(controllerPos.asLong(), pos -> new HashSet<>()).add(satellite);
        this.setDirty();
    }

    @Override
    public void disableAllDysonSatellites(BlockPos controllerPos) {
        swarmSatellites.remove(controllerPos.asLong());
        this.setDirty();
    }

    public void load(CompoundTag arg) {
        if (arg.contains("dysonSphereControllerPos", Tag.TAG_COMPOUND)) {
            BlockPos sphereControllerPos = NbtUtils.readBlockPos(arg.getCompound("dysonSphereControllerPos"));
            this.currentActiveSunBlock = new DysonSphere(sphereControllerPos);
        }
        CompoundTag stationsTag = arg.getCompound("satellites");
        for (String name : stationsTag.getAllKeys()) {
            ListTag tag = stationsTag.getList(name, Tag.TAG_COMPOUND);
            long pos = Long.parseLong(name);
            for (int i = 0; i < tag.size(); ++i) {
                DysonSwarmSatellite satellite = GcysSatellites.DYSON_SWARM.getCodec().parse(NbtOps.INSTANCE, tag.getCompound(i)).getOrThrow(false, GregicalitySpace.LOGGER::error);
                swarmSatellites.computeIfAbsent(pos, $ -> new HashSet<>()).add(satellite);
            }
        }
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        if (currentActiveSunBlock != null) {
            compoundTag.put("dysonSphereControllerPos", NbtUtils.writeBlockPos(this.currentActiveSunBlock.controllerPos()));
        }
        CompoundTag tag = new CompoundTag();
        for (Long2ObjectMap.Entry<Set<DysonSwarmSatellite>> entry : swarmSatellites.long2ObjectEntrySet()) {
            ListTag pos = new ListTag();
            for (DysonSwarmSatellite satellite : entry.getValue()) {
                Tag station = GcysSatellites.DYSON_SWARM.getCodec().encodeStart(NbtOps.INSTANCE, satellite).result().orElseThrow();
                pos.add(station);
            }
            tag.put(Long.toString(entry.getLongKey()), pos);
        }
        compoundTag.put("satellites", tag);
        return compoundTag;
    }
}
