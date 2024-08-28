package argent_matter.gcyr.data.tags;

import argent_matter.gcyr.common.data.GCYRItems;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;

public class ItemTagLoader {

    public static void init(RegistrateItemTagsProvider provider) {
        provider.addTag(ItemTags.TRIM_TEMPLATES)
                .add(GCYRItems.SPACE_UPGRADE_SMITHING_TEMPLATE.get());
        provider.addTag(ItemTags.HEAD_ARMOR)
                .add(GCYRItems.SPACE_SUIT_HELMET.get());
        provider.addTag(ItemTags.CHEST_ARMOR)
                .add(GCYRItems.SPACE_SUIT_CHEST.get());
        provider.addTag(ItemTags.LEG_ARMOR)
                .add(GCYRItems.SPACE_SUIT_LEGS.get());
        provider.addTag(ItemTags.FOOT_ARMOR)
                .add(GCYRItems.SPACE_SUIT_BOOTS.get());
    }

}
