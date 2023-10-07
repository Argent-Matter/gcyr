package argent_matter.gcys.common.data.forge;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.common.data.GcysKeyMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GregicalitySpace.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GcysKeyMappingsImpl {
    public static void initPlatform() {

    }

    @SubscribeEvent
    public static void event(RegisterKeyMappingsEvent event) {
        event.register(GcysKeyMappings.START_ROCKET);
    }
}
