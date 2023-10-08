package argent_matter.gcys.common.data;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.data.recipe.GcysTags;
import com.gregtechceu.gtceu.api.block.RendererBlock;
import com.gregtechceu.gtceu.api.block.RendererGlassBlock;
import com.gregtechceu.gtceu.api.item.RendererBlockItem;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.client.renderer.block.TextureOverrideRenderer;
import com.lowdragmc.lowdraglib.Platform;
import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static argent_matter.gcys.api.registries.GcysRegistries.REGISTRATE;

public class GcysBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> GcysCreativeModeTabs.GCYS);
    }

    public static final BlockEntry<Block> CASING_ALUMINIUM_AEROSPACE = createCasingBlock("aluminium_aerospace", GregicalitySpace.id("block/casings/solid/machine_casing_aerospace"));


    public static final BlockEntry<RotatedPillarBlock> ROCKET_MOTOR = REGISTRATE
            .block("rocket_motor", RotatedPillarBlock::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Rocket Motor")
            .blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), prov.models().cubeBottomTop("rocket_motor",
                    GregicalitySpace.id("block/casings/rocket/rocket_motor_side"), GregicalitySpace.id("block/casings/rocket/rocket_motor_bottom"), GregicalitySpace.id("block/casings/rocket/rocket_motor_top")
            )))
            .tag(GTToolType.WRENCH.harvestTag, BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();

    public static final BlockEntry<DoorBlock> AIRLOCK_DOOR = REGISTRATE
            .block("airlock_door", DoorBlock::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Airlock Door")
            .properties(p -> p.strength(4.0F, 6.0F))
            .tag(GTToolType.WRENCH.harvestTag, BlockTags.MINEABLE_WITH_PICKAXE, GcysTags.BLOCKS_FLOOD_FILL)
            .blockstate((ctx, prov) -> prov.doorBlock(ctx.getEntry(), GregicalitySpace.id("block/airlock_door_bottom"), GregicalitySpace.id("block/airlock_door_top")))
            .item()
            .tag(ItemTags.DOORS)
            .defaultModel()
            .build()
            .register();

    public static final BlockEntry<Block> LAUNCH_PAD = REGISTRATE
            .block("launch_pad", Block::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Launch Pad")
            .blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry()))
            .tag(GTToolType.WRENCH.harvestTag, BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();

    public static final BlockEntry<RotatedPillarBlock> FUEL_TANK = REGISTRATE
            .block("fuel_tank", RotatedPillarBlock::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Fuel Tank")
            .blockstate((ctx, prov) -> prov.axisBlock(ctx.getEntry()))
            .tag(GTToolType.WRENCH.harvestTag, BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();
    public static final BlockEntry<Block> SEAT = REGISTRATE
            .block("seat", Block::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Seat")
            .blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), prov.models().carpet("seat", new ResourceLocation("block/light_gray_wool"))))
            .tag(GTToolType.WRENCH.harvestTag, BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();

    private static BlockEntry<Block> createCasingBlock(String name, ResourceLocation texture) {
        return createCasingBlock(name, RendererBlock::new, texture, () -> Blocks.IRON_BLOCK, () -> RenderType::cutoutMipped);
    }

    private static BlockEntry<Block> createGlassCasingBlock(String name, ResourceLocation texture, Supplier<Supplier<RenderType>> type) {
        return createCasingBlock(name, RendererGlassBlock::new, texture, () -> Blocks.GLASS, type);
    }

    private static BlockEntry<Block> createCasingBlock(String name, BiFunction<BlockBehaviour.Properties, IRenderer, ? extends RendererBlock> blockSupplier, ResourceLocation texture, NonNullSupplier<? extends Block> properties, Supplier<Supplier<RenderType>> type) {
        return REGISTRATE.block(name, p -> (Block) blockSupplier.apply(p,
                        Platform.isClient() ? new TextureOverrideRenderer(new ResourceLocation("block/cube_all"),
                                Map.of("all", texture)) : null))
                .initialProperties(properties)
                .addLayer(type)
                .blockstate(NonNullBiConsumer.noop())
                .tag(GTToolType.WRENCH.harvestTag, BlockTags.MINEABLE_WITH_PICKAXE)
                .item(RendererBlockItem::new)
                .model(NonNullBiConsumer.noop())
                .build()
                .register();
    }

    private static BlockEntry<Block> createBottomTopCasingBlock(String name, BiFunction<BlockBehaviour.Properties, IRenderer, ? extends RendererBlock> blockSupplier, ResourceLocation sideTexture, ResourceLocation topTexture, ResourceLocation bottomTexture, NonNullSupplier<? extends Block> properties, Supplier<Supplier<RenderType>> type) {
        return REGISTRATE.block(name, p -> (Block) blockSupplier.apply(p,
                        Platform.isClient() ? new TextureOverrideRenderer(new ResourceLocation("block/cube_bottom_top"),
                                Map.of("side", sideTexture, "top", topTexture, "bottom", bottomTexture)) : null))
                .initialProperties(properties)
                .addLayer(type)
                .blockstate(NonNullBiConsumer.noop())
                .tag(GTToolType.WRENCH.harvestTag, BlockTags.MINEABLE_WITH_PICKAXE)
                .item(RendererBlockItem::new)
                .model(NonNullBiConsumer.noop())
                .build()
                .register();
    }

    public static void init() {

    }
}
