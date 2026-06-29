package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;

import java.util.Map;

// StoneVariant is a helper to produce basic stone/cobble variants.
// It cuts quite a bit on duplication but it's mostly here to ensure consistency.
public class StoneVariant {

    record StoneType(Block block, Block slab, Block stairs) {}

    // map the name of a stone type to a StoneType, which has its full block, slab, and stair Block
    private static final Map<String, StoneType> TYPES = Map.of(
            "cobblestone", new StoneType(Blocks.COBBLESTONE, Blocks.COBBLESTONE_SLAB, Blocks.COBBLESTONE_STAIRS),
            "rock", new StoneType(Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_STAIRS),
            "stone", new StoneType(Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_STAIRS));

    private final GTRegistrate registrate;
    private final String prefix;
    private final MapColor mapColor;

    public StoneVariant(GTRegistrate registrate, String prefix, MapColor color) {
        this.registrate = registrate;
        this.prefix = prefix;
        this.mapColor = color;
    }

    public BlockSetType blockSetType() {
        return BlockSetType.register(new BlockSetType(GCYR.id(prefix).toString()));
    }

    public BlockBuilder<Block, GTRegistrate> cobblestone(String lang) {
        return registrate.block(prefix + "_cobblestone", Block::new)
                .lang(lang)
                .initialProperties(() -> Blocks.COBBLESTONE)
                .properties(p -> p.mapColor(mapColor))
                .blockstate(GCYRModels::randomRotatedModel)
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .simpleItem();
    }

    // type is the ID segment for the stone block: "rock" or "stone"
    public BlockBuilder<Block, GTRegistrate> rock(String type, String lang, BlockEntry<Block> cobblestone) {
        return registrate.block(prefix + "_" + type, Block::new)
                .lang(lang)
                .initialProperties(() -> Blocks.STONE)
                .properties(p -> p.mapColor(mapColor))
                .blockstate(GCYRModels::randomRotatedModel)
                .loot((table, block) -> table.dropOther(block, cobblestone.asItem()))
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .simpleItem();
    }

    public BlockBuilder<SlabBlock, GTRegistrate> slab(String type, String lang, BlockEntry<Block> base) {
        Block props = TYPES.get(type).slab();
        return registrate.block(prefix + "_" + type + "_slab", SlabBlock::new)
                .lang(lang)
                .initialProperties(() -> props)
                .blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(),
                        prov.blockTexture(base.get()), prov.blockTexture(base.get())))
                .tag(BlockTags.SLABS, BlockTags.MINEABLE_WITH_PICKAXE)
                .item()
                .tag(ItemTags.SLABS)
                .build();
    }

    public BlockBuilder<StairBlock, GTRegistrate> stairs(String type, String lang, BlockEntry<Block> base) {
        Block props = TYPES.get(type).stairs();
        return registrate.block(prefix + "_" + type + "_stairs",
                (p) -> new StairBlock(base::getDefaultState, p))
                .lang(lang)
                .initialProperties(() -> props)
                .tag(BlockTags.STAIRS, BlockTags.MINEABLE_WITH_PICKAXE)
                .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), prov.blockTexture(base.get())))
                .item()
                .tag(ItemTags.STAIRS)
                .build();
    }

    // type is the ID segment for the button: "rock" or "stone"
    public BlockBuilder<ButtonBlock, GTRegistrate> button(String type, String lang, BlockEntry<Block> base,
                                                          BlockSetType set) {
        return registrate.block(prefix + "_" + type + "_button",
                (p) -> new ButtonBlock(p, set, 30, false))
                .lang(lang)
                .initialProperties(() -> Blocks.STONE_BUTTON)
                .tag(BlockTags.BUTTONS, BlockTags.MINEABLE_WITH_PICKAXE)
                .blockstate((ctx, prov) -> prov.buttonBlock(ctx.getEntry(), prov.blockTexture(base.get())))
                .item()
                .model((ctx, prov) -> prov.buttonInventory(ctx.getName(), base.getId().withPrefix("block/")))
                .tag(ItemTags.BUTTONS)
                .build();
    }
}
