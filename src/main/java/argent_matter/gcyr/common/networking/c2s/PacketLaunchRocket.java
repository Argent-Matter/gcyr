package argent_matter.gcyr.common.networking.c2s;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.entity.RocketEntity;
import lombok.NoArgsConstructor;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@NoArgsConstructor
public class PacketLaunchRocket implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PacketLaunchRocket> TYPE = new CustomPacketPayload.Type<>(GCYR.id("launch_rocket"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketLaunchRocket> CODEC = StreamCodec.ofMember(PacketLaunchRocket::encode, PacketLaunchRocket::decode);

    public void encode(RegistryFriendlyByteBuf buf) {

    }

    public static PacketLaunchRocket decode(RegistryFriendlyByteBuf buf) {
        return new PacketLaunchRocket();
    }

    public static void execute(PacketLaunchRocket packet, IPayloadContext handler) {
        if (handler.player().getVehicle() instanceof RocketEntity rocketEntity) {
            rocketEntity.startRocket();
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
