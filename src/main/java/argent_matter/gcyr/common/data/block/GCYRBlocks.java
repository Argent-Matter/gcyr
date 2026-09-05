package argent_matter.gcyr.common.data.block;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.api.block.IFuelTankProperties;
import argent_matter.gcyr.api.block.IRocketMotorType;
import argent_matter.gcyr.api.block.StoneVariant;
import argent_matter.gcyr.common.block.FuelTankBlock;
import argent_matter.gcyr.common.block.RocketMotorBlock;
import argent_matter.gcyr.common.data.item.GCYRCreativeModeTabs;
import argent_matter.gcyr.common.data.client.GCYRModels;
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

import java.util.HashMap;
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
    public static final BlockEntry<Block> CASING_ALUMINIUM_AEROSPACE = createCasingBlock("aerospace_aluminium_casing",
            GCYR.id("block/casings/solid/machine_casing_aerospace"));
    public static final BlockEntry<Block> CASING_BEAM_RECEIVER = createCasingBlock("beam_receiver",
            GCYR.id("block/casings/solid/beam_receiver"));
    public static final BlockEntry<Block> CASING_SUPPORT = createCasingBlock("space_elevator_support",
            GCYR.id("block/casings/solid/space_elevator_support"));

    public static final BlockEntry<Block> CASING_DYSON_SPHERE = createCasingBlock("dyson_sphere_casing",
            GCYR.id("block/casings/solid/dyson_sphere"));
    public static final BlockEntry<Block> CASING_DYSON_CELL = createCasingBlock("dyson_solar_cell",
            GCYR.id("block/casings/solid/dyson_solar_cell"));
    public static final BlockEntry<Block> CASING_DYSON_PORT = createCasingBlock("dyson_sphere_maintenance_port",
            GCYR.id("block/casings/solid/dyson_sphere_maintenance_port"));

    public static final BlockEntry<Block> CASING_STAINLESS_EVAPORATION = createCasingBlock(
            "stainless_evaporation_casing", GCYR.id("block/casings/solid/machine_casing_stainless_evaporation"));

    // region mercury

    private static final StoneVariant MERCURY_VARIANT = new StoneVariant(REGISTRATE, "mercury", MapColor.COLOR_GRAY);
    public static final BlockSetType MERCURY_SET = MERCURY_VARIANT.blockSetType();
    public static final BlockEntry<Block> MERCURY_COBBLESTONE = MERCURY_VARIANT.cobblestone("Cobbled Mercury Rock")
            .register();

    public static final BlockEntry<Block> MERCURY_ROCK = MERCURY_VARIANT.rock("rock", "Mercury Rock", MERCURY_COBBLESTONE)
            .register();

    public static final BlockEntry<SlabBlock> MERCURY_COBBLESTONE_SLAB = MERCURY_VARIANT
            .slab("cobblestone", "Cobbled Mercury Rock Slab", MERCURY_COBBLESTONE)
            .register();

    public static final BlockEntry<SlabBlock> MERCURY_ROCK_SLAB = MERCURY_VARIANT.slab("rock", "Mercury Rock Slab", MERCURY_ROCK)
            .register();

    public static final BlockEntry<StairBlock> MERCURY_COBBLESTONE_STAIRS = MERCURY_VARIANT
            .stairs("cobblestone", "Cobbled Mercury Rock Stairs", MERCURY_COBBLESTONE)
            .register();

    public static final BlockEntry<StairBlock> MERCURY_ROCK_STAIRS = MERCURY_VARIANT
            .stairs("rock", "Mercury Rock Stairs", MERCURY_ROCK)
            .register();

    public static final BlockEntry<ButtonBlock> MERCURY_ROCK_BUTTON = MERCURY_VARIANT
            .button("rock", "Mercury Rock Button", MERCURY_ROCK, MERCURY_SET)
            .register();

    // endregion

    // region venus

    private static final StoneVariant VENUS_VARIANT = new StoneVariant(REGISTRATE, "venus", MapColor.COLOR_GRAY);
    public static final BlockSetType VENUS_SET = VENUS_VARIANT.blockSetType();

    public static final BlockEntry<Block> VENUS_COBBLESTONE = VENUS_VARIANT.cobblestone("Cobbled Venus Rock")
            .register();

    public static final BlockEntry<Block> VENUS_ROCK = VENUS_VARIANT.rock("rock", "Venus Rock", VENUS_COBBLESTONE)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY))
            .register();

    public static final BlockEntry<SlabBlock> VENUS_COBBLESTONE_SLAB = VENUS_VARIANT
            .slab("cobblestone", "Cobbled Venus Rock Slab", VENUS_COBBLESTONE)
            .register();

    public static final BlockEntry<SlabBlock> VENUS_ROCK_SLAB = VENUS_VARIANT.slab("rock", "Venus Rock Slab", VENUS_ROCK)
            .register();

    public static final BlockEntry<StairBlock> VENUS_COBBLESTONE_STAIRS = VENUS_VARIANT
            .stairs("cobblestone", "Cobbled Venus Rock Stairs", VENUS_COBBLESTONE)
            .register();

    public static final BlockEntry<StairBlock> VENUS_ROCK_STAIRS = VENUS_VARIANT.stairs("rock", "Venus Rock Stairs", VENUS_ROCK)
            .register();

    public static final BlockEntry<ButtonBlock> VENUS_ROCK_BUTTON = VENUS_VARIANT
            .button("rock", "Venus Rock Button", VENUS_ROCK, VENUS_SET)
            .register();

    public static final BlockEntry<FallingBlock> VENUS_SAND = REGISTRATE
            .block("venus_sand", FallingBlock::new)
            .lang("Venus Sand")
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

    // region moon

    private static final StoneVariant LUNAR_VARIANT = new StoneVariant(REGISTRATE, "lunar", MapColor.COLOR_GRAY);
    public static final BlockSetType MOON_SET = LUNAR_VARIANT.blockSetType();

    public static final BlockEntry<Block> MOON_COBBLESTONE = LUNAR_VARIANT.cobblestone("Cobbled Lunar Stone")
            .register();

    public static final BlockEntry<Block> MOON_STONE = LUNAR_VARIANT.rock("stone", "Lunar Stone", MOON_COBBLESTONE)
            .properties(p -> p.mapColor(MapColor.COLOR_LIGHT_GRAY))
            .register();

    public static final BlockEntry<SlabBlock> MOON_COBBLESTONE_SLAB = LUNAR_VARIANT
            .slab("cobblestone", "Cobbled Lunar Stone Slab", MOON_COBBLESTONE)
            .register();

    public static final BlockEntry<SlabBlock> MOON_STONE_SLAB = LUNAR_VARIANT.slab("stone", "Lunar Stone Slab", MOON_STONE)
            .register();

    public static final BlockEntry<StairBlock> MOON_COBBLESTONE_STAIRS = LUNAR_VARIANT
            .stairs("cobblestone", "Cobbled Lunar Stone Stairs", MOON_COBBLESTONE)
            .register();

    public static final BlockEntry<StairBlock> MOON_STONE_STAIRS = LUNAR_VARIANT.stairs("stone", "Lunar Stone Stairs", MOON_STONE)
            .register();

    public static final BlockEntry<ButtonBlock> MOON_STONE_BUTTON = LUNAR_VARIANT
            .button("stone", "Lunar Stone Button", MOON_STONE, MOON_SET)
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

    public static final BlockEntry<FallingBlock> MOON_SAND = REGISTRATE
            .block("lunar_sand", FallingBlock::new)
            .lang("Lunar Sand")
            .initialProperties(() -> Blocks.SAND)
            .properties(properties -> properties.mapColor(MapColor.STONE))
            .tag(BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.SAND)
            .blockstate(GCYRModels::lunarSandModel)
            .simpleItem()
            .register();

    // region mars

    private static final StoneVariant MARS_VARIANT = new StoneVariant(REGISTRATE, "martian", MapColor.COLOR_GRAY);
    public static final BlockSetType MARS_SET = MARS_VARIANT.blockSetType();

    public static final BlockEntry<FallingBlock> MARS_REGOLITH = REGISTRATE
            .block("mars_regolith", FallingBlock::new)
            .lang("Martian Regolith")
            .initialProperties(() -> Blocks.GRAVEL)
            .properties(properties -> properties.mapColor(MapColor.COLOR_ORANGE))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> MARTIAN_COBBLESTONE = MARS_VARIANT.cobblestone("Cobbled Martian Rock")
            .register();

    public static final BlockEntry<Block> MARTIAN_ROCK = MARS_VARIANT.rock("rock", "Martian Rock", MARTIAN_COBBLESTONE)
            .properties(p -> p.mapColor(MapColor.COLOR_RED))
            .register();

    public static final BlockEntry<SlabBlock> MARTIAN_COBBLESTONE_SLAB = MARS_VARIANT
            .slab("cobblestone", "Cobbled Martian Rock Slab", MARTIAN_COBBLESTONE)
            .register();

    public static final BlockEntry<SlabBlock> MARTIAN_ROCK_SLAB = MARS_VARIANT.slab("rock", "Martian Rock Slab", MARTIAN_ROCK)
            .register();

    public static final BlockEntry<StairBlock> MARTIAN_COBBLESTONE_STAIRS = MARS_VARIANT
            .stairs("cobblestone", "Cobbled Martian Rock Stairs", MARTIAN_COBBLESTONE)
            .register();

    public static final BlockEntry<StairBlock> MARTIAN_ROCK_STAIRS = MARS_VARIANT
            .stairs("rock", "Martian Rock Stairs", MARTIAN_ROCK)
            .register();

    public static final BlockEntry<ButtonBlock> MARTIAN_ROCK_BUTTON = MARS_VARIANT
            .button("rock", "Martian Rock Button", MARTIAN_ROCK, MARS_SET)
            .register();

    public static final BlockEntry<MushroomBlock> PRB_SHROOM = REGISTRATE
            .block("prb_underground_mushroom", (p) -> new MushroomBlock(p, null /* todo fix */))
            .lang("Proxima b Underground Mushroom")
            .initialProperties(() -> Blocks.BROWN_MUSHROOM)
            .properties(p -> p.mapColor(MapColor.COLOR_LIGHT_GRAY).lightLevel((arg) -> 11))
            .addLayer(() -> RenderType::cutout)
            .blockstate(GCYRModels::crossModel)
            .item()
            .tag(Tags.Items.MUSHROOMS)
            .model(GCYRModels::blockTextureGeneratedModel)
            .build()
            .register();

    public static final BlockEntry<MushroomBlock> PRB_BULB = REGISTRATE
            .block("prb_underground_bulb", (p) -> new MushroomBlock(p, null))
            .lang("Proxima b Underground Bulb")
            .initialProperties(() -> Blocks.BROWN_MUSHROOM)
            .properties(p -> p.mapColor(MapColor.COLOR_LIGHT_GRAY).lightLevel(state -> 1))
            .addLayer(() -> RenderType::cutout)
            .blockstate(GCYRModels::crossModel)
            .item()
            .tag(Tags.Items.MUSHROOMS)
            .model(GCYRModels::blockTextureGeneratedModel)
            .build()
            .register();

    // region proxima centauri b

    public static final BlockEntry<FallingBlock> PROXIMA_CENTAURI_B_REGOLITH = REGISTRATE
            .block("proxima_centauri_b_regolith", FallingBlock::new)
            .lang("Proxima Centauri B Regolith")
            .initialProperties(() -> Blocks.GRAVEL)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_BLACK))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCYRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PROXIMA_CENTAURI_B_TURF = REGISTRATE
            .block("proxima_centauri_b_turf", Block::new)
            .lang("Proxima Centauri B Turf")
            .initialProperties(() -> Blocks.DIRT)
            .properties(properties -> properties.mapColor(MapColor.SAND))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCYRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PROXIMA_CENTAURI_B_HARDENED_TURF = REGISTRATE
            .block("proxima_centauri_b_hardened_turf", Block::new)
            .lang("Proxima Centauri B Hardened Turf")
            .initialProperties(() -> Blocks.DIRT)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_WHITE))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCYRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> PROXIMA_CENTAURI_B_GRASSY_TURF = REGISTRATE
            .block("proxima_centauri_b_grassy_turf", Block::new)
            .lang("Proxima Centauri B Grassy Turf")
            .initialProperties(() -> Blocks.DIRT)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_LIGHT_BLUE))
            .tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .blockstate(GCYRModels::randomRotatedModel)
            .simpleItem()
            .register();

    // region rocket stuff
    public static final Map<IRocketMotorType, Supplier<RocketMotorBlock>> ALL_ROCKET_MOTORS = new HashMap<>();
    public static final BlockEntry<RocketMotorBlock> BASIC_ROCKET_MOTOR = createRocketMotor(
            RocketMotorBlock.RocketMotorType.BASIC);
    public static final BlockEntry<RocketMotorBlock> ADVANCED_ROCKET_MOTOR = createRocketMotor(
            RocketMotorBlock.RocketMotorType.ADVANCED);
    public static final BlockEntry<RocketMotorBlock> ELITE_ROCKET_MOTOR = createRocketMotor(
            RocketMotorBlock.RocketMotorType.ELITE);

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

    public static final Map<IFuelTankProperties, Supplier<FuelTankBlock>> ALL_FUEL_TANKS = new HashMap<>();
    public static final BlockEntry<FuelTankBlock> BASIC_FUEL_TANK = createFuelTank(
            FuelTankBlock.FuelTankProperties.BASIC);
    public static final BlockEntry<FuelTankBlock> ADVANCED_FUEL_TANK = createFuelTank(
            FuelTankBlock.FuelTankProperties.ADVANCED);
    public static final BlockEntry<FuelTankBlock> ELITE_FUEL_TANK = createFuelTank(
            FuelTankBlock.FuelTankProperties.ELITE);

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
