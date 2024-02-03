package argent_matter.gcyr.mixin.lithium;

import argent_matter.gcyr.util.MixinHelpers;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(targets = "me.jellysquid.mods.lithium.common.entity.LithiumEntityCollisions$1$1", remap = false)
public class LithiumEntityCollisions$IteratorMixin {

    @Unique
    private LithiumEntityCollisions$IterableAccessor gcyr$this;

    // god fucking damnit why can't I just shadow this$0.
    @Inject(method = "<init>(Lme/jellysquid/mods/lithium/common/entity/LithiumEntityCollisions$1;)V", at = @At("TAIL"))
    private void gcyr$captureSuper(CallbackInfo ci) {
        try {
            Field field = this.getClass().getDeclaredField("this$0");
            field.setAccessible(true);
            gcyr$this = (LithiumEntityCollisions$IterableAccessor) field.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @ModifyExpressionValue(method = "computeNext", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getWorldBorder()Lnet/minecraft/world/level/border/WorldBorder;", remap = true))
    private WorldBorder gcyr$modifySpaceStationBorder(WorldBorder value) {
        return MixinHelpers.modifySpaceStationBorder(value, gcyr$this.getEntity());
    }
}
