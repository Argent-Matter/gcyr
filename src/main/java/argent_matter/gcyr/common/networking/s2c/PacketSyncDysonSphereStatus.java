package argent_matter.gcyr.common.networking.s2c;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.GCYRClient;
import argent_matter.gcyr.common.networking.c2s.PacketSendSelectedDimension;
import com.lowdragmc.lowdraglib.networking.IHandlerContext;
import com.lowdragmc.lowdraglib.networking.IPacket;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@NoArgsConstructor
@AllArgsConstructor
public class PacketSyncDysonSphereStatus implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PacketSyncDysonSphereStatus> TYPE = new CustomPacketPayload.Type<>(GCYR.id("sync_dyson_sphere_status"));
    public static final StreamCodec<ByteBuf, PacketSyncDysonSphereStatus> CODEC = StreamCodec.ofMember(PacketSyncDysonSphereStatus::encode, PacketSyncDysonSphereStatus::decode);


    private boolean isSphereActive;

    public void encode(ByteBuf buf) {
        buf.writeBoolean(isSphereActive);
    }

    public static PacketSyncDysonSphereStatus decode(ByteBuf buf) {
        boolean isSphereActive = buf.readBoolean();
        return new PacketSyncDysonSphereStatus(isSphereActive);
    }

    public static void execute(PacketSyncDysonSphereStatus packet, IPayloadContext handler) {
        GCYRClient.isDysonSphereActive = packet.isSphereActive;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
