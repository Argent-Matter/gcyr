package argent_matter.gcys.data.recipe.chemistry;

import argent_matter.gcys.GCyS;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static argent_matter.gcys.common.data.GCySMaterials.*;
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
        CHEMICAL_RECIPES.recipeBuilder(GCyS.id("aminophenol"))
                .inputFluids(Phenol.getFluid(1000))
                .inputFluids(NitrationMixture.getFluid(1000))
                .notConsumable(dust, Iron)
                .outputFluids(AminoPhenol.getFluid(1000))
                .outputFluids(DilutedSulfuricAcid.getFluid(1000))
                .duration(300).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCyS.id("chloronitrobenzene"))
                .inputFluids(Chlorobenzene.getFluid(1000))
                .inputFluids(NitricAcid.getFluid(1000))
                .outputFluids(ChloroNitrobenzene.getFluid(1000))
                .outputFluids(Water.getFluid(1000))
                .duration(400).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCyS.id("dimethylformamide"))
                .inputFluids(CarbonMonoxide.getFluid(1000))
                .inputFluids(Dimethylamine.getFluid(1000))
                .outputFluids(Dimethylformamide.getFluid(1000))
                .duration(200).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCyS.id("pyrometillic_dianhydride"))
                .inputFluids(Durene.getFluid(250))
                .inputFluids(Oxygen.getFluid(1500))
                .outputFluids(PyromelliticDianhydride.getFluid(250))
                .outputFluids(Water.getFluid(1500))
                .duration(400).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCyS.id("potassium_hydroxide"))
                .inputFluids(Durene.getFluid(250))
                .inputFluids(Oxygen.getFluid(1500))
                .outputFluids(PyromelliticDianhydride.getFluid(250))
                .outputFluids(Water.getFluid(1500))
                .duration(400).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCyS.id("oxydianiline_sludge"))
                .inputFluids(AminoPhenol.getFluid(1000))
                .inputFluids(ChloroNitrobenzene.getFluid(1000))
                .inputFluids(Dimethylformamide.getFluid(1000))
                .inputItems(dust, PotassiumCarbonate)
                .outputFluids(OxydianilineSludge.getFluid(250))
                .outputFluids(Water.getFluid(1500))
                .duration(400).EUt(VA[HV]).save(provider);

        DISTILLATION_RECIPES.recipeBuilder(GCyS.id("distill_oxydianiline_sludge"))
                .inputFluids(OxydianilineSludge.getFluid(1000))
                .outputFluids(Dimethylformamide.getFluid(1000))
                .outputFluids(Oxydianiline.getFluid(144))
                .duration(200).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCyS.id("kapton_k"))
                .inputFluids(PyromelliticDianhydride.getFluid(1000))
                .inputFluids(Oxydianiline.getFluid(1000))
                .outputFluids(KaptonK.getFluid(1000))
                .duration(400).EUt(VA[HV]).save(provider);

        // Kapton E coming soon:tm:
        /*
         * ortho-Xylene, 3 Oxygen, Palladium (Cata (consumed)) -> biphenyltetracarboxylic acid dianhydride (BPDA) (C16H6O6), 3 Water (heater), Hydrogen (skips a step: Phthalic Anhydride (C6H4(CO)2O))
         *
         */

        // Kevlar
        CHEMICAL_RECIPES.recipeBuilder(GCyS.id("cuminaldehyde"))
                .inputFluids(Cumene.getFluid(1000))
                .inputFluids(Hydrogen.getFluid(2000))
                .inputFluids(CarbonMonoxide.getFluid(1000))
                .outputFluids(Cuminaldehyde.getFluid(1000))
                .duration(200).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCyS.id("cymene"))
                .inputFluids(Toluene.getFluid(500))
                .inputFluids(Propene.getFluid(500))
                .outputFluids(Cymene.getFluid(1000))
                .duration(200).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCyS.id("cuminol"))
                .inputFluids(Cuminaldehyde.getFluid(1000))
                .inputFluids(Hydrogen.getFluid(1000))
                .outputFluids(Cuminol.getFluid(1000))
                .duration(200).EUt(VA[HV]).save(provider);

        LARGE_CHEMICAL_RECIPES.recipeBuilder(GCyS.id("therephthalic_acid"))
                .inputFluids(Cymene.getFluid(1000))
                .inputFluids(Cuminol.getFluid(1000))
                .inputFluids(ChromicAcid.getFluid(1000))
                .outputFluids(TerephthalicAcid.getFluid(1000))
                .duration(200).EUt(VA[EV]).save(provider);

        LARGE_CHEMICAL_RECIPES.recipeBuilder(GCyS.id("terephthaloyl_chloride"))
                .inputFluids(TerephthalicAcid.getFluid(1000))
                .inputFluids(Methanol.getFluid(1000))
                .inputFluids(Chlorine.getFluid(2000))
                .outputFluids(TerephthaloylChloride.getFluid(1000))
                .outputFluids(Water.getFluid(3000))
                .duration(200).EUt(VA[EV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCyS.id("para_phenylenediamine"))
                .inputFluids(Nitrochlorobenzene.getFluid(1000))
                .inputFluids(Ammonia.getFluid(1000))
                .inputFluids(Hydrogen.getFluid(1000))
                .outputFluids(ParaPhenylenediamine.getFluid(1000))
                .outputFluids(CarbonDioxide.getFluid(1000))
                .duration(200).EUt(VA[HV]).save(provider);

        LARGE_CHEMICAL_RECIPES.recipeBuilder(GCyS.id("para_aramid"))
                .inputFluids(ParaPhenylenediamine.getFluid(1000))
                .inputFluids(TerephthaloylChloride.getFluid(1000))
                .outputFluids(ParaAramid.getFluid(1000))
                .outputFluids(HydrochloricAcid.getFluid(2000))
                .duration(200).EUt(VA[HV]).save(provider);
    }
}
