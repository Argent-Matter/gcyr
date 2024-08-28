package argent_matter.gcyr.common.networking.c2s;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.api.capability.GCYRCapabilityHelper;
import argent_matter.gcyr.api.capability.ISpaceStationHolder;
import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.common.data.GCYRItems;
import argent_matter.gcyr.common.item.behaviour.PlanetIdChipBehaviour;
import argent_matter.gcyr.data.loader.PlanetData;
import lombok.NoArgsConstructor;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@NoArgsConstructor
public class PacketCreateSpaceStation implements CustomPacketPayload {


    public static final CustomPacketPayload.Type<PacketCreateSpaceStation> TYPE = new CustomPacketPayload.Type<>(GCYR.id("create_space_station"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketCreateSpaceStation> CODEC = StreamCodec.ofMember(PacketCreateSpaceStation::encode, PacketCreateSpaceStation::decode);

    public void encode(RegistryFriendlyByteBuf buf) {
    }

    public static PacketCreateSpaceStation decode(RegistryFriendlyByteBuf buf) {
        return new PacketCreateSpaceStation();
    }

    public static void execute(PacketCreateSpaceStation packet, IPayloadContext handler) {
        if (handler.player().level() instanceof ServerLevel serverLevel) {
            ISpaceStationHolder holder = GCYRCapabilityHelper.getSpaceStations(serverLevel.getServer().getLevel(PlanetData.getPlanetFromLevelOrOrbit(serverLevel.dimension()).map(Planet::orbitWorld).orElse(null)));
            if (holder == null) return;

            ItemStack held = handler.player().getItemInHand(handler.player().getUsedItemHand());
            if (GCYRItems.ID_CHIP.isIn(held)) {
                PlanetIdChipBehaviour.setSpaceStation(held, holder.allocateStation(PlanetIdChipBehaviour.getPlanetFromStack(held)).getFirst());
            }
        }
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
