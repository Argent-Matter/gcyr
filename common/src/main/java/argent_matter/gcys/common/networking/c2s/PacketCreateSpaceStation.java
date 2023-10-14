package argent_matter.gcys.common.networking.c2s;

import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.api.capability.ISpaceStationHolder;
import argent_matter.gcys.common.data.GcysDimensionTypes;
import argent_matter.gcys.common.data.GcysItems;
import argent_matter.gcys.common.item.PlanetIdChipBehaviour;
import com.lowdragmc.lowdraglib.networking.IHandlerContext;
import com.lowdragmc.lowdraglib.networking.IPacket;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;

@NoArgsConstructor
public class PacketCreateSpaceStation implements IPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
    }

    @Override
    public void execute(IHandlerContext handler) {
        if (handler.getLevel() instanceof ServerLevel serverLevel) {
            ISpaceStationHolder holder = GcysCapabilityHelper.getSpaceStations(serverLevel.getServer().getLevel(GcysDimensionTypes.SPACE_LEVEL));
            if (holder == null) return;

            ItemStack held = handler.getPlayer().getItemInHand(handler.getPlayer().getUsedItemHand());
            if (GcysItems.ID_CHIP.isIn(held)) {
                PlanetIdChipBehaviour.setSpaceStation(holder.allocateStation(PlanetIdChipBehaviour.getPlanetFromStack(held)).getFirst(), held);
            }
        }
    }

}
