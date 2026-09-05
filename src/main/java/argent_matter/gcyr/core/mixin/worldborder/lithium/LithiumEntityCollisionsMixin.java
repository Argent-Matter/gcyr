package argent_matter.gcyr.core.mixin.worldborder.lithium;

import argent_matter.gcyr.api.space.station.SpaceStationWorldBorderHelper;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.border.WorldBorder;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.jellysquid.mods.lithium.common.entity.LithiumEntityCollisions;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(value = LithiumEntityCollisions.class, remap = false)
public class LithiumEntityCollisionsMixin {

    @ModifyExpressionValue(method = "*",
                           at = @At(value = "INVOKE",
                                    target = "Lnet/minecraft/world/level/CollisionGetter;getWorldBorder()Lnet/minecraft/world/level/border/WorldBorder;",
                                    remap = true))
    private static WorldBorder gcyr$modifySpaceStationBorder(WorldBorder value, CollisionGetter collisionView,
                                                             Entity entity) {
        return SpaceStationWorldBorderHelper.modifySpaceStationBorder(value, entity);
    }

    @Mixin(targets = "me.jellysquid.mods.lithium.common.entity.LithiumEntityCollisions$1$1", remap = false)
    private static class IteratorMixin {

        @Unique
        private IterableAccessor gcyr$this;

        // god fucking damnit why can't I just shadow this$0.
        @Inject(method = "<init>(Lme/jellysquid/mods/lithium/common/entity/LithiumEntityCollisions$1;)V", at = @At("TAIL"))
        private void gcyr$captureSuper(CallbackInfo ci) {
            try {
                Field field = this.getClass().getDeclaredField("this$0");
                field.setAccessible(true);
                gcyr$this = (IterableAccessor) field.get(this);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @ModifyExpressionValue(method = "computeNext()Lnet/minecraft/world/phys/shapes/VoxelShape;",
                at = @At(value = "INVOKE",
                        target = "Lnet/minecraft/world/level/Level;getWorldBorder()Lnet/minecraft/world/level/border/WorldBorder;",
                        remap = true))
        private WorldBorder gcyr$modifySpaceStationBorder(WorldBorder value) {
            return SpaceStationWorldBorderHelper.modifySpaceStationBorder(value, gcyr$this.getEntity());
        }
    }

    @Mixin(targets = "me.jellysquid.mods.lithium.common.entity.LithiumEntityCollisions$1", remap = false)
    private interface IterableAccessor extends Iterable<VoxelShape> {

        @Accessor("val$entity")
        Entity getEntity();
    }
}
