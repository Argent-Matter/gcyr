package argent_matter.gcyr.common.data.forge;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.data.GCyRKeyMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GCyR.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GCyRKeyMappingsImpl {
    public static void initPlatform() {

    }

    //@SubscribeEvent
    public static void event(RegisterKeyMappingsEvent event) {
        event.register(GCyRKeyMappings.START_ROCKET);
    }
}
