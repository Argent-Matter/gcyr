package argent_matter.gcyr.data.tags;

import argent_matter.gcyr.common.data.item.GCYRItems;

import net.minecraft.tags.ItemTags;

import net.minecraftforge.common.Tags;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;

public class ItemTagLoader {

    public static void init(RegistrateItemTagsProvider provider) {
        provider.addTag(ItemTags.TRIM_TEMPLATES)
                .add(GCYRItems.SPACE_UPGRADE_SMITHING_TEMPLATE)
                .add(GCYRItems.SPACE_SUIT_THERMAL_UPGRADE_SMITHING_TEMPLATE);

        provider.addTag(Tags.Items.ARMORS_HELMETS)
                .add(GCYRItems.SPACE_SUIT_HELMET);
        provider.addTag(Tags.Items.ARMORS_CHESTPLATES)
                .add(GCYRItems.SPACE_SUIT_CHEST);
        provider.addTag(Tags.Items.ARMORS_LEGGINGS)
                .add(GCYRItems.SPACE_SUIT_LEGS);
        provider.addTag(Tags.Items.ARMORS_BOOTS)
                .add(GCYRItems.SPACE_SUIT_BOOTS);
    }
}
