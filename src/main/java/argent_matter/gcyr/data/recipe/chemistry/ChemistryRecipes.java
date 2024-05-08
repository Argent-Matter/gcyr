package argent_matter.gcyr.data.recipe.chemistry;

import argent_matter.gcyr.GCyR;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static argent_matter.gcyr.common.data.GCyRMaterials.*;
import static argent_matter.gcyr.common.data.GCyRRecipeTypes.*;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class ChemistryRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        bromineProcess(provider);
    }

    private static void bromineProcess(Consumer<FinishedRecipe> provider) {
        EVAPORATION_RECIPES.recipeBuilder(GCyR.id("brine_evaporation"))
                .inputFluids(SaltWater.getFluid(20000))
                .outputFluids(RawBrine.getFluid(1000))
                .duration(1000).EUt(VA[HV]).save(provider);
        FLUID_HEATER_RECIPES.recipeBuilder(GCyR.id("brine_heating"))
                .inputFluids(RawBrine.getFluid(1000))
                .outputFluids(HotBrine.getFluid(1000))
                .duration(12000).EUt(VA[HV]).save(provider);

        // Main chain
        CHEMICAL_RECIPES.recipeBuilder(GCyR.id("brine_chlorination"))
                .inputFluids(HotBrine.getFluid(1000))
                .inputFluids(Chlorine.getFluid(1000))
                .outputFluids(HotChlorinatedBrominatedBrine.getFluid(2000))
                .duration(100).EUt(VA[HV]).save(provider);
        CHEMICAL_RECIPES.recipeBuilder(GCyR.id("brine_filtration"))
                .inputFluids(HotChlorinatedBrominatedBrine.getFluid(1000))
                .inputFluids(Chlorine.getFluid(1000))
                .inputFluids(Steam.getFluid(1000))
                .outputFluids(HotAlkalineDebrominatedBrine.getFluid(1000))
                .outputFluids(BrominatedChlorineVapor.getFluid(2000))
                .duration(300).EUt(VA[HV]).save(provider);
        HEAT_EXCHANGER_RECIPES.recipeBuilder(GCyR.id("brominated_chlorine_vapor_condensation"))
                .inputFluids(BrominatedChlorineVapor.getFluid(1000))
                .inputFluids(Water.getFluid(1000))
                .outputFluids(AcidicBromineSolution.getFluid(1000))
                .outputFluids(Water.getFluid(1000))
                .duration(200).EUt(VA[HV]).save(provider);
        CHEMICAL_RECIPES.recipeBuilder(GCyR.id("bromine_vapor_concentration"))
                .inputFluids(AcidicBromineSolution.getFluid(1000))
                .inputFluids(Steam.getFluid(1000))
                .outputFluids(ConcentratedBromineSolution.getFluid(1000))
                .outputFluids(AcidicBromineExhaust.getFluid(1000))
                .duration(100).EUt(VA[HV]).save(provider);
        DISTILLATION_RECIPES.recipeBuilder(GCyR.id("bromine_distillation"))
                .inputFluids(ConcentratedBromineSolution.getFluid(1000))
                .outputFluids(Chlorine.getFluid(500))
                .outputFluids(Bromine.getFluid(1000))
                .duration(500).EUt(VA[HV]).save(provider);

        // byproduct loop
        CHEMICAL_RECIPES.recipeBuilder(GCyR.id("brine_neutralization"))
                .inputFluids(HotAlkalineDebrominatedBrine.getFluid(3000))
                .outputFluids(Chlorine.getFluid(1000))
                .outputFluids(HotDebrominatedBrine.getFluid(2000))
                .duration(100).EUt(VA[HV]).save(provider);
        HEAT_EXCHANGER_RECIPES.recipeBuilder(GCyR.id("debrominated_brine_raw_brine_mixing"))
                .inputFluids(RawBrine.getFluid(1000))
                .inputFluids(HotDebrominatedBrine.getFluid(1000))
                .outputFluids(HotBrine.getFluid(1000))
                .outputFluids(DebrominatedBrine.getFluid(1000))
                .duration(200).EUt(VA[HV]).save(provider);
        CHEMICAL_RECIPES.recipeBuilder(GCyR.id("acidic_bromine_exhaust_heating"))
                .inputFluids(AcidicBromineExhaust.getFluid(1000))
                .inputFluids(HotBrine.getFluid(1000))
                .outputFluids(HotChlorinatedBrominatedBrine.getFluid(1000))
                .outputFluids(Steam.getFluid(3000))
                .duration(100).EUt(VA[HV]).save(provider);
    }

}
