package argent_matter.gcyr.mixin;

import argent_matter.gcyr.util.MixinHelpers;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(Entity.class)
public class EntityBorderMixin {
    @ModifyExpressionValue(method = "collideBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getWorldBorder()Lnet/minecraft/world/level/border/WorldBorder;"))
    private static WorldBorder gcyr$modifySpaceStationBorder(WorldBorder value, @Nullable Entity entity, Vec3 vec, AABB collisionBox, Level level, List<VoxelShape> potentialHits) {
        return MixinHelpers.modifySpaceStationBorder(value, entity);
    }
}
