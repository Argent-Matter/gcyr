package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCyR;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;

import javax.annotation.Nonnull;

import static argent_matter.gcyr.api.registries.GCyRRegistries.REGISTRATE;


public class GCyRCreativeModeTabs {
    public static RegistryEntry<CreativeModeTab> GCYR = REGISTRATE.defaultCreativeTab(GCyR.MOD_ID,
                    builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(GCyR.MOD_ID, REGISTRATE))
                            .icon(GCyRItems.DYSON_CONSTRUCTION_DRONE::asStack)
                            .build())
            .register();

    public static void init() {

    }
}
