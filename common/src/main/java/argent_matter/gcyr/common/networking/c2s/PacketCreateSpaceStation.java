package argent_matter.gcyr.common.networking.c2s;

import argent_matter.gcyr.api.capability.GCyRCapabilityHelper;
import argent_matter.gcyr.api.capability.ISpaceStationHolder;
import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.common.data.GCyRItems;
import argent_matter.gcyr.common.item.PlanetIdChipBehaviour;
import argent_matter.gcyr.data.loader.PlanetData;
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
            ISpaceStationHolder holder = GCyRCapabilityHelper.getSpaceStations(serverLevel.getServer().getLevel(PlanetData.getPlanetFromLevelOrOrbit(serverLevel.dimension()).map(Planet::orbitWorld).orElse(null)));
            if (holder == null) return;

            ItemStack held = handler.getPlayer().getItemInHand(handler.getPlayer().getUsedItemHand());
            if (GCyRItems.ID_CHIP.isIn(held)) {
                PlanetIdChipBehaviour.setSpaceStation(held, holder.allocateStation(PlanetIdChipBehaviour.getPlanetFromStack(held)).getFirst());
            }
        }
    }

}
