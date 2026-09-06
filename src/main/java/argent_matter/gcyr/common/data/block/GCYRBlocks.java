package argent_matter.gcyr.common.data.block;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.api.block.IFuelTankProperties;
import argent_matter.gcyr.api.block.IRocketMotorType;
import argent_matter.gcyr.api.block.StoneVariant;
import argent_matter.gcyr.common.block.FuelTankBlock;
import argent_matter.gcyr.common.block.RocketMotorBlock;
import argent_matter.gcyr.common.data.client.GCYRModels;
import argent_matter.gcyr.common.data.item.GCYRCreativeModeTabs;
import argent_matter.gcyr.common.data.tag.GCYRTags;

import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

import net.minecraftforge.common.Tags;

import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static argent_matter.gcyr.api.registries.GCYRRegistries.REGISTRATE;

@SuppressWarnings("unused")
public class GCYRBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> GCYRCreativeModeTabs.CREATIVE_TAB);
    }

    // region casings

    // spotless:off
    public static final BlockEntry<Block> CASING_ALUMINIUM_AEROSPACE = createCasingBlock("aerospace_aluminium_casing", GCYR.id("block/casings/solid/machine_casing_aerospace"));
    public static final BlockEntry<Block> CASING_BEAM_RECEIVER = createCasingBlock("beam_receiver", GCYR.id("block/casings/solid/beam_receiver"));
    public static final BlockEntry<Block> CASING_SUPPORT = createCasingBlock("space_elevator_support", GCYR.id("block/casings/solid/space_elevator_support"));

    public static final BlockEntry<Block> CASING_DYSON_SPHERE = createCasingBlock("dyson_sphere_casing", GCYR.id("block/casings/solid/dyson_sphere"));
    public static final BlockEntry<Block> CASING_DYSON_CELL = createCasingBlock("dyson_solar_cell", GCYR.id("block/casings/solid/dyson_solar_cell"));
    public static final BlockEntry<Block> CASING_DYSON_PORT = createCasingBlock("dyson_sphere_maintenance_port", GCYR.id("block/casings/solid/dyson_sphere_maintenance_port"));

    public static final BlockEntry<Block> CASING_STAINLESS_EVAPORATION = createCasingBlock("stainless_evaporation_casing", GCYR.id("block/casings/solid/machine_casing_stainless_evaporation"));
    // spotless:on

    // endregion

    // region mercury

    private static final StoneVariant MERCURY_VARIANT = new StoneVariant(REGISTRATE, "mercurian",
            StoneVariant.BaseType.ROCK, MapColor.COLOR_GRAY);
    public static final BlockSetType MERCURY_SET = MERCURY_VARIANT.blockSetType();

    // todo needs better texture
    public static final BlockEntry<Block> MERCURY_COBBLESTONE = MERCURY_VARIANT
            .cobblestone("Cobbled Mercurian Rock")
            .register();

    public static final BlockEntry<Block> MERCURY_ROCK = MERCURY_VARIANT
            .rock("Mercurian Rock", MERCURY_COBBLESTONE)
            .register();

    public static final BlockEntry<SlabBlock> MERCURY_COBBLESTONE_SLAB = MERCURY_VARIANT
            .cobbleSlab("Cobbled Mercurian Rock Slab", MERCURY_COBBLESTONE)
            .register();

    public static final BlockEntry<SlabBlock> MERCURY_ROCK_SLAB = MERCURY_VARIANT
            .slab("Mercurian Rock Slab", MERCURY_ROCK)
            .register();

    public static final BlockEntry<StairBlock> MERCURY_COBBLESTONE_STAIRS = MERCURY_VARIANT
            .cobbleStairs("Cobbled Mercurian Rock Stairs", MERCURY_COBBLESTONE)
            .register();

    public static final BlockEntry<StairBlock> MERCURY_ROCK_STAIRS = MERCURY_VARIANT
            .stairs("Mercurian Rock Stairs", MERCURY_ROCK)
            .register();

    public static final BlockEntry<ButtonBlock> MERCURY_ROCK_BUTTON = MERCURY_VARIANT
            .button("Mercurian Rock Button", MERCURY_ROCK, MERCURY_SET)
            .register();

    // endregion

    // region venus

    private static final StoneVariant VENUS_VARIANT = new StoneVariant(REGISTRATE, "venusian",
            StoneVariant.BaseType.ROCK, MapColor.COLOR_GRAY);
    public static final BlockSetType VENUS_SET = VENUS_VARIANT.blockSetType();

    // todo needs better texture
    public static final BlockEntry<Block> VENUS_COBBLESTONE = VENUS_VARIANT
            .cobblestone("Cobbled Venusian Rock")
            .register();

    public static final BlockEntry<Block> VENUS_ROCK = VENUS_VARIANT
            .rock("Venusian Rock", VENUS_COBBLESTONE)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY))
            .register();

    public static final BlockEntry<SlabBlock> VENUS_COBBLESTONE_SLAB = VENUS_VARIANT
            .cobbleSlab("Cobbled Venusian Rock Slab", VENUS_COBBLESTONE)
            .register();

    public static final BlockEntry<SlabBlock> VENUS_ROCK_SLAB = VENUS_VARIANT
            .slab("Venusian Rock Slab", VENUS_ROCK)
            .register();

    public static final BlockEntry<StairBlock> VENUS_COBBLESTONE_STAIRS = VENUS_VARIANT
            .cobbleStairs("Cobbled Venusian Rock Stairs", VENUS_COBBLESTONE)
            .register();

    public static final BlockEntry<StairBlock> VENUS_ROCK_STAIRS = VENUS_VARIANT
            .stairs("Venusian Rock Stairs", VENUS_ROCK)
            .register();

    public static final BlockEntry<ButtonBlock> VENUS_ROCK_BUTTON = VENUS_VARIANT
            .button("Venusian Rock Button", VENUS_ROCK, VENUS_SET)
            .register();

    public static final BlockEntry<FallingBlock> VENUS_SAND = REGISTRATE
            .block("venusian_sand", FallingBlock::new)
            .lang("Venusian Sand")
            .initialProperties(() -> Blocks.SAND)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_ORANGE))
            .tag(BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.SAND)
            .blockstate(GCYRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<FallingBlock> VENUSIAN_REGOLITH = REGISTRATE
            .block("venusian_regolith", FallingBlock::new)
            .lang("Venusian Regolith")
            .initialProperties(() -> Blocks.GRAVEL)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_GRAY))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCYRModels::randomRotatedModel)
            .simpleItem()
            .register();

    // endregion

    // region moon

    private static final StoneVariant LUNA_VARIANT = new StoneVariant(REGISTRATE, "lunar", StoneVariant.BaseType.STONE,
            MapColor.COLOR_GRAY);
    public static final BlockSetType LUNA_SET = LUNA_VARIANT.blockSetType();

    // todo needs better texture
    public static final BlockEntry<Block> LUNAR_COBBLESTONE = LUNA_VARIANT
            .cobblestone("Cobbled Lunar Stone")
            .register();

    public static final BlockEntry<Block> LUNAR_STONE = LUNA_VARIANT
            .rock("Lunar Stone", LUNAR_COBBLESTONE)
            .properties(p -> p.mapColor(MapColor.COLOR_LIGHT_GRAY))
            .register();

    public static final BlockEntry<SlabBlock> LUNAR_COBBLESTONE_SLAB = LUNA_VARIANT
            .cobbleSlab("Cobbled Lunar Stone Slab", LUNAR_COBBLESTONE)
            .register();

    public static final BlockEntry<SlabBlock> LUNAR_STONE_SLAB = LUNA_VARIANT
            .slab("Lunar Stone Slab", LUNAR_STONE)
            .register();

    public static final BlockEntry<StairBlock> LUNAR_COBBLESTONE_STAIRS = LUNA_VARIANT
            .cobbleStairs("Cobbled Lunar Stone Stairs", LUNAR_COBBLESTONE)
            .register();

    public static final BlockEntry<StairBlock> LUNAR_STONE_STAIRS = LUNA_VARIANT
            .stairs("Lunar Stone Stairs", LUNAR_STONE)
            .register();

    public static final BlockEntry<ButtonBlock> LUNAR_STONE_BUTTON = LUNA_VARIANT
            .button("Lunar Stone Button", LUNAR_STONE, LUNA_SET)
            .register();

    public static final BlockEntry<FallingBlock> LUNAR_MARE_REGOLITH = REGISTRATE
            .block("lunar_mare_regolith", FallingBlock::new)
            .lang("Lunar Mare Regolith")
            .initialProperties(() -> Blocks.GRAVEL)
            .properties(properties -> properties.mapColor(MapColor.COLOR_LIGHT_GRAY))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCYRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<FallingBlock> LUNAR_SAND = REGISTRATE
            .block("lunar_sand", FallingBlock::new)
            .lang("Lunar Sand")
            .initialProperties(() -> Blocks.SAND)
            .properties(properties -> properties.mapColor(MapColor.STONE))
            .tag(BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.SAND)
            .blockstate(GCYRModels::lunarSandModel)
            .simpleItem()
            .register();

    // endregion

    // region mars

    private static final StoneVariant MARS_VARIANT = new StoneVariant(REGISTRATE, "martian", StoneVariant.BaseType.ROCK,
            MapColor.COLOR_GRAY);
    public static final BlockSetType MARS_SET = MARS_VARIANT.blockSetType();

    public static final BlockEntry<FallingBlock> MARS_REGOLITH = REGISTRATE
            .block("martian_regolith", FallingBlock::new)
            .lang("Martian Regolith")
            .initialProperties(() -> Blocks.GRAVEL)
            .properties(properties -> properties.mapColor(MapColor.COLOR_ORANGE))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();

    // todo needs better texture
    public static final BlockEntry<Block> MARTIAN_COBBLESTONE = MARS_VARIANT
            .cobblestone("Cobbled Martian Rock")
            .register();

    public static final BlockEntry<Block> MARTIAN_ROCK = MARS_VARIANT
            .rock("Martian Rock", MARTIAN_COBBLESTONE)
            .properties(p -> p.mapColor(MapColor.COLOR_RED))
            .register();

    public static final BlockEntry<SlabBlock> MARTIAN_COBBLESTONE_SLAB = MARS_VARIANT
            .cobbleSlab("Cobbled Martian Rock Slab", MARTIAN_COBBLESTONE)
            .register();

    public static final BlockEntry<SlabBlock> MARTIAN_ROCK_SLAB = MARS_VARIANT
            .slab("Martian Rock Slab", MARTIAN_ROCK)
            .register();

    public static final BlockEntry<StairBlock> MARTIAN_COBBLESTONE_STAIRS = MARS_VARIANT
            .cobbleStairs("Cobbled Martian Rock Stairs", MARTIAN_COBBLESTONE)
            .register();

    public static final BlockEntry<StairBlock> MARTIAN_ROCK_STAIRS = MARS_VARIANT
            .stairs("Martian Rock Stairs", MARTIAN_ROCK)
            .register();

    public static final BlockEntry<ButtonBlock> MARTIAN_ROCK_BUTTON = MARS_VARIANT
            .button("Martian Rock Button", MARTIAN_ROCK, MARS_SET)
            .register();

    // endregion

    // region proxima centauri b

    private static final StoneVariant PROXIMA_CENTAURI_B_VARIANT = new StoneVariant(REGISTRATE, "proximian",
            StoneVariant.BaseType.STONE, MapColor.COLOR_GRAY);
    public static final BlockSetType PROXIMA_CENTAURI_B_SET = PROXIMA_CENTAURI_B_VARIANT.blockSetType();

    // todo needs better texture
    public static final BlockEntry<Block> PROXIMIAN_COBBLESTONE = PROXIMA_CENTAURI_B_VARIANT
            .cobblestone("Cobbled Proximian Stone")
            .initialProperties(() -> Blocks.COBBLED_DEEPSLATE)
            .register();

    // todo needs better texture
    public static final BlockEntry<RotatedPillarBlock> PROXIMIAN_STONE = REGISTRATE
            .block("proximian_stone", RotatedPillarBlock::new)
            .lang("Proximian Stone")
            .initialProperties(() -> Blocks.DEEPSLATE)
            .properties(p -> p.mapColor(MapColor.COLOR_RED))
            // .blockstate((ctx, prov) -> prov.axisBlock(ctx.getEntry()))
            .loot((tables, block) -> tables.add(block, tables.createSingleItemTableWithSilkTouch(block, PROXIMIAN_COBBLESTONE)))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, Tags.Blocks.STONE)
            .simpleItem()
            .register();

    public static final BlockEntry<SlabBlock> PROXIMIAN_COBBLESTONE_SLAB = PROXIMA_CENTAURI_B_VARIANT
            .cobbleSlab("Cobbled Proximian Stone Slab", PROXIMIAN_COBBLESTONE)
            .initialProperties(() -> Blocks.COBBLED_DEEPSLATE_SLAB)
            .register();

    public static final BlockEntry<SlabBlock> PROXIMIAN_STONE_SLAB = PROXIMA_CENTAURI_B_VARIANT
            .slab("Proximian Stone Slab", PROXIMIAN_STONE)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .register();

    public static final BlockEntry<StairBlock> PROXIMIAN_COBBLESTONE_STAIRS = PROXIMA_CENTAURI_B_VARIANT
            .cobbleStairs("Cobbled Proximian Stone Stairs", PROXIMIAN_COBBLESTONE)
            .initialProperties(() -> Blocks.COBBLED_DEEPSLATE_STAIRS)
            .register();

    public static final BlockEntry<StairBlock> PROXIMIAN_STONE_STAIRS = PROXIMA_CENTAURI_B_VARIANT
            .stairs("Proximian Stone Stairs", PROXIMIAN_STONE)
            .initialProperties(() -> Blocks.DEEPSLATE)
            .register();

    public static final BlockEntry<ButtonBlock> PROXIMIAN_STONE_BUTTON = PROXIMA_CENTAURI_B_VARIANT
            .button("Proximian Stone Button", PROXIMIAN_STONE, PROXIMA_CENTAURI_B_SET)
            .initialProperties(() -> Blocks.COBBLED_DEEPSLATE)
            .register();

    public static final BlockEntry<FallingBlock> PROXIMIAN_REGOLITH = REGISTRATE
            .block("proximian_regolith", FallingBlock::new)
            .lang("Proximian Regolith")
            .initialProperties(() -> Blocks.GRAVEL)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_BLACK))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCYRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PROXIMIAN_TURF = REGISTRATE
            .block("proximian_turf", Block::new)
            .lang("Proximian Turf")
            .initialProperties(() -> Blocks.COARSE_DIRT)
            .properties(properties -> properties.mapColor(MapColor.SAND))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCYRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PROXIMIAN_HARDENED_TURF = REGISTRATE
            .block("hardened_proximian_turf", Block::new)
            .lang("Hardened Proximian Turf")
            .initialProperties(() -> Blocks.PACKED_MUD)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_WHITE))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCYRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PROXIMIAN_GRASSY_TURF = REGISTRATE
            .block("grassy_proximian_turf", Block::new)
            .lang("Grassy Proximian Turf")
            .initialProperties(() -> Blocks.GRASS)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCYRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<MushroomBlock> PROXIMIAN_SHROOM = REGISTRATE
            .block("proximian_mushroom", (p) -> new MushroomBlock(p, null /* todo fix */))
            .lang("Proximian Mushroom")
            .initialProperties(() -> Blocks.BROWN_MUSHROOM)
            .properties(p -> p.mapColor(MapColor.COLOR_LIGHT_GRAY).lightLevel((arg) -> 11))
            .addLayer(() -> RenderType::cutout)
            .blockstate(GCYRModels::crossModel)
            .item()
            .tag(Tags.Items.MUSHROOMS)
            .model(GCYRModels::blockTextureGeneratedModel)
            .build()
            .register();

    public static final BlockEntry<MushroomBlock> POXIMIAN_BULB = REGISTRATE
            .block("proximian_bulb", (p) -> new MushroomBlock(p, null))
            .lang("Proximian Bulb")
            .initialProperties(() -> Blocks.BROWN_MUSHROOM)
            .properties(p -> p.mapColor(MapColor.COLOR_LIGHT_GRAY).lightLevel(state -> 1))
            .addLayer(() -> RenderType::cutout)
            .blockstate(GCYRModels::crossModel)
            .item()
            .tag(Tags.Items.MUSHROOMS)
            .model(GCYRModels::blockTextureGeneratedModel)
            .build()
            .register();

    // endregion

    // region rocket stuff

    // spotless:off
    public static final Map<IRocketMotorType, Supplier<RocketMotorBlock>> ALL_ROCKET_MOTORS = new IdentityHashMap<>();

    public static final BlockEntry<RocketMotorBlock> BASIC_ROCKET_MOTOR = createRocketMotor(RocketMotorBlock.RocketMotorType.BASIC);
    public static final BlockEntry<RocketMotorBlock> ADVANCED_ROCKET_MOTOR = createRocketMotor(RocketMotorBlock.RocketMotorType.ADVANCED);
    public static final BlockEntry<RocketMotorBlock> ELITE_ROCKET_MOTOR = createRocketMotor(RocketMotorBlock.RocketMotorType.ELITE);
    // spotless:on

    public static final BlockEntry<DoorBlock> AIRLOCK_DOOR = REGISTRATE
            .block("airlock_door", properties -> new DoorBlock(properties, BlockSetType.IRON))
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Airlock Door")
            .properties(p -> p.strength(4.0F, 6.0F))
            .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH,
                    GCYRTags.Blocks.BLOCKS_FLOOD_FILL, BlockTags.DOORS)
            .blockstate(GCYRModels::airlockDoorModel)
            .loot((table, block) -> table.add(block, table.createDoorTable(block)))
            .item()
            .tag(ItemTags.DOORS)
            .defaultModel()
            .build()
            .register();

    public static final BlockEntry<Block> LAUNCH_PAD = REGISTRATE
            .block("launch_pad", Block::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Launch Pad")
            .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
            .simpleItem()
            .register();

    public static final BlockEntry<CarpetBlock> SEAT = REGISTRATE
            .block("seat", CarpetBlock::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Seat")
            .blockstate(GCYRModels::seatModel)
            .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
            .simpleItem()
            .register();

    // spotless:off
    public static final Map<IFuelTankProperties, Supplier<FuelTankBlock>> ALL_FUEL_TANKS = new IdentityHashMap<>();

    public static final BlockEntry<FuelTankBlock> BASIC_FUEL_TANK = createFuelTank(FuelTankBlock.FuelTankProperties.BASIC);
    public static final BlockEntry<FuelTankBlock> ADVANCED_FUEL_TANK = createFuelTank(FuelTankBlock.FuelTankProperties.ADVANCED);
    public static final BlockEntry<FuelTankBlock> ELITE_FUEL_TANK = createFuelTank(FuelTankBlock.FuelTankProperties.ELITE);
    // spotless:on

    // endregion

    private static BlockEntry<Block> createCasingBlock(String name, ResourceLocation texture) {
        return createCasingBlock(name, Block::new, texture, () -> Blocks.IRON_BLOCK, () -> RenderType::cutoutMipped);
    }

    private static BlockEntry<Block> createGlassCasingBlock(String name, ResourceLocation texture,
                                                            Supplier<Supplier<RenderType>> type) {
        return createCasingBlock(name, GlassBlock::new, texture, () -> Blocks.GLASS, type);
    }

    private static BlockEntry<Block> createCasingBlock(String name,
                                                       Function<BlockBehaviour.Properties, ? extends Block> blockSupplier,
                                                       ResourceLocation texture,
                                                       NonNullSupplier<? extends Block> properties,
                                                       Supplier<Supplier<RenderType>> type) {
        return REGISTRATE.block(name, p -> (Block) blockSupplier.apply(p))
                .initialProperties(properties)
                .addLayer(type)
                .blockstate(GCYRModels.cubeAllModel(name, texture))
                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .simpleItem()
                .register();
    }

    private static BlockEntry<FuelTankBlock> createFuelTank(IFuelTankProperties properties) {
        BlockEntry<FuelTankBlock> block = REGISTRATE
                .block("%s_fuel_tank".formatted(properties.getSerializedName()),
                        (p) -> new FuelTankBlock(p, properties))
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .lang("%s Fuel Tank".formatted(FormattingUtil.toEnglishName(properties.getSerializedName())))
                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .blockstate((ctx, prov) -> prov.axisBlock(ctx.getEntry()))
                .simpleItem()
                .register();
        ALL_FUEL_TANKS.put(properties, block);
        return block;
    }

    private static BlockEntry<RocketMotorBlock> createRocketMotor(IRocketMotorType type) {
        BlockEntry<RocketMotorBlock> block = REGISTRATE
                .block("%s_rocket_motor".formatted(type.getSerializedName()), (p) -> new RocketMotorBlock(p, type))
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .lang("%s Rocket Motor".formatted(FormattingUtil.toEnglishName(type.getSerializedName())))
                .blockstate((ctx, prov) -> GCYRModels.rocketMotorModel(ctx, prov, type))
                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .simpleItem()
                .register();
        ALL_ROCKET_MOTORS.put(type, block);
        return block;
    }

    public static void init() {}
}
