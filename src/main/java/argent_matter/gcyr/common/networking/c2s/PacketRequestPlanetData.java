package argent_matter.gcyr.common.networking.c2s;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.GCYRNetworking;
import argent_matter.gcyr.common.entity.RocketEntity;
import argent_matter.gcyr.common.networking.s2c.PacketReturnPlanetData;
import com.lowdragmc.lowdraglib.networking.IHandlerContext;
import com.lowdragmc.lowdraglib.networking.IPacket;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@NoArgsConstructor
public class PacketRequestPlanetData implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PacketRequestPlanetData> TYPE = new CustomPacketPayload.Type<>(GCYR.id("request_planet_data"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketRequestPlanetData> CODEC = StreamCodec.ofMember(PacketRequestPlanetData::encode, PacketRequestPlanetData::decode);

    public void encode(RegistryFriendlyByteBuf buf) {

    }

    public static PacketRequestPlanetData decode(RegistryFriendlyByteBuf buf) {
        return new PacketRequestPlanetData();
    }

    public static void execute(PacketRequestPlanetData packet, IPayloadContext handler) {
        if (handler.player() instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new PacketReturnPlanetData());
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
