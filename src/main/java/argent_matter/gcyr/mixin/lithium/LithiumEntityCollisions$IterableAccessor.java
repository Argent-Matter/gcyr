package argent_matter.gcyr.mixin.lithium;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "me.jellysquid.mods.lithium.common.entity.LithiumEntityCollisions$1", remap = false)
public interface LithiumEntityCollisions$IterableAccessor extends Iterable<VoxelShape> {
    @Accessor("val$entity")
    Entity getEntity();
}
