package argent_matter.gcyr.common.networking.s2c;

import argent_matter.gcyr.GCyRClient;
import argent_matter.gcyr.data.loader.PlanetData;
import com.lowdragmc.lowdraglib.networking.IPacket;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;

@NoArgsConstructor
public class PacketReturnPlanetData implements IPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        PlanetData.writePlanetData(buf);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        PlanetData.readPlanetData(buf);
        GCyRClient.hasUpdatedPlanets = true;
    }
}
