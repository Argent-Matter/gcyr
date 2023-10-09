package argent_matter.gcys.common.networking.s2c;

import argent_matter.gcys.GregicalitySpaceClient;
import argent_matter.gcys.data.loader.PlanetData;
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
        GregicalitySpaceClient.hasUpdatedPlanets = true;
    }
}
