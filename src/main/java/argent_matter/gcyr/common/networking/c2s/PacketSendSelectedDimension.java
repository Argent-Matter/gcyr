package argent_matter.gcyr.common.networking.c2s;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.GCYRItems;
import argent_matter.gcyr.common.entity.RocketEntity;
import argent_matter.gcyr.common.item.behaviour.PlanetIdChipBehaviour;
import lombok.AllArgsConstructor;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@AllArgsConstructor
public class PacketSendSelectedDimension implements CustomPacketPayload {

    private ResourceLocation dimensionId;


    public static final CustomPacketPayload.Type<PacketSendSelectedDimension> TYPE = new CustomPacketPayload.Type<>(GCYR.id("send_selected_dimension"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketSendSelectedDimension> CODEC = StreamCodec.ofMember(PacketSendSelectedDimension::encode, PacketSendSelectedDimension::decode);

    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeResourceLocation(dimensionId);
    }

    public static PacketSendSelectedDimension decode(RegistryFriendlyByteBuf buf) {
        return new PacketSendSelectedDimension(buf.readResourceLocation());
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

    public static void execute(PacketSendSelectedDimension packet, IPayloadContext handler) {
        if (packet.dimensionId != null) {
            ItemStack handItem = handler.player().getItemInHand(handler.player().getUsedItemHand());
            if (handItem.is(GCYRItems.ID_CHIP.get())) {
                handItem.getOrCreateTag().putString(PlanetIdChipBehaviour.CURRENT_PLANET_KEY, dimensionId.toString());
                handItem.getTag().remove(PlanetIdChipBehaviour.CURRENT_STATION_KEY);
            }
        }
    }
}
