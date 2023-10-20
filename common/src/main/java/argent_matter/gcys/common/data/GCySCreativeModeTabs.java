package argent_matter.gcys.common.data;

import argent_matter.gcys.GCyS;
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.item.CreativeModeTab;

import static com.gregtechceu.gtceu.api.registry.GTRegistries.REGISTRATE;

public class GCySCreativeModeTabs {
    public static RegistryEntry<CreativeModeTab> GCYS = REGISTRATE.defaultCreativeTab(GCyS.MOD_ID,
                    builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(GCyS.MOD_ID))
                            .icon(GCySItems.DYSON_CONSTRUCTION_DRONE::asStack)
                            .build())
            .register();

    public static void init() {

    }
}
