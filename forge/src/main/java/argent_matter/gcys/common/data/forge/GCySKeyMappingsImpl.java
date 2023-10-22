package argent_matter.gcys.common.data.forge;

import argent_matter.gcys.GCyS;
import argent_matter.gcys.common.data.GCySKeyMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GCyS.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GCySKeyMappingsImpl {
    public static void initPlatform() {

    }

    //@SubscribeEvent
    public static void event(RegisterKeyMappingsEvent event) {
        event.register(GCySKeyMappings.START_ROCKET);
    }
}
