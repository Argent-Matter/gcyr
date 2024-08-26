package argent_matter.gcyr.common.networking.s2c;

import argent_matter.gcyr.GCYRClient;
import com.lowdragmc.lowdraglib.networking.IPacket;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;

@NoArgsConstructor
@AllArgsConstructor
public class PacketSyncDysonSphereStatus implements IPacket {
    private boolean isSphereActive;

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(isSphereActive);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.isSphereActive = buf.readBoolean();
        GCYRClient.isDysonSphereActive = isSphereActive;
    }
}
