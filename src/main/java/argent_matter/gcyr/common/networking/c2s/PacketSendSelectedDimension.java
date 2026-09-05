package argent_matter.gcyr.common.networking.c2s;

import argent_matter.gcyr.common.data.item.GCYRItems;
import argent_matter.gcyr.common.item.behaviour.PlanetIdChipBehaviour;

import com.lowdragmc.lowdraglib.networking.IHandlerContext;
import com.lowdragmc.lowdraglib.networking.IPacket;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PacketSendSelectedDimension implements IPacket {

    private ResourceKey<Level> dimension;

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceKey(dimension);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.dimension = buf.readResourceKey(Registries.DIMENSION);
    }

    public void execute(IHandlerContext handler) {
        if (handler.isClient() || dimension == null) {
            return;
        }

        ItemStack handItem = handler.getPlayer().getItemInHand(handler.getPlayer().getUsedItemHand());
        if (handItem.is(GCYRItems.ID_CHIP.get())) {
            handItem.getOrCreateTag().putString(PlanetIdChipBehaviour.CURRENT_PLANET_KEY, dimension.toString());
            handItem.getTag().remove(PlanetIdChipBehaviour.CURRENT_STATION_KEY);
        }
    }
}
