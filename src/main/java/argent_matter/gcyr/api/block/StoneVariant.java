package argent_matter.gcyr.api.block;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.client.GCYRModels;

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

import net.minecraftforge.common.Tags;

import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import org.jetbrains.annotations.Nullable;

// StoneVariant is a helper to produce basic stone/cobble variants.
// It cuts quite a bit on duplication, but it's mostly here to ensure consistency.
public class StoneVariant {

    private final GTRegistrate registrate;
    private final String name;
    private final BaseType stoneType, cobbleType;
    private final MapColor mapColor;

    public StoneVariant(GTRegistrate registrate, String name, BaseType stoneType, MapColor color) {
        this.registrate = registrate;
        this.name = name;

        this.stoneType = stoneType;
        this.cobbleType = switch (this.stoneType) {
            case STONE, COBBLESTONE -> BaseType.COBBLESTONE;
            case ROCK, COBBLED_ROCK -> BaseType.COBBLED_ROCK;
        };

        this.mapColor = color;
    }

    public BlockSetType blockSetType() {
        return BlockSetType.register(new BlockSetType(GCYR.id(name).toString()));
    }

    public BlockBuilder<Block, GTRegistrate> cobblestone(String lang) {
        return registrate.block(cobbleType.getFormattedId(name, null), Block::new)
                .lang(lang)
                .initialProperties(() -> cobbleType.baseBlock)
                .properties(p -> p.mapColor(mapColor))
                .blockstate(GCYRModels::randomRotatedModel)
                .tag(BlockTags.MINEABLE_WITH_PICKAXE, Tags.Blocks.COBBLESTONE)
                .item().tag(ItemTags.STONE_TOOL_MATERIALS).build();
    }

    // type is the ID segment for the stone block: "rock" or "stone"
    public BlockBuilder<Block, GTRegistrate> rock(String lang, BlockEntry<? extends Block> cobblestone) {
        return registrate.block(this.stoneType.getFormattedId(name, null), Block::new)
                .lang(lang)
                .initialProperties(() -> stoneType.baseBlock)
                .properties(p -> p.mapColor(mapColor))
                .blockstate(GCYRModels::randomRotatedModel)
                .loot((tables, block) -> tables.createSingleItemTableWithSilkTouch(block, cobblestone))
                .tag(BlockTags.MINEABLE_WITH_PICKAXE, Tags.Blocks.STONE)
                .simpleItem();
    }

    public BlockBuilder<SlabBlock, GTRegistrate> slab(String lang, BlockEntry<? extends Block> base) {
        return slab(this.stoneType, lang, base);
    }

    public BlockBuilder<SlabBlock, GTRegistrate> cobbleSlab(String lang, BlockEntry<? extends Block> base) {
        return slab(this.cobbleType, lang, base);
    }

    private BlockBuilder<SlabBlock, GTRegistrate> slab(BaseType type, String lang, BlockEntry<? extends Block> base) {
        return registrate.block(stoneType.formattableId.formatted(this.name) + "_slab", SlabBlock::new)
                .lang(lang)
                .initialProperties(() -> stoneType.baseBlock)
                .blockstate((ctx, prov) -> {
                    ResourceLocation texture = prov.blockTexture(base.get());
                    prov.slabBlock(ctx.getEntry(), texture, texture);
                })
                .tag(BlockTags.SLABS, BlockTags.MINEABLE_WITH_PICKAXE)
                .item()
                .tag(ItemTags.SLABS)
                .build();
    }

    public BlockBuilder<StairBlock, GTRegistrate> stairs(String lang, BlockEntry<? extends Block> base) {
        return stairs(this.stoneType, lang, base);
    }

    public BlockBuilder<StairBlock, GTRegistrate> cobbleStairs(String lang, BlockEntry<? extends Block> base) {
        return stairs(this.cobbleType, lang, base);
    }

    private BlockBuilder<StairBlock, GTRegistrate> stairs(BaseType type, String lang, BlockEntry<? extends Block> base) {
        return registrate.block(type.getFormattedId(name, "stairs"),
                (p) -> new StairBlock(base::getDefaultState, p))
                .lang(lang)
                .initialProperties(() -> type.baseBlock)
                .tag(BlockTags.STAIRS, BlockTags.MINEABLE_WITH_PICKAXE)
                .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), prov.blockTexture(base.get())))
                .item()
                .tag(ItemTags.STAIRS)
                .build();
    }

    // type is the ID segment for the button: "rock" or "stone"
    public BlockBuilder<ButtonBlock, GTRegistrate> button(String type, String lang,
                                                          BlockEntry<? extends Block> base, BlockSetType set) {
        return registrate.block(name + "_" + type + "_button",
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

    // map the name of a stone type to a StoneType, which has its full block, slab, and stair Block
    public enum BaseType {
        STONE("%s_stone", Blocks.STONE),
        COBBLESTONE("%s_cobblestone", Blocks.COBBLESTONE),
        ROCK("%s_rock", Blocks.DEEPSLATE),
        COBBLED_ROCK("cobbled_%s_rock", Blocks.COBBLED_DEEPSLATE);

        final String formattableId;
        final Block baseBlock;

        BaseType(String formattableId, Block baseBlock) {
            this.formattableId = formattableId;
            this.baseBlock = baseBlock;
        }

        public String getFormattedId(String variantName, @Nullable String postfix) {
            return this.formattableId.formatted(variantName) +
                    (postfix != null && !postfix.isEmpty() ? "_" + postfix : "");
        }
    }
}
