package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.api.block.IFuelTankProperties;
import argent_matter.gcyr.api.block.IRocketMotorType;
import argent_matter.gcyr.common.block.FuelTankBlock;
import argent_matter.gcyr.common.block.RocketMotorBlock;
import argent_matter.gcyr.data.recipe.GCyRTags;
import com.gregtechceu.gtceu.api.block.RendererBlock;
import com.gregtechceu.gtceu.api.block.RendererGlassBlock;
import com.gregtechceu.gtceu.api.item.RendererBlockItem;
import com.gregtechceu.gtceu.client.renderer.block.TextureOverrideRenderer;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.lowdragmc.lowdraglib.Platform;
import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static argent_matter.gcyr.api.registries.GCyRRegistries.REGISTRATE;

@SuppressWarnings("unused")
public class  GCyRBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> GCyRCreativeModeTabs.GCYR);
    }

    // region casings
    public static final BlockEntry<Block> CASING_ALUMINIUM_AEROSPACE = createCasingBlock("aerospace_aluminium_casing", GCyR.id("block/casings/solid/machine_casing_aerospace"));
    public static final BlockEntry<Block> CASING_BEAM_RECEIVER = createCasingBlock("beam_receiver", GCyR.id("block/casings/solid/beam_receiver"));
    public static final BlockEntry<Block> CASING_SUPPORT = createCasingBlock("space_elevator_support", GCyR.id("block/casings/solid/space_elevator_support"));

    public static final BlockEntry<Block> CASING_DYSON_SPHERE = createCasingBlock("dyson_sphere_casing", GCyR.id("block/casings/solid/dyson_sphere"));
    public static final BlockEntry<Block> CASING_DYSON_CELL = createCasingBlock("dyson_solar_cell", GCyR.id("block/casings/solid/dyson_solar_cell"));
    public static final BlockEntry<Block> CASING_DYSON_PORT = createCasingBlock("dyson_sphere_maintenance_port", GCyR.id("block/casings/solid/dyson_sphere_maintenance_port"));

    public static final BlockEntry<Block> CASING_STAINLESS_EVAPORATION = createCasingBlock("stainless_evaporation_casing", GCyR.id("block/casings/solid/machine_casing_stainless_evaporation"));

    // region mercury

    public static final BlockSetType MERCURY_SET = BlockSetType.register(new BlockSetType(GCyR.id("mercury").toString()));

    public static final BlockEntry<Block> MERCURY_COBBLESTONE = REGISTRATE
            .block("mercury_rock", Block::new)
            .lang("Cobbled Mercury Rock")
            .initialProperties(() -> Blocks.COBBLESTONE)
            .properties(properties -> properties.mapColor(MapColor.COLOR_GRAY))
            .blockstate(GCyRModels::randomRotatedModel)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();
    public static final BlockEntry<Block> MERCURY_ROCK = REGISTRATE
            .block("mercury_rock", Block::new)
            .lang("Mercury Rock")
            .properties(properties -> properties.mapColor(MapColor.COLOR_GRAY))
            .blockstate(GCyRModels::randomRotatedModel)
            .loot((table, block) -> table.dropOther(block, MERCURY_COBBLESTONE.asItem()))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();

    public static final BlockEntry<SlabBlock> MERCURY_COBBLESTONE_SLAB = REGISTRATE
            .block("mercury_cobblestone_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.COBBLESTONE_SLAB)
            .lang("Cobbled Mercury Rock Slab")
            .blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.MERCURY_COBBLESTONE.get()), prov.blockTexture(GCyRBlocks.MERCURY_COBBLESTONE.get())))
            .tag(BlockTags.SLABS, BlockTags.MINEABLE_WITH_PICKAXE)
            .item()
            .tag(ItemTags.SLABS)
            .build()
            .register();

    public static final BlockEntry<SlabBlock> MERCURY_ROCK_SLAB = REGISTRATE
            .block("mercury_rock_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.STONE_SLAB)
            .lang("Mercury Rock Slab")
        .blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.MERCURY_ROCK.get()), prov.blockTexture(GCyRBlocks.MERCURY_ROCK.get())))
            .tag(BlockTags.SLABS, BlockTags.MINEABLE_WITH_PICKAXE)
            .item()
            .tag(ItemTags.SLABS)
            .build()
            .register();

    public static final BlockEntry<StairBlock> MERCURY_COBBLESTONE_STAIRS = REGISTRATE
            .block("cobbled_mercury_rock_stairs", (p) -> new StairBlock(MERCURY_COBBLESTONE::getDefaultState, p))
            .initialProperties(() -> Blocks.COBBLESTONE_STAIRS)
            .lang("Cobbled Mercury Rock Stairs")
            .tag(BlockTags.STAIRS, BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.MERCURY_COBBLESTONE.get())))
            .item()
            .tag(ItemTags.STAIRS)
            .build()
            .register();

    public static final BlockEntry<StairBlock> MERCURY_ROCK_STAIRS = REGISTRATE
            .block("mercury_rock_stairs", (p) -> new StairBlock(MERCURY_ROCK::getDefaultState, p))
            .initialProperties(() -> Blocks.STONE_STAIRS)
            .lang("Mercury Rock Stairs")
            .tag(BlockTags.STAIRS, BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.MERCURY_ROCK.get())))
            .item()
            .tag(ItemTags.STAIRS)
            .build()
            .register();

    public static final BlockEntry<ButtonBlock> MERCURY_ROCK_BUTTON = REGISTRATE
            .block("mercury_rock_button", (p) -> new ButtonBlock(p, MERCURY_SET, 30, false))
            .initialProperties(() -> Blocks.STONE_BUTTON)
            .lang("Mercury Rock Button")
            .tag(BlockTags.BUTTONS, BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> prov.buttonBlock(ctx.getEntry(), prov.blockTexture(MERCURY_ROCK.get())))
            .item()
            .model((ctx, prov) -> prov.buttonInventory(ctx.getName(), GCyRBlocks.MERCURY_ROCK.getId().withPrefix("block/")))
            .tag(ItemTags.BUTTONS)
            .build()
            .register();

    //endregion

    //region venus

    public static final BlockSetType VENUS_SET = BlockSetType.register(new BlockSetType(GCyR.id("venus").toString()));
    public static final BlockEntry<FallingBlock> VENUS_SAND = REGISTRATE
            .block("venus_sand", FallingBlock::new)
            .lang("Venus Sand")
            .initialProperties(() -> Blocks.SAND)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_ORANGE))
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .blockstate(GCyRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> VENUSIAN_REGOLITH = REGISTRATE
            .block("venusian_regolith", Block::new)
            .lang("Venusian Regolith")
            .initialProperties(() -> Blocks.SANDSTONE)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_GRAY))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCyRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> VENUS_COBBLESTONE = REGISTRATE
            .block("venus_cobblestone", Block::new)
            .lang("Cobbled Venus Rock")
            .initialProperties(() -> Blocks.COBBLESTONE)
            .properties(properties -> properties.mapColor(MapColor.COLOR_GRAY))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCyRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> VENUS_ROCK = REGISTRATE
            .block("venus_rock", Block::new)
            .lang("Venus Rock")
            .initialProperties(() -> Blocks.STONE)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCyRModels::randomRotatedModel)
            .loot((table, block) -> table.dropOther(block, VENUS_COBBLESTONE.asItem()))
            .simpleItem()
            .register();

    public static final BlockEntry<SlabBlock> VENUS_COBBLESTONE_SLAB = REGISTRATE
            .block("venus_cobblestone_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.COBBLESTONE_SLAB)
            .lang("Cobbled Venus Rock Slab")
            .blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.VENUS_COBBLESTONE.get()), prov.blockTexture(GCyRBlocks.VENUS_COBBLESTONE.get())))
            .tag(BlockTags.SLABS, BlockTags.MINEABLE_WITH_PICKAXE)
            .item()
            .tag(ItemTags.SLABS)
            .build()
            .register();

    public static final BlockEntry<SlabBlock> VENUS_ROCK_SLAB = REGISTRATE
            .block("venus_rock_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.STONE_SLAB)
            .lang("Venus Rock Slab")
            .blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.VENUS_ROCK.get()), prov.blockTexture(GCyRBlocks.VENUS_ROCK.get())))
            .tag(BlockTags.SLABS, BlockTags.MINEABLE_WITH_PICKAXE)
            .item()
            .tag(ItemTags.SLABS)
            .build()
            .register();

    public static final BlockEntry<StairBlock> VENUS_COBBLESTONE_STAIRS = REGISTRATE
            .block("venus_cobblestone_stairs", (p) -> new StairBlock(VENUS_COBBLESTONE::getDefaultState, p))
            .initialProperties(() -> Blocks.COBBLESTONE_STAIRS)
            .lang("Cobbled Venus Rock Stairs")
            .tag(BlockTags.STAIRS, BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.VENUS_COBBLESTONE.get())))
            .item()
            .tag(ItemTags.STAIRS)
            .build()
            .register();

    public static final BlockEntry<StairBlock> VENUS_ROCK_STAIRS = REGISTRATE
            .block("venus_rock_stairs", (p) -> new StairBlock(VENUS_ROCK::getDefaultState, p))
            .initialProperties(() -> Blocks.STONE_STAIRS)
            .lang("Venus Rock Stairs")
            .tag(BlockTags.STAIRS, BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.VENUS_ROCK.get())))
            .item()
            .tag(ItemTags.STAIRS)
            .build()
            .register();

    public static final BlockEntry<ButtonBlock> VENUS_ROCK_BUTTON = REGISTRATE
            .block("venus_rock_button", (p) -> new ButtonBlock(p, VENUS_SET, 30, false))
            .initialProperties(() -> Blocks.STONE_BUTTON)
            .lang("Venus Rock Button")
            .tag(BlockTags.BUTTONS, BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> prov.buttonBlock(ctx.getEntry(), prov.blockTexture(VENUS_ROCK.get())))
            .item()
            .model((ctx, prov) -> prov.buttonInventory(ctx.getName(), GCyRBlocks.VENUS_ROCK.getId().withPrefix("block/")))
            .tag(ItemTags.BUTTONS)
            .build()
            .register();

    // region moon

    public static final BlockSetType MOON_SET = BlockSetType.register(new BlockSetType(GCyR.id("moon").toString()));

    public static final BlockEntry<Block> MOON_COBBLESTONE = REGISTRATE
            .block("moon_cobblestone", Block::new)
            .lang("Cobbled Lunar Stone")
            .initialProperties(() -> Blocks.COBBLESTONE)
            .properties(properties -> properties.mapColor(MapColor.COLOR_GRAY))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCyRModels::randomRotatedModel)
            .simpleItem()
            .register();
    public static final BlockEntry<Block> MOON_STONE = REGISTRATE
            .block("moon_stone", Block::new)
            .lang("Lunar Stone")
            .initialProperties(() -> Blocks.STONE)
            .properties(properties -> properties.mapColor(MapColor.COLOR_LIGHT_GRAY))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCyRModels::randomRotatedModel)
            .loot((table, block) -> table.dropOther(block, MOON_COBBLESTONE.asItem()))
            .simpleItem()
            .register();
    public static final BlockEntry<FallingBlock> MOON_SAND = REGISTRATE
            .block("moon_sand", FallingBlock::new)
            .lang("Lunar Sand")
            .initialProperties(() -> Blocks.GRAVEL)
            .tag(BlockTags.MINEABLE_WITH_SHOVEL)
            .blockstate(GCyRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<SlabBlock> MOON_COBBLESTONE_SLAB = REGISTRATE
            .block("moon_cobblestone_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.COBBLESTONE_SLAB)
            .lang("Cobbled Lunar Stone Slab")
            .blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.MOON_COBBLESTONE.get()), prov.blockTexture(GCyRBlocks.MOON_COBBLESTONE.get())))
            .tag(BlockTags.SLABS, BlockTags.MINEABLE_WITH_PICKAXE)
            .item()
            .tag(ItemTags.SLABS)
            .build()
            .register();

    public static final BlockEntry<SlabBlock> MOON_STONE_SLAB = REGISTRATE
            .block("moon_stone_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.STONE_SLAB)
            .lang("Lunar Stone Slab")
            .blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.MOON_STONE.get()), prov.blockTexture(GCyRBlocks.MOON_STONE.get())))
            .tag(BlockTags.SLABS, BlockTags.MINEABLE_WITH_PICKAXE)
            .item()
            .tag(ItemTags.SLABS)
            .build()
            .register();

    public static final BlockEntry<StairBlock> MOON_COBBLESTONE_STAIRS = REGISTRATE
            .block("moon_cobblestone_stairs", (p) -> new StairBlock(MOON_COBBLESTONE::getDefaultState, p))
        .initialProperties(() -> Blocks.COBBLESTONE_STAIRS)
            .lang("Cobbled Lunar Stone Stairs")
            .tag(BlockTags.STAIRS, BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.MOON_COBBLESTONE.get())))
            .item()
            .tag(ItemTags.STAIRS)
            .build()
            .register();

    public static final BlockEntry<StairBlock> MOON_STONE_STAIRS = REGISTRATE
            .block("moon_stone_stairs", (p) -> new StairBlock(MOON_STONE::getDefaultState, p))
            .initialProperties(() -> Blocks.STONE_STAIRS)
            .lang("Lunar Stone Stairs")
            .tag(BlockTags.STAIRS, BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.MOON_STONE.get())))
            .item()
            .tag(ItemTags.STAIRS)
            .build()
            .register();

    public static final BlockEntry<ButtonBlock> MOON_STONE_BUTTON = REGISTRATE
            .block("moon_stone_button", (p) -> new ButtonBlock(p, MOON_SET, 30, false))
            .initialProperties(() -> Blocks.STONE_BUTTON)
            .lang("Lunar Stone Button")
            .tag(BlockTags.BUTTONS, BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> prov.buttonBlock(ctx.getEntry(), prov.blockTexture(MOON_STONE.get())))
            .item()
            .model((ctx, prov) -> prov.buttonInventory(ctx.getName(), GCyRBlocks.MOON_STONE.getId().withPrefix("block/")))
            .tag(ItemTags.BUTTONS)
            .build()
            .register();

    // region mars

    public static final BlockSetType MARS_SET = BlockSetType.register(new BlockSetType(GCyR.id("martian").toString()));

    public static final BlockEntry<FallingBlock> MARS_REGOLITH = REGISTRATE
            .block("mars_regolith", FallingBlock::new)
            .lang("Martian Regolith")
            .initialProperties(()  -> Blocks.RED_SAND)
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> MARTIAN_COBBLESTONE = REGISTRATE
            .block("martian_cobblestone", Block::new)
            .lang("Cobbled Martian Rock")
            .initialProperties(() -> Blocks.COBBLESTONE)
            .properties(properties -> properties.mapColor(MapColor.COLOR_GRAY))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCyRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> MARTIAN_ROCK = REGISTRATE
            .block("martian_rock", Block::new)
            .lang("Martian Rock")
            .initialProperties(() -> Blocks.STONE)
            .properties(properties -> properties.mapColor(MapColor.COLOR_RED))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .loot((table, block) -> table.dropOther(block, MARTIAN_COBBLESTONE.asItem()))
            .simpleItem()
            .register();

    public static final BlockEntry<SlabBlock> MARTIAN_COBBLESTONE_SLAB = REGISTRATE
            .block("martian_cobblestone_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.COBBLESTONE_SLAB)
            .lang("Cobbled Martian Rock Slab")
            .blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.MARTIAN_COBBLESTONE.get()), prov.blockTexture(GCyRBlocks.MARTIAN_COBBLESTONE.get())))
            .tag(BlockTags.SLABS, BlockTags.MINEABLE_WITH_PICKAXE)
            .item()
            .tag(ItemTags.SLABS)
            .build()
            .register();

    public static final BlockEntry<SlabBlock> MARTIAN_ROCK_SLAB = REGISTRATE
            .block("martian_rock_slab", SlabBlock::new)
            .initialProperties(() -> Blocks.STONE_SLAB)
            .lang("Martian Rock Slab")
            .blockstate((ctx, prov) -> prov.slabBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.MARTIAN_ROCK.get()), prov.blockTexture(GCyRBlocks.MARTIAN_ROCK.get())))
            .tag(BlockTags.SLABS, BlockTags.MINEABLE_WITH_PICKAXE)
            .item()
            .tag(ItemTags.SLABS)
            .build()
            .register();

    public static final BlockEntry<StairBlock> MARTIAN_COBBLESTONE_STAIRS = REGISTRATE
            .block("martian_cobblestone_stairs", (p) -> new StairBlock(MARTIAN_COBBLESTONE::getDefaultState, p))
            .initialProperties(() -> Blocks.COBBLESTONE_STAIRS)
            .lang("Cobbled Martian Rock Stairs")
            .tag(BlockTags.STAIRS, BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.MARTIAN_COBBLESTONE.get())))
            .item()
            .tag(ItemTags.STAIRS)
            .build()
            .register();

    public static final BlockEntry<StairBlock> MARTIAN_ROCK_STAIRS = REGISTRATE
            .block("martian_rock_stairs", (p) -> new StairBlock(MARTIAN_ROCK::getDefaultState, p))
            .initialProperties(() -> Blocks.STONE_STAIRS)
            .lang("Martian Rock Stairs")
            .tag(BlockTags.STAIRS, BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> prov.stairsBlock(ctx.getEntry(), prov.blockTexture(GCyRBlocks.MARTIAN_ROCK.get())))
            .item()
            .tag(ItemTags.STAIRS)
            .build()
            .register();

    public static final BlockEntry<ButtonBlock> MARTIAN_ROCK_BUTTON = REGISTRATE
            .block("martian_rock_button", (p) -> new ButtonBlock(p, MARS_SET, 30, false))
            .initialProperties(() -> Blocks.STONE_BUTTON)
            .lang("Martian Rock Button")
            .tag(BlockTags.BUTTONS, BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate((ctx, prov) -> prov.buttonBlock(ctx.getEntry(), prov.blockTexture(MARTIAN_ROCK.get())))
            .item()
            .model((ctx, prov) -> prov.buttonInventory(ctx.getName(), GCyRBlocks.MARTIAN_ROCK.getId().withPrefix("block/")))
            .tag(ItemTags.BUTTONS)
            .build()
            .register();

    // erm what the sigma
    public static final BlockEntry<MushroomBlock> PRB_SHROOM = REGISTRATE
            .block("prb_underground_mushroom", (p) -> new MushroomBlock(p, null /*todo fix*/))
            .initialProperties(() -> Blocks.BROWN_MUSHROOM)
            .properties(p -> p.mapColor(MapColor.COLOR_CYAN))
            .addLayer(() -> RenderType::cutout)
            .blockstate(GCyRModels::crossModel)
            .item()
            .model(GCyRModels::blockTextureGeneratedModel)
            .build()
            .register();

    // region rocket stuff
    public static final Map<IRocketMotorType, Supplier<RocketMotorBlock>> ALL_ROCKET_MOTORS = new HashMap<>();
    public static final BlockEntry<RocketMotorBlock> BASIC_ROCKET_MOTOR = createRocketMotor(RocketMotorBlock.RocketMotorType.BASIC);
    public static final BlockEntry<RocketMotorBlock> ADVANCED_ROCKET_MOTOR = createRocketMotor(RocketMotorBlock.RocketMotorType.ADVANCED);
    public static final BlockEntry<RocketMotorBlock> ELITE_ROCKET_MOTOR = createRocketMotor(RocketMotorBlock.RocketMotorType.ELITE);

    public static final BlockEntry<DoorBlock> AIRLOCK_DOOR = REGISTRATE
            .block("airlock_door", properties -> new DoorBlock(properties, BlockSetType.IRON))
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Airlock Door")
            .properties(p -> p.strength(4.0F, 6.0F))
            .tag(GCyRTags.MINEABLE_WITH_WRENCH, BlockTags.MINEABLE_WITH_PICKAXE, GCyRTags.BLOCKS_FLOOD_FILL)
            .blockstate(GCyRModels::airlockDoorModel)
            .item()
            .tag(ItemTags.DOORS)
            .defaultModel()
            .build()
            .register();

    public static final BlockEntry<Block> LAUNCH_PAD = REGISTRATE
            .block("launch_pad", Block::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Launch Pad")
            .defaultBlockstate()
            .tag(GCyRTags.MINEABLE_WITH_WRENCH, BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();

    public static final BlockEntry<CarpetBlock> SEAT = REGISTRATE
            .block("seat", CarpetBlock::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Seat")
            .blockstate(GCyRModels::seatModel)
            .tag(GCyRTags.MINEABLE_WITH_WRENCH, BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();

    public static final Map<IFuelTankProperties, Supplier<FuelTankBlock>> ALL_FUEL_TANKS = new HashMap<>();
    public static final BlockEntry<FuelTankBlock> BASIC_FUEL_TANK = createFuelTank(FuelTankBlock.FuelTankProperties.BASIC);
    public static final BlockEntry<FuelTankBlock> ADVANCED_FUEL_TANK = createFuelTank(FuelTankBlock.FuelTankProperties.ADVANCED);
    public static final BlockEntry<FuelTankBlock> ELITE_FUEL_TANK = createFuelTank(FuelTankBlock.FuelTankProperties.ELITE);

    // endregion

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
                .tag(GCyRTags.MINEABLE_WITH_WRENCH, BlockTags.MINEABLE_WITH_PICKAXE)
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
                .tag(GCyRTags.MINEABLE_WITH_WRENCH, BlockTags.MINEABLE_WITH_PICKAXE)
                .item(RendererBlockItem::new)
                .model(NonNullBiConsumer.noop())
                .build()
                .register();
    }

    private static BlockEntry<FuelTankBlock> createFuelTank(IFuelTankProperties properties) {
        BlockEntry<FuelTankBlock> block = REGISTRATE.block("%s_fuel_tank".formatted(properties.getSerializedName()), (p) -> new FuelTankBlock(p, properties))
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .lang("%s Fuel Tank".formatted(FormattingUtil.toEnglishName(properties.getSerializedName())))
                .blockstate(GCyRModels::fuelTankModel)
                .tag(GCyRTags.MINEABLE_WITH_WRENCH)
                .simpleItem()
                .register();
        ALL_FUEL_TANKS.put(properties, block);
        return block;
    }

    private static BlockEntry<RocketMotorBlock> createRocketMotor(IRocketMotorType properties) {
        BlockEntry<RocketMotorBlock> block = REGISTRATE.block("%s_rocket_motor".formatted(properties.getSerializedName()), (p) -> new RocketMotorBlock(p, properties))
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .lang("%s Rocket Motor".formatted(FormattingUtil.toEnglishName(properties.getSerializedName())))
                .blockstate((ctx, prov) -> GCyRModels.rocketMotorModel(ctx, prov, properties))
                .tag(GCyRTags.MINEABLE_WITH_WRENCH)
                .simpleItem()
                .register();
        ALL_ROCKET_MOTORS.put(properties, block);
        return block;
    }

    public static void init() {

    }
}
