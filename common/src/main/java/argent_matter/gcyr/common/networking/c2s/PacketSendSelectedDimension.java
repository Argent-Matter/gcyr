package argent_matter.gcyr.common.networking.c2s;

import argent_matter.gcyr.common.data.GCyRItems;
import argent_matter.gcyr.common.item.PlanetIdChipBehaviour;
import com.lowdragmc.lowdraglib.networking.IHandlerContext;
import com.lowdragmc.lowdraglib.networking.IPacket;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@NoArgsConstructor
@AllArgsConstructor
public class PacketSendSelectedDimension implements IPacket {

    private ResourceLocation dimensionId;

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceLocation(dimensionId);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.dimensionId = buf.readResourceLocation();
    }

    public void execute(IHandlerContext handler) {
        if (!handler.isClient() && dimensionId != null) {
            ItemStack handItem = handler.getPlayer().getItemInHand(handler.getPlayer().getUsedItemHand());
            if (handItem.is(GCyRItems.ID_CHIP.get())) {
                handItem.getOrCreateTag().putString(PlanetIdChipBehaviour.CURRENT_PLANET_KEY, dimensionId.toString());
                handItem.getTag().remove(PlanetIdChipBehaviour.CURRENT_STATION_KEY);
            }
        }
    }
}
