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
                    builder -> builder.displayItems(new RegistrateDisplayItemsGenerator(GCyR.MOD_ID))
                            .icon(GCyRItems.DYSON_CONSTRUCTION_DRONE::asStack)
                            .build())
            .register();

    public static void init() {

    }

    public static class RegistrateDisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {

        public final String name;

        public RegistrateDisplayItemsGenerator(String name) {
            this.name = name;
        }

        @Override
        public void accept(@Nonnull CreativeModeTab.ItemDisplayParameters itemDisplayParameters, @Nonnull CreativeModeTab.Output output) {
            var tab = REGISTRATE.get(name, Registries.CREATIVE_MODE_TAB);
            for (var entry : REGISTRATE.getAll(Registries.BLOCK)) {
                if (!REGISTRATE.isInCreativeTab(entry, tab))
                    continue;
                Item item = entry.get().asItem();
                if (item == Items.AIR)
                    continue;
                if (item instanceof ComponentItem componentItem) {
                    NonNullList<ItemStack> list = NonNullList.create();
                    componentItem.fillItemCategory(tab.get(), list);
                    list.forEach(output::accept);
                } else {
                    output.accept(item);
                }
            }
            for (var entry : REGISTRATE.getAll(Registries.ITEM)) {
                if (!REGISTRATE.isInCreativeTab(entry, tab))
                    continue;
                Item item = entry.get();
                if (item instanceof BlockItem)
                    continue;
                if (item instanceof ComponentItem componentItem) {
                    NonNullList<ItemStack> list = NonNullList.create();
                    componentItem.fillItemCategory(tab.get(), list);
                    list.forEach(output::accept);
                } else {
                    output.accept(item);
                }
            }
        }
    }
}
