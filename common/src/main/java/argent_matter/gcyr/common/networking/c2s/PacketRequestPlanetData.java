package argent_matter.gcyr.common.networking.c2s;

import argent_matter.gcyr.common.data.GCyRNetworking;
import argent_matter.gcyr.common.networking.s2c.PacketReturnPlanetData;
import com.lowdragmc.lowdraglib.networking.IHandlerContext;
import com.lowdragmc.lowdraglib.networking.IPacket;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;

@NoArgsConstructor
public class PacketRequestPlanetData implements IPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {

    }

    @Override
    public void decode(FriendlyByteBuf buf) {

    }

    @Override
    public void execute(IHandlerContext handler) {
        if (!handler.isClient()) {
            GCyRNetworking.NETWORK.sendToPlayer(new PacketReturnPlanetData(), handler.getPlayer());
        }
    }

}
