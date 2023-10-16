package argent_matter.gcys.common.data;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.common.machine.electric.OxygenSpreaderMachine;
import argent_matter.gcys.common.machine.multiblock.electric.DysonSystemControllerMachine;
import argent_matter.gcys.common.machine.multiblock.electric.RocketScannerMachine;
import argent_matter.gcys.common.machine.multiblock.electric.SpaceShuttleMachine;
import argent_matter.gcys.data.recipe.GcysTags;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.client.renderer.machine.TieredHullMachineRenderer;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.function.BiFunction;

import static argent_matter.gcys.api.registries.GcysRegistries.REGISTRATE;
import static argent_matter.gcys.common.data.GcysBlocks.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gregtechceu.gtceu.common.data.GCyMBlocks.CASING_ATOMIC;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.*;

public class GcysMachines {

    public final static MachineDefinition[] OXYGEN_SPREADER = registerTieredMachines("oxygen_spreader", OxygenSpreaderMachine::new,
            (tier, builder) -> builder
                    .rotationState(RotationState.NON_Y_AXIS)
                    .hasTESR(true)
                    .renderer(() -> new TieredHullMachineRenderer(tier, GregicalitySpace.id("block/machine/oxygen_spreader_machine")))
                    .recipeType(GcysRecipeTypes.OXYGEN_SPREADER_RECIPES)
                    .tooltips(workableTiered(tier, GTValues.V[tier], GTValues.V[tier] * 64, GcysRecipeTypes.OXYGEN_SPREADER_RECIPES, OxygenSpreaderMachine.tankScalingFunction(tier), true))
                    .blockBuilder(block -> block.tag(GcysTags.PASSES_FLOOD_FILL))
                    .register(),
            HIGH_TIERS);

