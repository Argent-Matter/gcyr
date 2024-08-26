package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.networking.c2s.*;
import argent_matter.gcyr.common.networking.s2c.PacketReturnPlanetData;
import argent_matter.gcyr.common.networking.s2c.PacketSyncDysonSphereStatus;
import com.lowdragmc.lowdraglib.networking.INetworking;

import static com.lowdragmc.lowdraglib.networking.LDLNetworking.createNetworking;

public class GCYRNetworking {

    public static final INetworking NETWORK = createNetworking(GCYR.id("networking"), "0.0.1");

    public static void init() {
        NETWORK.registerC2S(PacketLaunchRocket.class);
        NETWORK.registerC2S(PacketRequestPlanetData.class);
        NETWORK.registerC2S(PacketSendSelectedDimension.class);
        NETWORK.registerC2S(PacketCreateSpaceStation.class);
        NETWORK.registerC2S(PacketRequestRocketBlocks.class);

        NETWORK.registerS2C(PacketReturnPlanetData.class);
        NETWORK.registerS2C(PacketSyncDysonSphereStatus.class);
    }

}
