package argent_matter.gcyr.data.tags;

import argent_matter.gcyr.data.recipe.GCyRTags;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BlockTagLoader {

    public static void init(RegistrateTagsProvider<Block> provider) {
        createBlock(provider, GCyRTags.PASSES_FLOOD_FILL, "#fences", "iron_bars", "tnt");
    }

    private static void createBlock(RegistrateTagsProvider<Block> provider, TagKey<Block> tagKey, String... rls) {
        TagBuilder builder = provider.getOrCreateRawBuilder(tagKey);
        for (String str : rls) {
            if (str.startsWith("#")) builder.addOptionalTag(rl(str.substring(1)));
            else builder.addElement(rl(str));
        }
        builder.build();
    }

    private static ResourceLocation rl(String name) {
        return new ResourceLocation(name);
    }

}
