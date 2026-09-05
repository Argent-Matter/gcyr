package argent_matter.gcyr.core.mixin.worldborder.vanilla;

import argent_matter.gcyr.api.space.station.SpaceStationWorldBorderHelper;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

import org.jetbrains.annotations.Nullable;

@Mixin(Entity.class)
public class EntityBorderMixin {

    @ModifyExpressionValue(method = "collideBoundingBox",
                           at = @At(value = "INVOKE",
                                    target = "Lnet/minecraft/world/level/Level;getWorldBorder()Lnet/minecraft/world/level/border/WorldBorder;"))
    private static WorldBorder gcyr$modifySpaceStationBorder(WorldBorder value, @Nullable Entity entity, Vec3 vec,
                                                             AABB collisionBox, Level level,
                                                             List<VoxelShape> potentialHits) {
        return SpaceStationWorldBorderHelper.modifySpaceStationBorder(value, entity);
    }
}
