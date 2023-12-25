package argent_matter.gcyr.mixin;

import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.api.syncdata.entity.IAutoPersistEntity;
import argent_matter.gcyr.api.syncdata.entity.IManagedEntity;
import argent_matter.gcyr.data.loader.PlanetData;
import argent_matter.gcyr.util.PlatformUtils;
import com.lowdragmc.lowdraglib.syncdata.blockentity.IAutoPersistBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void gcyr$tick(CallbackInfo ci) {
        Entity entity = ((Entity) (Object) this);
        if (!(entity.level() instanceof ServerLevel level)) return;

        // Teleport the entity to the planet when they fall in the void while in an orbit dimension
        if (entity.getY() < level.getMinBuildHeight() && PlanetData.isOrbitLevel(level.dimension())) {
            ServerLevel newLevel = level.getServer().getLevel(PlanetData.getPlanetFromOrbit(level.dimension()).map(Planet::level).orElse(Level.OVERWORLD));
            PlatformUtils.changeDimension(entity, newLevel);
        }
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
