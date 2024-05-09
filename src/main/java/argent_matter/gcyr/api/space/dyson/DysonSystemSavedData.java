package argent_matter.gcyr.api.space.dyson;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.api.capability.IDysonSystem;
import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.common.data.GCyRNetworking;
import argent_matter.gcyr.common.data.GCyRSatellites;
import argent_matter.gcyr.common.networking.s2c.PacketSyncDysonSphereStatus;
import argent_matter.gcyr.common.satellite.DysonSwarmSatellite;
import argent_matter.gcyr.data.loader.PlanetData;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;
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

        ResourceLocation solarSystem = PlanetData.getPlanetFromLevelOrOrbit(originLevel.dimension()).map(Planet::solarSystem).orElse(null);
        if (solarSystem == null) return null; // A planet definition is required.

        List<Planet> planets = PlanetData.getSolarSystemPlanets(solarSystem);
        if (planets.isEmpty()) {
            internalGetOrCreate(originLevel);
        }
        ServerLevel firstWorldLevel = originLevel.getServer().getLevel(planets.get(0).level());
        return internalGetOrCreate(Objects.requireNonNullElse(firstWorldLevel, originLevel));
    }

    private static DysonSystemSavedData internalGetOrCreate(ServerLevel serverLevel) {
        return serverLevel.getDataStorage().computeIfAbsent(tag -> new DysonSystemSavedData(serverLevel, tag), () -> new DysonSystemSavedData(serverLevel), GCyR.MOD_ID + "_dyson_systems");
    }

    private final Map<BlockPos, DysonSphere> currentActiveDysonSpheres = new HashMap<>();
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
    public Map<BlockPos, DysonSphere> activeDysonSpheres() {
        return currentActiveDysonSpheres;
    }

    @Override
    public boolean isDysonSphereActive() {
        return !currentActiveDysonSpheres.isEmpty();
    }

    @Override
    public int activeDysonSwarmSatelliteCount() {
        return swarmSatellites.values().stream().mapToInt(Collection::size).sum();
    }


    @Override
    public void addDysonSphere(BlockPos controllerPos) {
        DysonSphere sphere = new DysonSphere(controllerPos, this);
        this.currentActiveDysonSpheres.put(controllerPos, sphere);
        this.swarmSatellites.get(controllerPos.asLong()).forEach(swarmSatellites::remove);
        this.setDirty();
        for (ServerPlayer player : this.level.getServer().getPlayerList().getPlayers()) {
            Planet playerPlanet = PlanetData.getPlanetFromLevel(player.serverLevel().dimension()).orElse(null);
            Planet thisPlanet = PlanetData.getPlanetFromLevel(this.level.dimension()).orElse(null);
            if (playerPlanet == null || thisPlanet == null) continue;
            if (playerPlanet.solarSystem().equals(thisPlanet.solarSystem())) {
                //GCyRNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(false), player);
            }
        }
    }

    @Override
    public void disableDysonSphere(BlockPos controllerPos) {
        DysonSphere current = currentActiveDysonSpheres.get(controllerPos);
        if (current != null) {
            current.setControllerPos(null);
            this.setDirty();
            for (ServerPlayer player : this.level.getServer().getPlayerList().getPlayers()) {
                Planet playerPlanet = PlanetData.getPlanetFromLevel(player.serverLevel().dimension()).orElse(null);
                Planet thisPlanet = PlanetData.getPlanetFromLevel(this.level.dimension()).orElse(null);
                if (playerPlanet == null || thisPlanet == null) continue;
                if (playerPlanet.solarSystem().equals(thisPlanet.solarSystem())) {
                    //GCyRNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(false), player);
                }
            }
        }
    }

    @Override
    public void addDysonSatellite(BlockPos controllerPos, DysonSwarmSatellite satellite) {
        swarmSatellites.computeIfAbsent(controllerPos.asLong(), pos -> new HashSet<>()).add(satellite);
        satellite.setSwarmController(controllerPos);
        this.setDirty();
    }

    @Override
    public void disableAllDysonSatellites(BlockPos controllerPos) {
        swarmSatellites.remove(controllerPos.asLong());
        this.setDirty();
    }

    @Override
    public void tick() {
        this.currentActiveDysonSpheres.forEach((pos, sphere) -> sphere.tick(this.level));
    }

    @Override
    public void setChanged() {
        this.setDirty();
    }

    public void load(CompoundTag arg) {
        if (arg.contains("dysonSpheres", Tag.TAG_LIST)) {
            for (Tag sphere : arg.getList("dysonSpheres", Tag.TAG_COMPOUND)) {
                BlockPos controllerPos = NbtUtils.readBlockPos(((CompoundTag)sphere).getCompound("controllerPos"));
                this.currentActiveDysonSpheres.put(controllerPos, DysonSphere.load((CompoundTag) sphere, this));
                for (ServerPlayer player : this.level.players()) {
                    //GCyRNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(true), player);
                }
            }
        }
        CompoundTag stationsTag = arg.getCompound("satellites");
        for (String name : stationsTag.getAllKeys()) {
            ListTag tag = stationsTag.getList(name, Tag.TAG_COMPOUND);
            long pos = Long.parseLong(name);
            for (int i = 0; i < tag.size(); ++i) {
                DysonSwarmSatellite satellite = GCyRSatellites.DYSON_SWARM.getCodec().parse(NbtOps.INSTANCE, tag.getCompound(i)).getOrThrow(false, GCyR.LOGGER::error);
                swarmSatellites.computeIfAbsent(pos, $ -> new HashSet<>()).add(satellite);
            }
        }
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        ListTag spheresTag = new ListTag();
        for (DysonSphere sphere : this.currentActiveDysonSpheres.values()) {
            CompoundTag tag = new CompoundTag();
            sphere.save(tag);
            spheresTag.add(tag);
        }
        compoundTag.put("dysonSpheres", spheresTag);
        CompoundTag tag = new CompoundTag();
        for (Long2ObjectMap.Entry<Set<DysonSwarmSatellite>> entry : swarmSatellites.long2ObjectEntrySet()) {
            ListTag pos = new ListTag();
            for (DysonSwarmSatellite satellite : entry.getValue()) {
                Tag station = GCyRSatellites.DYSON_SWARM.getCodec().encodeStart(NbtOps.INSTANCE, satellite).result().orElseThrow();
                pos.add(station);
            }
            tag.put(Long.toString(entry.getLongKey()), pos);
        }
        compoundTag.put("satellites", tag);
        return compoundTag;
    }
}
