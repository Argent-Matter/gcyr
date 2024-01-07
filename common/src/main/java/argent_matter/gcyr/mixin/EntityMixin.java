package argent_matter.gcyr.mixin;

import argent_matter.gcyr.api.capability.GCyRCapabilityHelper;
import argent_matter.gcyr.api.capability.ISpaceStationHolder;
import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.api.space.station.SpaceStation;
import argent_matter.gcyr.api.syncdata.entity.IAutoPersistEntity;
import argent_matter.gcyr.api.syncdata.entity.IManagedEntity;
import argent_matter.gcyr.data.loader.PlanetData;
import argent_matter.gcyr.util.PlatformUtils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.lowdragmc.lowdraglib.syncdata.blockentity.IAutoPersistBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void gcyr$tick(CallbackInfo ci) {
        //noinspection DataFlowIssue
        Entity entity = (Entity) (Object) this;
        if (!(entity.level() instanceof ServerLevel level)) return;

        // Teleport the entity to the planet when they fall in the void while in an orbit dimension
        if (entity.getY() < level.getMinBuildHeight() && PlanetData.isOrbitLevel(level.dimension())) {
            ServerLevel newLevel = level.getServer().getLevel(PlanetData.getPlanetFromOrbit(level.dimension()).map(Planet::level).orElse(Level.OVERWORLD));
            Entity newEntity = PlatformUtils.changeDimension(entity, newLevel);
            newEntity.setPos(newEntity.getX(), 600.0, newEntity.getZ());
        }
    }

    @ModifyExpressionValue(method = "collideBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getWorldBorder()Lnet/minecraft/world/level/border/WorldBorder;"))
    private static WorldBorder gcyr$modifySpaceStationBorder(WorldBorder value, @Nullable Entity entity, Vec3 vec, AABB collisionBox, Level level, List<VoxelShape> potentialHits) {
        if (entity != null && level instanceof ServerLevel serverLevel) {
            if (PlanetData.isOrbitLevel(serverLevel.dimension())) {
                // default to normal world border if somehow not on a space station dimension
                ISpaceStationHolder spaceStationHolder = GCyRCapabilityHelper.getSpaceStations(serverLevel);
                if (spaceStationHolder == null) return value;

                // get nearest space station
                List<Integer> stationIds = spaceStationHolder.getStationsNearWorldPos(entity.blockPosition(), SpaceStation.BLOCK_MULTIPLIER / 2);
                if (!stationIds.isEmpty()) {
                    int nearest = stationIds.get(0);
                    SpaceStation station = spaceStationHolder.getStation(nearest);
                    // get border of selected station, or fall back to normal border if invalid
                    WorldBorder border = station != null ? station.border() : value;

                    // sync new border to client
                    if (entity instanceof ServerPlayer player) {
                        player.connection.send(new ClientboundSetBorderCenterPacket(border));
                        player.connection.send(new ClientboundSetBorderSizePacket(border));
                    }
                    return border;
                }
            } else {
                // reset client border if not on station
                if (entity instanceof ServerPlayer player) {
                    player.connection.send(new ClientboundSetBorderCenterPacket(value));
                    player.connection.send(new ClientboundSetBorderSizePacket(value));
                }
            }

        }
        return value;
    }

    @Inject(method = "saveWithoutId", at = @At("RETURN"))
    private void gcyr$addAdditionalSaveData(CompoundTag compound, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof IAutoPersistEntity autoPersistEntity) {
            autoPersistEntity.saveManagedPersistentData(compound);
        }
    }

    @Inject(method = "load", at = @At("RETURN"))
    private void gcyr$load(CompoundTag compound, CallbackInfo ci) {
        if (this instanceof IAutoPersistBlockEntity autoPersistBlockEntity) {
            autoPersistBlockEntity.loadManagedPersistentData(compound);
        }
    }
    /* TODO IAsyncAutoSyncEntity
    @Inject(method = "setRemoved", at = @At(value = "RETURN"))
    private void injectSetRemoved(CallbackInfo ci) {
        if (this instanceof IAsyncAutoSyncBlockEntity autoSyncBlockEntity) {
            autoSyncBlockEntity.onInValid();
        }
    }
    */
    @Inject(method = "unsetRemoved", at = @At(value = "RETURN"))
    private void injectClearRemoved(CallbackInfo ci) {
        if (this instanceof IManagedEntity managed) {
            managed.getRootStorage().init();
            /*
            if (managed instanceof IAsyncAutoSyncBlockEntity autoSyncBlockEntity) {
                autoSyncBlockEntity.onValid();
            }
            */
        }
    }
}
