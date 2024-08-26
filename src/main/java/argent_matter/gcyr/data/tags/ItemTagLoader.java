package argent_matter.gcyr.data.tags;

import argent_matter.gcyr.common.data.GCYRItems;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

public class ItemTagLoader {

    public static void init(RegistrateItemTagsProvider provider) {
        provider.addTag(ItemTags.TRIM_TEMPLATES)
                .add(GCYRItems.SPACE_UPGRADE_SMITHING_TEMPLATE.get());
        provider.addTag(Tags.Items.ARMORS_HELMETS)
                .add(GCYRItems.SPACE_SUIT_HELMET.get());
        provider.addTag(Tags.Items.ARMORS_CHESTPLATES)
                .add(GCYRItems.SPACE_SUIT_CHEST.get());
        provider.addTag(Tags.Items.ARMORS_LEGGINGS)
                .add(GCYRItems.SPACE_SUIT_LEGS.get());
        provider.addTag(Tags.Items.ARMORS_BOOTS)
                .add(GCYRItems.SPACE_SUIT_BOOTS.get());
    }

}
