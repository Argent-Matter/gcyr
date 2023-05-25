package screret.gcys.mixin;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import screret.gcys.api.capability.IGpsTracked;
import screret.gcys.common.data.GcysDimensionTypes;
import screret.gcys.common.entity.data.EntityOxygenSystem;
import screret.gcys.common.entity.data.EntityTemperatureSystem;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements IGpsTracked {
    @Getter
    @Setter
    private boolean gpsTracked;

    private LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void gtceu$addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.putBoolean("GT.GpsTracked", gpsTracked);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void gtceu$readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("GT.GpsTracked")) gpsTracked = tag.getBoolean("GT.GpsTracked");
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

                EntityOxygenSystem.oxygenTick(entity, (ServerLevel) level);

                if (level.dimension() != GcysDimensionTypes.SPACE_LEVEL) {
                    return;
                }

                EntityTemperatureSystem.temperatureTick(entity, (ServerLevel) level);
            }
        }
    }
}
