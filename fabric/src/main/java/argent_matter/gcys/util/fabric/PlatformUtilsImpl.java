package argent_matter.gcys.util.fabric;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;

public class PlatformUtilsImpl {
    public static Entity changeDimension(Entity entity, ServerLevel newDim) {
        return FabricDimensions.teleport(entity, newDim, new PortalInfo(entity.position(), Vec3.ZERO, entity.getYRot(), entity.getXRot()));
    }
}
