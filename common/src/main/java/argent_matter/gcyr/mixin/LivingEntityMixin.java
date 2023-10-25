package argent_matter.gcyr.mixin;

import argent_matter.gcyr.api.capability.IGpsTracked;
import argent_matter.gcyr.common.entity.data.EntityOxygenSystem;
import argent_matter.gcyr.common.entity.data.EntityTemperatureSystem;
import argent_matter.gcyr.data.loader.PlanetData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements IGpsTracked {
    @Unique
    private boolean gcyr$gpsTracked;

    private LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void gcyr$addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.putBoolean("gcyr:gps_tracked", gcyr$gpsTracked);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void gcyr$readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("gcyr:gps_tracked")) gcyr$gpsTracked = tag.getBoolean("gcyr:gps_tracked");
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void gcyr$tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        Level level = entity.level();
        if (!level.isClientSide) {
            if (level.getGameTime() % 10 == 0) {
                if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) {
                    return;
                }
                if (entity instanceof ArmorStand) {
                    return;
                }

                EntityOxygenSystem.oxygenTick(entity, (ServerLevel) level);

                if (!PlanetData.isSpaceLevel(level)) {
                    return;
                }

                EntityTemperatureSystem.temperatureTick(entity, (ServerLevel) level);
            }
        }
    }

    @Override
    public boolean isGpsTracked() {
        return gcyr$gpsTracked;
    }

    @Override
    public void setGpsTracked(boolean tracked) {
        gcyr$gpsTracked = tracked;
    }
}
