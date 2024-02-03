package argent_matter.gcyr.mixin.lithium;

import argent_matter.gcyr.util.MixinHelpers;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.jellysquid.mods.lithium.common.entity.LithiumEntityCollisions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = LithiumEntityCollisions.class, remap = false)
public class LithiumEntityCollisionsMixin {

    @ModifyExpressionValue(method = "*", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/CollisionGetter;getWorldBorder()Lnet/minecraft/world/level/border/WorldBorder;", remap = true))
    private static WorldBorder gcyr$modifySpaceStationBorder(WorldBorder value, CollisionGetter collisionView, Entity entity) {
        return MixinHelpers.modifySpaceStationBorder(value, entity);
    }
}
