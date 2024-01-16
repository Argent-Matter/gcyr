package argent_matter.gcyr.data.tags.forge;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static argent_matter.gcyr.data.tags.BlockTagLoader.rl;

public class BlockTagLoaderImpl {
    public static void createBlock(RegistrateTagsProvider<Block> provider, TagKey<Block> tagKey, String... rls) {
        var builder = provider.addTag(tagKey);
        for (String str : rls) {
            if (str.startsWith("#")) builder.addOptionalTag(rl(str.substring(1)));
            else builder.addOptional(rl(str));
        }
    }
}
