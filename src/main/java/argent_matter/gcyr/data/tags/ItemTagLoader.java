package argent_matter.gcyr.data.tags;

import argent_matter.gcyr.common.data.GCyRItems;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

public class ItemTagLoader {

    public static void init(RegistrateItemTagsProvider provider) {
        provider.addTag(ItemTags.TRIM_TEMPLATES)
                .add(GCyRItems.SPACE_UPGRADE_SMITHING_TEMPLATE.get());
        provider.addTag(Tags.Items.ARMORS_HELMETS)
                .add(GCyRItems.SPACE_SUIT_HELMET.get());
        provider.addTag(Tags.Items.ARMORS_CHESTPLATES)
                .add(GCyRItems.SPACE_SUIT_CHEST.get());
        provider.addTag(Tags.Items.ARMORS_LEGGINGS)
                .add(GCyRItems.SPACE_SUIT_LEGS.get());
        provider.addTag(Tags.Items.ARMORS_BOOTS)
                .add(GCyRItems.SPACE_SUIT_BOOTS.get());
    }

}
