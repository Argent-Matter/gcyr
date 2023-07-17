package argent_matter.gcys.common.data;

import argent_matter.gcys.common.machine.electric.OxygenSpreaderMachine;
import argent_matter.gcys.common.machine.multiblock.electric.SpaceShuttleMachine;
import argent_matter.gcys.data.recipe.GcysTags;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.client.renderer.machine.TieredHullMachineRenderer;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import java.util.function.BiFunction;

import static argent_matter.gcys.api.registries.GcysRegistries.REGISTRATE;
import static argent_matter.gcys.common.data.GcysBlocks.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.*;

public class GcysMachines {

    public final static MachineDefinition[] OXYGEN_SPREADER = registerTieredMachines("oxygen_spreader", OxygenSpreaderMachine::new,
            (tier, builder) -> builder
                    .rotationState(RotationState.NON_Y_AXIS)
                    .hasTESR(true)
                    .renderer(() -> new TieredHullMachineRenderer(tier, GTCEu.id("block/machine/oxygen_spreader_machine")))
                    .recipeType(GcysRecipeTypes.OXYGEN_SPREADER_RECIPES)
                    .tooltips(workableTiered(tier, GTValues.V[tier], GTValues.V[tier] * 64, GcysRecipeTypes.OXYGEN_SPREADER_RECIPES, OxygenSpreaderMachine.tankScalingFunction(tier), true))
                    .blockBuilder(block -> block.tag(GcysTags.PASSES_FLOOD_FILL))
                    .register(),
            HIGH_TIERS);

    public static final MultiblockMachineDefinition SPACE_SHUTTLE = GTRegistries.REGISTRATE.multiblock("space_shuttle", SpaceShuttleMachine::new)
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
                    .where('S', Predicates.controller(blocks(definition.getBlock())))
                    .where(' ', Predicates.air())
                    .where('C', blocks(CASING_TUNGSTENSTEEL_ROBUST.get()).setMinGlobalLimited(110)
                            .or(Predicates.autoAbilities(definition.getRecipeType())))
                    .where('X', blocks(MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.StainlessSteel).get()))
                    .where('T', blocks(CASING_ALUMINIUM_AEROSPACE.get()))
                    .where('A', blocks(CASING_ROCKET_MOTOR.get()))
                    .where('B', blocks(MATERIAL_BLOCKS.get(TagPrefix.block, GcysMaterials.PolyOxydiphenylenePyromellitimide).get()))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"),
                    GTCEu.id("block/multiblock/assembly_line"), false)
            .register();

    public static final MultiblockMachineDefinition DRONE_HANGAR = GTRegistries.REGISTRATE.multiblock("drone_hangar", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            //.recipeType(GcysRecipeTypes.VACUUM_RECIPES)
            .appearanceBlock(CASING_ALUMINIUM_FROSTPROOF)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXX", "XXX", "XXX")
                    .aisle("XXX", "X#X", "XXX")
                    .aisle("XXX", "XSX", "XXX")
                    .where('S', Predicates.controller(blocks(definition.getBlock())))
                    .where('X', blocks(CASING_ALUMINIUM_FROSTPROOF.get()).setMinGlobalLimited(14)
                            .or(Predicates.autoAbilities(definition.getRecipeType())))
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
