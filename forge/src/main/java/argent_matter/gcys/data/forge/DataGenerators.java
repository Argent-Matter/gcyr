package argent_matter.gcys.data.forge;

import argent_matter.gcys.api.registries.registrate.GcysSoundEntryProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            var provider = new GcysSoundEntryProvider(generator);
            generator.addProvider(true, provider);
        }
    }
}