package argent_matter.gcys.util.forge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class PlatformUtilsImpl {
    public static Entity changeDimension(Entity entity, ServerLevel newDim) {
        return entity.changeDimension(newDim, new ITeleporter() {
            @Override
            public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
                return false;
            }
        });
    }
}
