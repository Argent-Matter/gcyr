package argent_matter.gcyr.data.tags;

import argent_matter.gcyr.common.data.block.GCYRBlocks;
import argent_matter.gcyr.common.data.tag.GCYRTags;

import com.gregtechceu.gtceu.common.data.GTBlocks;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import com.tterrag.registrate.providers.RegistrateTagsProvider;

public class BlockTagLoader {

    public static void init(RegistrateTagsProvider.IntrinsicImpl<Block> provider) {
        provider.addTag(GCYRTags.Blocks.MOON_ORE_REPLACEABLES)
                .add(GCYRBlocks.MOON_STONE);
        provider.addTag(GCYRTags.Blocks.MARS_ORE_REPLACEABLES)
                .add(GCYRBlocks.MARTIAN_ROCK);
        provider.addTag(GCYRTags.Blocks.PASSES_FLOOD_FILL)
                .addTag(BlockTags.FENCES).addTag(BlockTags.FENCE_GATES)
                .add(Blocks.IRON_BARS)
                .add(Blocks.TNT).add(GTBlocks.INDUSTRIAL_TNT);
    }
}
