package argent_matter.gcyr.common.data.item;

import argent_matter.gcyr.GCYR;
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import com.tterrag.registrate.util.entry.RegistryEntry;

import static argent_matter.gcyr.api.registries.GCYRRegistries.REGISTRATE;

public class GCYRCreativeModeTabs {

    public static RegistryEntry<CreativeModeTab> CREATIVE_TAB = REGISTRATE.defaultCreativeTab(GCYR.MOD_ID,
            builder -> builder
                    .displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(GCYR.MOD_ID, REGISTRATE))
                    .icon(GCYRItems.DYSON_CONSTRUCTION_DRONE::asStack)
                    .title(Component.literal("Gregicality Rocketry"))
                    .build())
            .register();

    public static void init() {}
}
