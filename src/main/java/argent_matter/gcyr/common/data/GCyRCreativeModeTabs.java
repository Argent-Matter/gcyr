package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCyR;
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import static argent_matter.gcyr.api.registries.GCyRRegistries.REGISTRATE;


public class GCyRCreativeModeTabs {
    public static RegistryEntry<CreativeModeTab> GCYR = REGISTRATE.defaultCreativeTab(GCyR.MOD_ID,
                    builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(GCyR.MOD_ID, REGISTRATE))
                            .icon(GCyRItems.DYSON_CONSTRUCTION_DRONE::asStack)
                            .title(Component.literal("Gregicality Rocketry"))
                            .build())
            .register();

    public static void init() {

    }
}
