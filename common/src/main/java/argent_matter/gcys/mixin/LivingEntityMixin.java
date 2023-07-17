package argent_matter.gcys.mixin;

import argent_matter.gcys.api.capability.IGpsTracked;
import argent_matter.gcys.common.data.GcysDimensionTypes;
import argent_matter.gcys.common.entity.data.EntityOxygenSystem;
import argent_matter.gcys.common.entity.data.EntityTemperatureSystem;
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
    public void gtceu$addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.putBoolean("GT.GpsTracked", gcys$gpsTracked);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void gtceu$readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("GT.GpsTracked")) gcys$gpsTracked = tag.getBoolean("GT.GpsTracked");
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void gtceu$tick(CallbackInfo ci) {
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

                if (level.dimension() != GcysDimensionTypes.SPACE_LEVEL) {
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
