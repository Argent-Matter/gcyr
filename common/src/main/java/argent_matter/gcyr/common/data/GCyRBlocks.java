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
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
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
public class GCyRBlocks {

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

    // endregion

    // region mercury
    public static final BlockEntry<Block> MERCURY_ROCK = REGISTRATE
            .block("mercury_rock", Block::new)
            .initialProperties(() -> Blocks.STONE)
            .properties(properties -> properties.mapColor(MapColor.COLOR_GRAY))
            .blockstate(GCyRModels::randomRotatedModel)
            .simpleItem()
            .register();
    //endregion

    //region venus
    public static final BlockEntry<FallingBlock> VENUS_SAND = REGISTRATE
            .block("venus_sand", FallingBlock::new)
            .initialProperties(() -> Blocks.SAND)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_ORANGE))
            .blockstate(GCyRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> VENUSIAN_REGOLITH = REGISTRATE
            .block("venusian_regolith", Block::new)
            .initialProperties(() -> Blocks.SANDSTONE)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_ORANGE))
            .blockstate(GCyRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> VENUS_ROCK = REGISTRATE
            .block("venus_rock", Block::new)
            .initialProperties(() -> Blocks.STONE)
            .properties(properties -> properties.mapColor(MapColor.TERRACOTTA_ORANGE))
            .blockstate(GCyRModels::randomRotatedModel)
            .simpleItem()
            .register();
    //endregion


    // region moon
    public static final BlockEntry<Block> MOON_STONE = REGISTRATE
            .block("moon_stone", Block::new)
            .initialProperties(() -> Blocks.STONE)
            .blockstate(GCyRModels::randomRotatedModel)
            .simpleItem()
            .register();

    public static final BlockEntry<FallingBlock> MOON_SAND = REGISTRATE
            .block("moon_sand", FallingBlock::new)
            .initialProperties(() -> Blocks.GRAVEL)
            .blockstate(GCyRModels::randomRotatedModel)
            .simpleItem()
            .register();
    //endregion

    // region mars
    public static final BlockEntry<FallingBlock> MARS_REGOLITH = REGISTRATE
            .block("mars_regolith", FallingBlock::new)
            .initialProperties(()  -> Blocks.RED_SAND)
            .simpleItem()
            .register();

    public static final BlockEntry<Block> MARTIAN_ROCK = REGISTRATE
            .block("martian_rock", Block::new)
            .initialProperties(() -> Blocks.STONE)
            .simpleItem()
            .register();
    // endregion


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
            .tag(GTToolType.WRENCH.harvestTag, BlockTags.MINEABLE_WITH_PICKAXE, GCyRTags.BLOCKS_FLOOD_FILL)
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
            .tag(GTToolType.WRENCH.harvestTag, BlockTags.MINEABLE_WITH_PICKAXE)
            .simpleItem()
            .register();

    public static final BlockEntry<CarpetBlock> SEAT = REGISTRATE
            .block("seat", CarpetBlock::new)
            .initialProperties(() -> Blocks.IRON_BLOCK)
            .lang("Seat")
            .blockstate(GCyRModels::seatModel)
            .tag(GTToolType.WRENCH.harvestTag, BlockTags.MINEABLE_WITH_PICKAXE)
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

    private static BlockEntry<FuelTankBlock> createFuelTank(IFuelTankProperties properties) {
        BlockEntry<FuelTankBlock> block = REGISTRATE.block("%s_fuel_tank".formatted(properties.getSerializedName()), (p) -> new FuelTankBlock(p, properties))
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .lang("%s Fuel Tank".formatted(FormattingUtil.toEnglishName(properties.getSerializedName())))
                .blockstate(GCyRModels::fuelTankModel)
                .tag(GTToolType.WRENCH.harvestTag, BlockTags.MINEABLE_WITH_PICKAXE)
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
                .tag(GTToolType.WRENCH.harvestTag, BlockTags.MINEABLE_WITH_PICKAXE)
                .simpleItem()
                .register();
        ALL_ROCKET_MOTORS.put(properties, block);
        return block;
    }

    public static void init() {

    }
}
