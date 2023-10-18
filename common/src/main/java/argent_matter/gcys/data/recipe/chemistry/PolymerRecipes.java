package argent_matter.gcys.data.recipe.chemistry;

import argent_matter.gcys.GregicalitySpace;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static argent_matter.gcys.common.data.GcysMaterials.*;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;

public class PolymerRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        polyOxydiphenylenePyromellitimideProcess(provider);
    }

    public static void polyOxydiphenylenePyromellitimideProcess(Consumer<FinishedRecipe> provider) {
        // Kapton K
        CHEMICAL_RECIPES.recipeBuilder(GregicalitySpace.id("aminophenol"))
                .inputFluids(Phenol.getFluid(1000))
                .inputFluids(NitrationMixture.getFluid(1000))
                .notConsumable(dust, Iron)
                .outputFluids(AminoPhenol.getFluid(1000))
                .outputFluids(DilutedSulfuricAcid.getFluid(1000))
                .duration(300).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GregicalitySpace.id("chloronitrobenzene"))
                .inputFluids(Chlorobenzene.getFluid(1000))
                .inputFluids(NitricAcid.getFluid(1000))
                .outputFluids(ChloroNitrobenzene.getFluid(1000))
                .outputFluids(Water.getFluid(1000))
                .duration(400).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GregicalitySpace.id("dimethylformamide"))
                .inputFluids(CarbonMonoxide.getFluid(1000))
                .inputFluids(Dimethylamine.getFluid(1000))
                .outputFluids(Dimethylformamide.getFluid(1000))
                .duration(200).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GregicalitySpace.id("pyrometillic_dianhydride"))
                .inputFluids(Durene.getFluid(250))
                .inputFluids(Oxygen.getFluid(1500))
                .outputFluids(PyromelliticDianhydride.getFluid(250))
                .outputFluids(Water.getFluid(1500))
                .duration(400).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GregicalitySpace.id("potassium_hydroxide"))
                .inputFluids(Durene.getFluid(250))
                .inputFluids(Oxygen.getFluid(1500))
                .outputItems(dust, PyromelliticDianhydride)
                .outputFluids(Water.getFluid(1500))
                .duration(400).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GregicalitySpace.id("oxydianiline_sludge"))
                .inputFluids(AminoPhenol.getFluid(1000))
                .inputFluids(ChloroNitrobenzene.getFluid(1000))
                .inputFluids(Dimethylformamide.getFluid(1000))
                .inputItems(dust, PotassiumCarbonate)
                .outputFluids(OxydianilineSludge.getFluid(250))
                .outputFluids(Water.getFluid(1500))
                .duration(400).EUt(VA[HV]).save(provider);

        DISTILLATION_RECIPES.recipeBuilder(GregicalitySpace.id("distill_oxydianiline_sludge"))
                .inputFluids(OxydianilineSludge.getFluid(1000))
                .outputFluids(Dimethylformamide.getFluid(1000))
                .outputItems(dust, Oxydianiline, 1)
                .duration(200).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GregicalitySpace.id("kapton_k"))
                .inputFluids(PyromelliticDianhydride.getFluid(1000))
                .inputFluids(Oxydianiline.getFluid(1000))
                .outputFluids(KaptonK.getFluid(1000))
                .duration(400).EUt(VA[HV]).save(provider);

        // Kapton E coming soon:tm:
        /*
         * ortho-Xylene, 3 Oxygen, Palladium (Cata (consumed)) -> biphenyltetracarboxylic acid dianhydride (BPDA) (C16H6O6), 3 Water (heater), Hydrogen (skips a step: Phthalic Anhydride (C6H4(CO)2O))
         * */
    }
}
