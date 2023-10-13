package argent_matter.gcys.mixin;

import argent_matter.gcys.api.capability.IGpsTracked;
import argent_matter.gcys.common.entity.data.EntityOxygenSystem;
import argent_matter.gcys.common.entity.data.EntityTemperatureSystem;
import argent_matter.gcys.data.loader.PlanetData;
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
    private boolean gcys$gpsTracked;

    private LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void gcys$addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.putBoolean("gcys:gps_tracked", gcys$gpsTracked);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void gcys$readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("gcys:gps_tracked")) gcys$gpsTracked = tag.getBoolean("gcys:gps_tracked");
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void gcys$tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        Level level = entity.level;
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
        return gcys$gpsTracked;
    }

    @Override
    public void setGpsTracked(boolean tracked) {
        gcys$gpsTracked = tracked;
    }
}
