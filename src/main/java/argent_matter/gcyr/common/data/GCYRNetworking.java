package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.networking.c2s.*;
import argent_matter.gcyr.common.networking.s2c.PacketReturnPlanetData;
import argent_matter.gcyr.common.networking.s2c.PacketSyncDysonSphereStatus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = GCYR.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class GCYRNetworking {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registar = event.registrar(GCYR.MOD_ID);
        registar.playToServer(PacketLaunchRocket.TYPE, PacketLaunchRocket.CODEC, PacketLaunchRocket::execute);
        registar.playToServer(PacketRequestPlanetData.TYPE, PacketRequestPlanetData.CODEC, PacketRequestPlanetData::execute);
        registar.playToServer(PacketCreateSpaceStation.TYPE, PacketCreateSpaceStation.CODEC, PacketCreateSpaceStation::execute);
        registar.playToServer(PacketSendSelectedDimension.TYPE, PacketSendSelectedDimension.CODEC, PacketSendSelectedDimension::execute);

        registar.playToClient(PacketReturnPlanetData.TYPE, PacketReturnPlanetData.CODEC, PacketReturnPlanetData::execute);
        registar.playToClient(PacketSyncDysonSphereStatus.TYPE, PacketSyncDysonSphereStatus.CODEC, PacketSyncDysonSphereStatus::execute);
    }

}
