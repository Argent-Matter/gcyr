package argent_matter.gcys.common.networking.c2s;

import argent_matter.gcys.common.data.GcysItems;
import com.lowdragmc.lowdraglib.networking.IHandlerContext;
import com.lowdragmc.lowdraglib.networking.IPacket;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.minecraft.core.Registry;
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
        this.id = buf.readResourceKey(Registry.DIMENSION_REGISTRY);
    }

    public void execute(IHandlerContext handler) {
        if (!handler.isClient() && id != null) {
            ItemStack handItem = handler.getPlayer().getItemInHand(handler.getPlayer().getUsedItemHand());
            if (handItem.is(GcysItems.ID_CHIP.get())) {
                handItem.getOrCreateTag().putString("gcys:current_planet", id.location().toString());
            }
        }
    }
}
