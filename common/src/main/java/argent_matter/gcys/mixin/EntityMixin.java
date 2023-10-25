package argent_matter.gcys.mixin;

import argent_matter.gcys.api.space.planet.Planet;
import argent_matter.gcys.data.loader.PlanetData;
import argent_matter.gcys.util.PlatformUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void gcys$tick(CallbackInfo ci) {
        Entity entity = ((Entity) (Object) this);
        if (!(entity.level() instanceof ServerLevel level)) return;

        // Teleport the entity to the planet when they fall in the void while in an orbit dimension
        if (entity.getY() < level.getMinBuildHeight() && PlanetData.isOrbitLevel(level.dimension())) {
            ServerLevel newLevel = level.getServer().getLevel(PlanetData.getPlanetFromOrbit(level.dimension()).map(Planet::level).orElse(Level.OVERWORLD));
            PlatformUtils.changeDimension(entity, newLevel);
        }
    }
}
