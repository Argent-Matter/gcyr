package argent_matter.gcys.data.tags;

import argent_matter.gcys.data.recipe.GCySTags;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BlockTagLoader {

    public static void init(RegistrateTagsProvider<Block> provider) {
        createBlock(provider, GCySTags.PASSES_FLOOD_FILL, "#fences", "iron_bars", "tnt");
    }

    @ExpectPlatform
    private static void createBlock(RegistrateTagsProvider<Block> provider, TagKey<Block> tagKey, String... rls) {
        throw new AssertionError();
    }

    public static ResourceLocation rl(String name) {
        return new ResourceLocation(name);
    }

}