    public static final MachineDefinition ROCKET_SCANNER = REGISTRATE.multiblock("rocket_scanner", RocketScannerMachine::new)
            .langValue("Rocket Scanner")
            .rotationState(RotationState.NON_Y_AXIS)
            .tier(GTValues.EV)
            .pattern((definition) -> FactoryBlockPattern.start()
                    .aisle("     ", "  K  ", "  K  ", "  K  ", "  K  ", "  K  ")
                    .aisle(" BBB ", "     ", "     ", "     ", "     ", "     ")
                    .aisle(" BBB ", "     ", "     ", "     ", "     ", "     ")
                    .aisle(" BBB ", "     ", "     ", "     ", "     ", "     ")
                    .aisle("     ", "  S  ", "     ", "     ", "     ", "     ")
                    .where('S', controller(blocks(definition.getBlock())))
                    .where('B', blocks(LAUNCH_PAD.get()))
                    .where('K', blocks(GTBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.StainlessSteel).get()))
                    .where(' ', any())
                    .build()
            )
            .shapeInfos(definition -> {
                ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
                MultiblockShapeInfo.ShapeInfoBuilder builder = MultiblockShapeInfo.builder()
                        .aisle("     ", "  S  ", "     ", "     ", "     ", "     ")
                        .aisle(" BBB ", "     ", "     ", "     ", "     ", "     ")
                        .aisle(" BBB ", " EEE ", " TTT ", "  C  ", "     ", "     ")
                        .aisle(" BBB ", "     ", "     ", "     ", "     ", "     ")
                        .aisle("     ", "  K  ", "  K  ", "  K  ", "  K  ", "  K  ")
                        .where('S', definition, Direction.NORTH)
                        .where(' ', Blocks.AIR)
                        .where('K', GTBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.StainlessSteel).get())
                        .where('B', LAUNCH_PAD);
                shapeInfo.add(builder
                        .where('E', Blocks.AIR)
                        .where('T', Blocks.AIR)
                        .where('C', Blocks.AIR).build());
                shapeInfo.add(builder
                        .where('E', ROCKET_MOTOR)
                        .where('T', FUEL_TANK)
                        .where('C', SEAT).build());
                return shapeInfo;
            })
            .workableCasingRenderer(GTCEu.id("block/casings/voltage/ev/side"),
                    GTCEu.id("block/multiblock/assembly_line"), false)
            .register();

    public static final MultiblockMachineDefinition DYSON_SYSTEM_CONTROLLER = REGISTRATE.multiblock("dyson_system_controller", DysonSystemControllerMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .appearanceBlock(CASING_ATOMIC)
            .recipeType(GcysRecipeTypes.DYSON_ENERGY_RECIPES)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("CCCCCCC", "CCCCCCC", "  F    ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ")
                    .aisle("CCCCCCC", "CCCCCCC", "  F    ", "  F    ", "  F    ", "  F    ", "  F    ", "  F    ", "       ", "  H    ", " HHH   ", "  H    ")
                    .aisle("CCCCCCC", "CCGCCCC", "FFGFF  ", " FGF   ", " FGF   ", " FGF   ", " FGF   ", " FGF   ", "  G    ", " HGH   ", " HGH   ", " HHH   ")
                    .aisle("CCCCCCC", "CCCCCCC", "  F    ", "  F    ", "  F  EE", "  F E  ", "  F E  ", "  F    ", "       ", "  H    ", " HHH   ", "  H    ")
                    .aisle("CCCCCCC", "CCCCCCC", "  F  X ", "     X ", "    XEE", "    ET ", "    E  ", "       ", "       ", "       ", "       ", "       ")
                    .aisle("CCCCCCC", "CCCCCCC", "       ", "       ", "     X ", "     EE", "     EE", "       ", "       ", "       ", "       ", "       ")
                    .aisle("CCCCCCC", "CCCSCCC", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ")
                    .where('S', controller(blocks(definition.getBlock())))
                    .where('C', blocks(CASING_ATOMIC.get()).setMinGlobalLimited(90).or(autoAbilities(definition.getRecipeTypes())))
                    .where('G', blocks(ChemicalHelper.getBlock(TagPrefix.wireGtHex, GTMaterials.RutheniumTriniumAmericiumNeutronate)))
                    .where('F', blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.HastelloyC276)))
                    .where('H', blocks(CASING_BEAM_RECEIVER.get()))
                    .where('X', blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.TungstenSteel)))
                    .where('E', blocks(ChemicalHelper.getBlock(TagPrefix.block, GTMaterials.HSSS)))
                    .where('T', blocks(POWER_TRANSFORMER[GTValues.UV].getBlock()))
                    .where(' ', any())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/gcym/atomic_casing"),
                    GTCEu.id("block/multiblock/assembly_line"), false)
            .register();

    public static final MultiblockMachineDefinition SPACE_SHUTTLE = REGISTRATE.multiblock("space_shuttle", SpaceShuttleMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .appearanceBlock(CASING_TUNGSTENSTEEL_ROBUST)
            .recipeType(GcysRecipeTypes.SPACE_SHUTTLE_RECIPES)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("CCCCCCCCC", "CCCCCCCCC", "   X X   ", "   XXX   ", "   X X   ", "   XXX   ", "   X X   ", "   XXX   ", "   X X   ", "   XXX   ", "   X X   ", "         ")
                    .aisle("CCCCCCCCC", "CCCCCCCCC", "   X X   ", "         ", "   X X   ", "         ", "   X X   ", "         ", "   X X   ", "         ", "   X X   ", "         ")
                    .aisle("CCCCCCCCC", "CCCCCCCCC", "   X X   ", "   XXX   ", "   X X   ", "   XXX   ", "   X X   ", "   XXX   ", "   X X   ", "   XXX   ", "   X X   ", "         ")
                    .aisle("CCCCCCCCC", "CCCCCCCCC", "         ", "    T    ", "         ", "         ", "         ", "         ", "   XXX   ", "         ", "         ", "         ")
                    .aisle("CCCCCCCCC", "CCCCCCCCC", "         ", "    T    ", "    T    ", "    T    ", "         ", "         ", "   XXX   ", "         ", "         ", "         ")
                    .aisle("CCCCCCCCC", "CCCCCCCCC", "    A    ", "   TTT   ", "   TTT   ", "   TTT   ", "   TTT   ", "   TTT   ", "   TTT   ", "   TTT   ", "    B    ", "         ")
                    .aisle("CCCCCCCCC", "         ", "   A A   ", " TTTTTTT ", " TTT TTT ", "  TT TT  ", "  TT TT  ", "   T T   ", "   T T   ", "   T T   ", "   B B   ", "    B    ")
                    .aisle("CCCCCCCCC", "CCCCCCCCC", "    A    ", "   TTT   ", "   TTT   ", "   TTT   ", "   TTT   ", "   TTT   ", "   TTT   ", "   TTT   ", "    B    ", "         ")
                    .aisle("CCCCCCCCC", "CCCCSCCCC", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ", "         ")
                    .where('S', controller(blocks(definition.getBlock())))
                    .where(' ', air())
                    .where('C', blocks(CASING_TUNGSTENSTEEL_ROBUST.get()).setMinGlobalLimited(110)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where('X', blocks(MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.StainlessSteel).get()))
                    .where('T', blocks(CASING_ALUMINIUM_AEROSPACE.get()))
                    .where('A', blocks(ROCKET_MOTOR.get()))
                    .where('B', blocks(MATERIAL_BLOCKS.get(TagPrefix.block, GcysMaterials.PolyOxydiphenylenePyromellitimide).get()))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"),
                    GTCEu.id("block/multiblock/assembly_line"), false)
            .register();

    public static final MultiblockMachineDefinition DRONE_HANGAR = REGISTRATE.multiblock("drone_hangar", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTRecipeTypes.VACUUM_RECIPES)
            .appearanceBlock(CASING_ALUMINIUM_FROSTPROOF)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXX", "XXX", "XXX")
                    .aisle("XXX", "X#X", "XXX")
                    .aisle("XXX", "XSX", "XXX")
                    .where('S', Predicates.controller(blocks(definition.getBlock())))
                    .where('X', blocks(CASING_ALUMINIUM_FROSTPROOF.get()).setMinGlobalLimited(14)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes())))
                    .where('#', Predicates.air())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_frost_proof"),
                    GTCEu.id("block/multiblock/vacuum_freezer"), false)
            .register();
    
    public static MachineDefinition[] registerTieredMachines(String name,
                                                             BiFunction<IMachineBlockEntity, Integer, MetaMachine> factory,
                                                             BiFunction<Integer, MachineBuilder<MachineDefinition>, MachineDefinition> builder,
                                                             int... tiers) {
        MachineDefinition[] definitions = new MachineDefinition[tiers.length];
        for (int i = 0; i < tiers.length; i++) {
            int tier = tiers[i];
            var register =  REGISTRATE.machine(GTValues.VN[tier].toLowerCase() + "_" + name, holder -> factory.apply(holder, tier))
                    .tier(tier);
            definitions[i] = builder.apply(tier, register);
        }
        return definitions;
    }

    public static void init() {

    }
}
