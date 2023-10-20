package argent_matter.gcys.common.networking.c2s;

import argent_matter.gcys.common.data.GCySItems;
import argent_matter.gcys.common.item.PlanetIdChipBehaviour;
import com.lowdragmc.lowdraglib.networking.IHandlerContext;
import com.lowdragmc.lowdraglib.networking.IPacket;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@NoArgsConstructor
@AllArgsConstructor
public class PacketSendSelectedDimension implements IPacket {

    private ResourceKey<Level> id;

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceKey(id);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.id = buf.readResourceKey(Registries.DIMENSION);
    }

    public void execute(IHandlerContext handler) {
        if (!handler.isClient() && id != null) {
            ItemStack handItem = handler.getPlayer().getItemInHand(handler.getPlayer().getUsedItemHand());
            if (handItem.is(GCySItems.ID_CHIP.get())) {
                handItem.getOrCreateTag().putString(PlanetIdChipBehaviour.CURRENT_PLANET_TAG_ID, id.location().toString());
                handItem.getTag().remove(PlanetIdChipBehaviour.CURRENT_STATION_TAG_ID);
            }
        }
    }
}
