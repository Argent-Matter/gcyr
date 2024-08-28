package argent_matter.gcyr.common.networking.s2c;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.GCYRClient;
import argent_matter.gcyr.common.networking.c2s.PacketRequestPlanetData;
import argent_matter.gcyr.data.loader.PlanetData;
import com.lowdragmc.lowdraglib.networking.IPacket;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@NoArgsConstructor
public class PacketReturnPlanetData implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PacketReturnPlanetData> TYPE = new CustomPacketPayload.Type<>(GCYR.id("return_planet_data"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketReturnPlanetData> CODEC = StreamCodec.ofMember(PacketReturnPlanetData::encode, PacketReturnPlanetData::decode);


    public void encode(FriendlyByteBuf buf) {
        PlanetData.writePlanetData(buf);
    }

    public static PacketReturnPlanetData decode(FriendlyByteBuf buf) {
        PlanetData.readPlanetData(buf);
        return new PacketReturnPlanetData();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return null;
    }

    public static void execute(PacketReturnPlanetData packet, IPayloadContext handler) {
        GCYRClient.hasUpdatedPlanets = true;
    }
}
