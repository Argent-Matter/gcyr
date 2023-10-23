package argent_matter.gcys.api.space.dyson;

import argent_matter.gcys.GCyS;
import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.api.capability.IDysonSystem;
import argent_matter.gcys.api.capability.ISpaceStationHolder;
import argent_matter.gcys.api.space.planet.Planet;
import argent_matter.gcys.common.data.GCySDimensionTypes;
import argent_matter.gcys.common.data.GCySNetworking;
import argent_matter.gcys.common.data.GCySSatellites;
import argent_matter.gcys.common.networking.s2c.PacketSyncDysonSphereStatus;
import argent_matter.gcys.common.satellite.DysonSwarmSatellite;
import argent_matter.gcys.data.loader.PlanetData;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DysonSystemSavedData extends SavedData implements IDysonSystem {
    @Nullable
    public static DysonSystemSavedData getOrCreate(ServerLevel originLevel) {
        if (originLevel.dimensionType().hasCeiling()) return null;

        Planet planet = PlanetData.getPlanetFromLevel(originLevel.dimension()).orElse(null);
        if (planet == null) return null; // A planet definition is required.

        ResourceLocation solarSystem = planet.solarSystem();
        List<Planet> planets = PlanetData.getSolarSystemPlanets(solarSystem);
        if (planets.isEmpty()) {
            internalGetOrCreate(originLevel);
        }
        ServerLevel firstWorldLevel = originLevel.getServer().getLevel(planets.get(0).level());
        return internalGetOrCreate(Objects.requireNonNullElse(firstWorldLevel, originLevel));
    }

    @Nullable
    public static DysonSystemSavedData getOrCreateMaybeSpace(ServerLevel level, @Nullable BlockPos pos) {
        if (pos != null && level.dimension().location().equals(GCySDimensionTypes.SPACE_LEVEL.location())) {
            ISpaceStationHolder spaceStations = GcysCapabilityHelper.getSpaceStations(level);
            if (spaceStations == null) return null;
            List<Integer> nearbyStationIds = spaceStations.getStationsNearWorldPos(pos, 8 * 8 /*half of max station size*/);
            if (nearbyStationIds.isEmpty()) return null;
            return getOrCreateForSpace(level.getServer(), nearbyStationIds.get(0));
        }
        return getOrCreate(level);
    }

    @Nullable
    public static DysonSystemSavedData getOrCreateForSpace(MinecraftServer server, int stationId) {
        ISpaceStationHolder spaceStations = GcysCapabilityHelper.getSpaceStations(server.getLevel(GCySDimensionTypes.SPACE_LEVEL));
        if (spaceStations == null) return null;

        ServerLevel serverLevel = server.getLevel(spaceStations.getStation(stationId).orbitPlanet().level());
        if (serverLevel == null) return null;
        return getOrCreate(serverLevel);
    }

    private static DysonSystemSavedData internalGetOrCreate(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(tag -> new DysonSystemSavedData(serverLevel, tag), () -> new DysonSystemSavedData(serverLevel), GCyS.MOD_ID + "_dyson_systems");
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
    @Nullable
    public DysonSphere activeDysonSphere() {
        return currentActiveSunBlock;
    }

    @Override
    public boolean isDysonSphereActive() {
        return currentActiveSunBlock != null;
    }

    @Override
    public int activeDysonSwarmSatelliteCount() {
        return (int) swarmSatellites.values().stream().flatMap(Collection::stream).count();
    }

    @Override
    public void addDysonSphere(BlockPos controllerPos) {
        if (this.currentActiveSunBlock != null) return;
        this.currentActiveSunBlock = new DysonSphere(controllerPos, this);
        this.swarmSatellites.keySet().forEach(pos -> this.disableAllDysonSatellites(BlockPos.of(pos)));
        this.setDirty();
        for (ServerPlayer player : this.level.getServer().getPlayerList().getPlayers()) {
            Planet playerPlanet = PlanetData.getPlanetFromLevel(player.serverLevel().dimension()).orElse(null);
            Planet thisPlanet = PlanetData.getPlanetFromLevel(this.level.dimension()).orElse(null);
            if (playerPlanet == null || thisPlanet == null) continue;
            if (playerPlanet.solarSystem().equals(thisPlanet.solarSystem())) {
                GCySNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(false), player);
            }
        }
    }

    @Override
    public void disableDysonSphere(BlockPos controllerPos) {
        if (currentActiveSunBlock != null && controllerPos.equals(currentActiveSunBlock.getControllerPos())) {
            currentActiveSunBlock.setControllerPos(null);

            this.setDirty();
            for (ServerPlayer player : this.level.getServer().getPlayerList().getPlayers()) {
                Planet playerPlanet = PlanetData.getPlanetFromLevel(player.serverLevel().dimension()).orElse(null);
                Planet thisPlanet = PlanetData.getPlanetFromLevel(this.level.dimension()).orElse(null);
                if (playerPlanet == null || thisPlanet == null) continue;
                if (playerPlanet.solarSystem().equals(thisPlanet.solarSystem())) {
                    GCySNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(false), player);
                }
            }
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

    @Override
    public void tick() {
        if (this.currentActiveSunBlock != null) {
            this.currentActiveSunBlock.tick(this.level);
        }
    }

    @Override
    public void setChanged() {
        this.setDirty();
    }

    public void load(CompoundTag arg) {
        if (arg.contains("dysonSphere", Tag.TAG_COMPOUND)) {
            this.currentActiveSunBlock = DysonSphere.load(arg.getCompound("dysonSphere"), this);
            for (ServerPlayer player : this.level.players()) {
                GCySNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(true), player);
            }
        }
        CompoundTag stationsTag = arg.getCompound("satellites");
        for (String name : stationsTag.getAllKeys()) {
            ListTag tag = stationsTag.getList(name, Tag.TAG_COMPOUND);
            long pos = Long.parseLong(name);
            for (int i = 0; i < tag.size(); ++i) {
                DysonSwarmSatellite satellite = GCySSatellites.DYSON_SWARM.getCodec().parse(NbtOps.INSTANCE, tag.getCompound(i)).getOrThrow(false, GCyS.LOGGER::error);
                swarmSatellites.computeIfAbsent(pos, $ -> new HashSet<>()).add(satellite);
            }
        }
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        if (currentActiveSunBlock != null) {
            CompoundTag tag = new CompoundTag();
            this.currentActiveSunBlock.save(tag);
            compoundTag.put("dysonSphere", tag);
        }
        CompoundTag tag = new CompoundTag();
        for (Long2ObjectMap.Entry<Set<DysonSwarmSatellite>> entry : swarmSatellites.long2ObjectEntrySet()) {
            ListTag pos = new ListTag();
            for (DysonSwarmSatellite satellite : entry.getValue()) {
                Tag station = GCySSatellites.DYSON_SWARM.getCodec().encodeStart(NbtOps.INSTANCE, satellite).result().orElseThrow();
                pos.add(station);
            }
            tag.put(Long.toString(entry.getLongKey()), pos);
        }
        compoundTag.put("satellites", tag);
        return compoundTag;
    }
}
