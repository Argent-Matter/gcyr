package argent_matter.gcyr.core.mixin;

import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.data.loader.PlanetData;
import argent_matter.gcyr.util.PlatformUtils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public abstract Level level();

    @Shadow
    public abstract double getY();

    @Inject(method = "tick", at = @At("TAIL"))
    private void gcyr$tick(CallbackInfo ci) {
        if (!(this.level() instanceof ServerLevel level)) return;

        // Teleport the entity to the planet when they fall in the void while in an orbit dimension
        if (!(this.getY() < level.getMinBuildHeight()) || !PlanetData.isOrbitDimension(level.dimension())) {
            return;
        }

        var targetDimension = PlanetData.getPlanetFromOrbit(level.dimension())
                .map(Planet::dimension)
                .orElse(Level.OVERWORLD);
        ServerLevel newLevel = level.getServer().getLevel(targetDimension);
        Entity newEntity = PlatformUtils.changeDimension((Entity) (Object) this, newLevel);
        newEntity.setPos(newEntity.getX(), 600.0, newEntity.getZ());
    }
}
