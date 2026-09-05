package argent_matter.gcyr.data.tags;

import argent_matter.gcyr.common.data.block.GCYRBlocks;
import argent_matter.gcyr.data.recipe.GCYRTags;

import com.gregtechceu.gtceu.common.data.GTBlocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.world.level.block.Blocks;

public class BlockTagLoader {

    public static void init(RegistrateTagsProvider.IntrinsicImpl<Block> provider) {
        provider.addTag(GCYRTags.MOON_ORE_REPLACEABLES)
                .add(GCYRBlocks.MOON_STONE);
        provider.addTag(GCYRTags.MARS_ORE_REPLACEABLES)
                .add(GCYRBlocks.MARTIAN_ROCK);
        provider.addTag(GCYRTags.PASSES_FLOOD_FILL)
                .addTag(BlockTags.FENCES).addTag(BlockTags.FENCE_GATES)
                .add(Blocks.IRON_BARS)
                .add(Blocks.TNT).add(GTBlocks.INDUSTRIAL_TNT);
    }
}
