package argent_matter.gcys.common.data;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.common.networking.c2s.PacketCreateSpaceStation;
import argent_matter.gcys.common.networking.c2s.PacketLaunchRocket;
import argent_matter.gcys.common.networking.c2s.PacketRequestPlanetData;
import argent_matter.gcys.common.networking.c2s.PacketSendSelectedDimension;
import argent_matter.gcys.common.networking.s2c.PacketReturnPlanetData;
import com.lowdragmc.lowdraglib.networking.INetworking;

import static com.lowdragmc.lowdraglib.networking.LDLNetworking.createNetworking;

public class GcysNetworking {

    public static final INetworking NETWORK = createNetworking(GregicalitySpace.id("networking"), "0.0.1");

    public static void init() {
        NETWORK.registerC2S(PacketLaunchRocket.class);
        NETWORK.registerC2S(PacketRequestPlanetData.class);
        NETWORK.registerC2S(PacketSendSelectedDimension.class);
        NETWORK.registerC2S(PacketCreateSpaceStation.class);

        NETWORK.registerS2C(PacketReturnPlanetData.class);
    }

}
