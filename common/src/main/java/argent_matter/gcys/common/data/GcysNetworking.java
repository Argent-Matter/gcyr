package argent_matter.gcys.common.data;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.common.networking.c2s.PacketLaunchRocket;
import com.lowdragmc.lowdraglib.networking.INetworking;

import static com.lowdragmc.lowdraglib.networking.LDLNetworking.createNetworking;

public class GcysNetworking {

    public static final INetworking NETWORK = createNetworking(GregicalitySpace.id("networking"), "0.0.1");

    public static void init() {
        NETWORK.registerC2S(PacketLaunchRocket.class);
    }

}
