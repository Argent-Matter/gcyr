package argent_matter.gcyr.data.recipe;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.data.GCyRBlocks;
import argent_matter.gcyr.common.data.GCyRItems;
import argent_matter.gcyr.common.data.GCyRMachines;
import argent_matter.gcyr.common.data.GCyRMaterials;
import argent_matter.gcyr.data.recipe.builder.SmithingSpaceSuitRecipeBuilder;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.gregtechceu.gtceu.data.recipe.misc.MetaTileEntityLoader;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import static argent_matter.gcyr.common.data.GCyRMaterials.*;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.*;

public class MiscRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, true, GCyR.id("casing_atomic"),
                GCyMBlocks.CASING_ATOMIC.asStack(2),
                "PhP", "PFP", "PwP",
                'P', new UnificationEntry(TagPrefix.plateDouble, GCyRMaterials.Trinaquadalloy),
                'F', new UnificationEntry(TagPrefix.frameGt, GTMaterials.NaquadahAlloy));

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(GCyR.id("casing_atomic"))
                .inputItems(TagPrefix.plateDouble, GCyRMaterials.Trinaquadalloy, 6)
                .inputItems(TagPrefix.frameGt, GTMaterials.NaquadahAlloy)
                .circuitMeta(6)
                .outputItems(GCyMBlocks.CASING_ATOMIC.asStack(2))
                .duration(50).EUt(16).save(provider);

        VanillaRecipeHelper.addShapelessRecipe(provider, GCyR.id("id_chip"), GCyRItems.ID_CHIP.asStack(),
                new UnificationEntry(foil, IronMagnetic),
                new UnificationEntry(plate, Polyethylene)
        );

        VanillaRecipeHelper.addShapedRecipe(provider, GCyR.id("photovoltaic_cell"), GCyRItems.PHOTOVOLTAIC_CELL.asStack(),
                "WVW", "VGV", "CVC",
                'W', GTItems.NAQUADAH_WAFER.asStack(), 'G', GTBlocks.CASING_TEMPERED_GLASS.asStack(), 'C', CustomTags.LuV_CIRCUITS, 'V', new UnificationEntry(wireFine, Gold)
        );

        GTRecipeTypes.CHEMICAL_BATH_RECIPES.recipeBuilder(GCyR.id("fiberglass"))
                .inputItems(dust, SiliconDioxide, 2)
                .inputFluids(Epoxy.getFluid(250))
                .outputFluids(FiberGlass.getFluid(250))
                .duration(200).EUt(VA[EV]).save(provider);

        //region Spacesuit

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(GCyR.id("space_fabric"))
                .inputItems(foil, Polytetrafluoroethylene, 4)
                .inputItems(foil, ParaAramid, 4)
                .inputItems(foil, Polybenzimidazole, 4)
                .inputItems(dust, FiberGlass, 4)
                .outputItems(GCyRItems.SPACE_FABRIC.asStack(1))
                .duration(100).EUt(VA[HV]).save(provider);

        VanillaRecipeHelper.addShapedRecipe(provider, GCyR.id("space_helmet"), GCyRItems.SPACE_SUIT_HELMET.asStack(),
                "SFS", "FFF", "SCS",
                'S', GCyRItems.SPACE_FABRIC.asStack(), 'F', new UnificationEntry(foil, Gold), 'C', CustomTags.EV_CIRCUITS
        );
        VanillaRecipeHelper.addShapedRecipe(provider, GCyR.id("space_chest"), GCyRItems.SPACE_SUIT_CHEST.asStack(),
                "STS", "SCS", "SCS",
                'S', GCyRItems.SPACE_FABRIC.asStack(), 'T', GTItems.FLUID_CELL_LARGE_TUNGSTEN_STEEL.asStack(), 'C', CustomTags.EV_CIRCUITS
        );
        VanillaRecipeHelper.addShapedRecipe(provider, GCyR.id("space_legs"), GCyRItems.SPACE_SUIT_LEGS.asStack(),
                "SCS", "S S", "S S",
                'S', GCyRItems.SPACE_FABRIC.asStack(), 'C', CustomTags.EV_CIRCUITS
        );
        VanillaRecipeHelper.addShapedRecipe(provider, GCyR.id("space_boots"), GCyRItems.SPACE_SUIT_BOOTS.asStack(),
                "S S", "S S",
                'S', GCyRItems.SPACE_FABRIC.asStack()
        );

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(GCyR.id("space_upgrade_smithing_template"))
                .inputItems(GCyRItems.SPACE_FABRIC.asStack(5))
                .inputItems(plate, Titanium, 2)
                .inputItems(foil, Polybenzimidazole, 4)
                .outputItems(GCyRItems.SPACE_UPGRADE_SMITHING_TEMPLATE.asStack(1))
                .EUt(VA[IV]).duration(50)
                .save(provider);

        SmithingSpaceSuitRecipeBuilder.smithingSpacesuit(Ingredient.of(GCyRItems.SPACE_UPGRADE_SMITHING_TEMPLATE.get()),
                        Ingredient.of(Tags.Items.ARMORS_BOOTS),
                        Ingredient.of(GCyRItems.SPACE_SUIT_BOOTS.get()))
                .save(provider, GCyR.id("space_suit_boots_smithing_trim"));
        SmithingSpaceSuitRecipeBuilder.smithingSpacesuit(Ingredient.of(GCyRItems.SPACE_UPGRADE_SMITHING_TEMPLATE.get()),
                        Ingredient.of(Tags.Items.ARMORS_LEGGINGS),
                        Ingredient.of(GCyRItems.SPACE_SUIT_LEGS.get()))
                .save(provider, GCyR.id("space_suit_leggings_smithing_trim"));
        SmithingSpaceSuitRecipeBuilder.smithingSpacesuit(Ingredient.of(GCyRItems.SPACE_UPGRADE_SMITHING_TEMPLATE.get()),
                        Ingredient.of(Tags.Items.ARMORS_CHESTPLATES),
                        Ingredient.of(GCyRItems.SPACE_SUIT_CHEST.get()))
                .save(provider, GCyR.id("space_suit_chestplate_smithing_trim"));
        SmithingSpaceSuitRecipeBuilder.smithingSpacesuit(Ingredient.of(GCyRItems.SPACE_UPGRADE_SMITHING_TEMPLATE.get()),
                        Ingredient.of(Tags.Items.ARMORS_HELMETS),
                        Ingredient.of(GCyRItems.SPACE_SUIT_HELMET.get()))
                .save(provider, GCyR.id("space_suit_helmet_smithing_trim"));

        //endregion

        //region machines

        MetaTileEntityLoader.registerMachineRecipe(provider, GCyRMachines.OXYGEN_SPREADER, "PCP", "FHF", "PCP", 'H', HULL, 'P', PUMP, 'F', GTItems.FLUID_FILTER, 'C', CIRCUIT);
        VanillaRecipeHelper.addShapedRecipe(provider, true, GCyR.id("evaporation_plant"), GCyRMachines.EVAPORATION_PLANT.asStack(), "CBC", "FMF", "CBC", 'M', GTMachines.HULL[HV].asStack(), 'B', new UnificationEntry(wireGtDouble, Kanthal), 'C', CustomTags.HV_CIRCUITS, 'F', GTItems.ELECTRIC_PUMP_HV);
        VanillaRecipeHelper.addShapedRecipe(provider, GCyR.id("rocket_scanner"), GCyRMachines.ROCKET_SCANNER.asStack(), "PCP", "CHC", "SCS", 'H', GTMachines.HULL[EV].asStack(), 'P', GCyRBlocks.LAUNCH_PAD.asStack(), 'C', CustomTags.EV_CIRCUITS, 'S', new UnificationEntry(plate, Titanium));
        VanillaRecipeHelper.addShapedRecipe(provider, GCyR.id("space_station_packager"), GCyRMachines.SPACE_STATION_PACKAGER.asStack(), "PCP", "FHF", "PCP", 'H', GTMachines.HULL[LuV].asStack(), 'P', new UnificationEntry(frameGt, StainlessSteel), 'C', CustomTags.LuV_CIRCUITS, 'F', GTBlocks.PLASTCRETE.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, GCyR.id("dyson_system_controller"), GCyRMachines.DYSON_SYSTEM_CONTROLLER.asStack(), "PCP", "FHF", "PCP", 'H', GTMachines.HULL[UHV].asStack(), 'P', new UnificationEntry(plate, Darmstadtium), 'C', CustomTags.UHV_CIRCUITS, 'F', GCyMBlocks.CASING_ATOMIC.asStack());
        VanillaRecipeHelper.addShapedRecipe(provider, GCyR.id("space_elevator"), GCyRMachines.SPACE_ELEVATOR.asStack(), "FFF", "PHP", "PCP", 'H', GTCEuAPI.isHighTier() ? GTMachines.HULL[UEV].asStack() : GTMachines.HULL[UHV].asStack(), 'F', new UnificationEntry(plateDense, Trinaquadalloy), 'C', CustomTags.UHV_CIRCUITS, 'P', GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.asStack());

        //endregion

        //region rocket parts

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(GCyR.id("basic_fuel_tank"))
                .inputItems(GTMachines.TITANIUM_DRUM, 2)
                .inputItems(plate, KaptonK, 6)
                .inputItems(frameGt, Titanium)
                .outputItems(GCyRBlocks.BASIC_FUEL_TANK.asStack(1))
                .EUt(VA[EV]).duration(300)
                .save(provider);

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(GCyR.id("basic_rocket_motor"))
                .inputItems(GTItems.POWER_THRUSTER_ADVANCED, 4)
                .inputItems(plate, KaptonK, 6)
                .inputItems(frameGt, Titanium)
                .outputItems(GCyRBlocks.BASIC_ROCKET_MOTOR.asStack(1))
                .EUt(VA[EV]).duration(300)
                .save(provider);

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(GCyR.id("launch_pad"))
                .inputItems(Items.BLACK_CONCRETE, 1)
                .inputItems(frameGt, Steel, 1)
                .inputFluids(DyeYellow.getFluid(200))
                .outputItems(GCyRBlocks.LAUNCH_PAD.asStack(2))
                .EUt(VA[HV]).duration(40)
                .save(provider);

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(GCyR.id("seat"))
                .inputItems(Items.WHITE_CARPET, 1)
                .inputItems(rod, Steel, 6)
                .inputItems(plate, Steel, 2)
                .outputItems(GCyRBlocks.SEAT.asStack(1))
                .EUt(VA[MV]).duration(50)
                .save(provider);

        //endregion

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(GCyR.id("stainless_evaporation_casing"))
                .inputItems(GTBlocks.CASING_STAINLESS_CLEAN.asStack(1))
                .inputItems(wireGtDouble, AnnealedCopper, 4)
                .inputFluids(PolyvinylChloride.getFluid(L * 2))
                .outputItems(GCyRBlocks.CASING_STAINLESS_EVAPORATION.asStack(1))
                .duration(30).EUt(VA[HV]).save(provider);
    }

}
