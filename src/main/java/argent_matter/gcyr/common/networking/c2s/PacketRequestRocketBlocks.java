package argent_matter.gcyr.common.networking.c2s;

import argent_matter.gcyr.common.entity.RocketEntity;
import com.lowdragmc.lowdraglib.networking.IHandlerContext;
import com.lowdragmc.lowdraglib.networking.IPacket;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

@NoArgsConstructor
@AllArgsConstructor
public class PacketRequestRocketBlocks implements IPacket {

    private int entityId;

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        buf.readInt();
    }

    @Override
    public void execute(IHandlerContext handler) {
        Entity ent = handler.getLevel().getEntity(entityId);
        if (ent instanceof RocketEntity rocket) {
            rocket.getEntityData().set(RocketEntity.POSITIONED_STATES, rocket.getBlocks(), true);
        }
    }
}
