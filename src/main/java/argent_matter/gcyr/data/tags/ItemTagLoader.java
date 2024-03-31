package argent_matter.gcyr.data.tags;

import argent_matter.gcyr.common.data.GCyRItems;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.item.Item;

public class ItemTagLoader {

    public static void init(RegistrateTagsProvider<Item> provider) {
        provider.addTag(ItemTags.TRIM_TEMPLATES)
                        .add(TagEntry.element(GCyRItems.SPACE_UPGRADE_SMITHING_TEMPLATE.getId()));
    }

}
