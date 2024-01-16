package argent_matter.gcyr.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.ITeleporter;

public class PlatformUtils {

    public static Entity changeDimension(Entity entity, ServerLevel newDim) {
        return entity.changeDimension(newDim, new ITeleporter() {
            @Override
            public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
                return false;
            }
        });
    }
}
