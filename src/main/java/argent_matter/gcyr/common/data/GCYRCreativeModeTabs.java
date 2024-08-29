package argent_matter.gcyr.common.data;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import static argent_matter.gcyr.api.registries.GCYRRegistries.REGISTRATE;


public class GCYRCreativeModeTabs {
    public static RegistryEntry<CreativeModeTab, CreativeModeTab> GCYR = REGISTRATE.defaultCreativeTab(argent_matter.gcyr.GCYR.MOD_ID,
                    builder -> builder//.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(argent_matter.gcyr.GCYR.MOD_ID, REGISTRATE))
                            .icon(GCYRItems.DYSON_CONSTRUCTION_DRONE::asStack)
                            .title(Component.literal("Gregicality Rocketry"))
                            .build())
            .register();

    public static void init() {

    }
}
