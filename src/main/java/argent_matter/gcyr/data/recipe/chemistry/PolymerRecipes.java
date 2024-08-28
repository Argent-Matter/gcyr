package argent_matter.gcyr.data.recipe.chemistry;

import argent_matter.gcyr.GCYR;
import net.minecraft.data.recipes.RecipeOutput;

import static argent_matter.gcyr.common.data.GCYRMaterials.*;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.data.material.GTMaterials.*;
import static com.gregtechceu.gtceu.data.recipe.GTRecipeTypes.*;

public class PolymerRecipes {

    public static void init(RecipeOutput provider) {
        kaptonKProcess(provider);
    }

    public static void kaptonKProcess(RecipeOutput provider) {
        // Kapton K
        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("chloronitrobenzene"))
                .inputFluids(Chlorobenzene.getFluid(1000))
                .inputFluids(NitricAcid.getFluid(1000))
                .outputFluids(Nitrochlorobenzene.getFluid(1000))
                .outputFluids(Water.getFluid(1000))
                .duration(400).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("dimethylformamide"))
                .inputFluids(CarbonMonoxide.getFluid(1000))
                .inputFluids(Dimethylamine.getFluid(1000))
                .outputFluids(Dimethylformamide.getFluid(1000))
                .duration(200).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("pyrometillic_dianhydride"))
                .inputFluids(Durene.getFluid(250))
                .inputFluids(Oxygen.getFluid(1500))
                .outputFluids(PyromelliticDianhydride.getFluid(250))
                .outputFluids(Water.getFluid(1500))
                .duration(400).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("potassium_hydroxide"))
                .inputFluids(Durene.getFluid(250))
                .inputFluids(Oxygen.getFluid(1500))
                .outputFluids(PyromelliticDianhydride.getFluid(250))
                .outputFluids(Water.getFluid(1500))
                .duration(400).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("oxydianiline_sludge"))
                .inputFluids(AminoPhenol.getFluid(1000))
                .inputFluids(Nitrochlorobenzene.getFluid(1000))
                .inputFluids(Dimethylformamide.getFluid(1000))
                .inputItems(dust, PotassiumCarbonate)
                .outputFluids(OxydianilineSludge.getFluid(250))
                .outputFluids(Water.getFluid(1500))
                .duration(400).EUt(VA[HV]).save(provider);

        DISTILLATION_RECIPES.recipeBuilder(GCYR.id("distill_oxydianiline_sludge"))
                .inputFluids(OxydianilineSludge.getFluid(1000))
                .outputFluids(Dimethylformamide.getFluid(1000))
                .outputFluids(Oxydianiline.getFluid(144))
                .duration(200).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("kapton_k"))
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
        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("cobalt_bromide"))
                .inputItems(dust, Cobalt, 1)
                .inputFluids(Bromine.getFluid(1000))
                .inputFluids(AceticAcid.getFluid(1000))
                .outputFluids(CobaltBromide.getFluid(1000))
                .duration(60).EUt(VA[HV]).save(provider);
        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("manganese_bromide"))
                .inputItems(dust, Manganese, 1)
                .inputFluids(Bromine.getFluid(1000))
                .inputFluids(AceticAcid.getFluid(1000))
                .outputFluids(ManganeseBromide.getFluid(1000))
                .duration(60).EUt(VA[HV]).save(provider);
        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("manganese_acetate"))
                .inputItems(dust, Manganese, 1)
                .inputFluids(AceticAcid.getFluid(1000))
                .outputFluids(ManganeseAcetate.getFluid(1000))
                .duration(60).EUt(VA[HV]).save(provider);
        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("hydrobromic_acid"))
                .notConsumable(dust, Platinum)
                .inputFluids(Hydrogen.getFluid(1000))
                .inputFluids(Bromine.getFluid(1000))
                .inputFluids(Water.getFluid(1000))
                .outputFluids(HydrobromicAcid.getFluid(1000))
                .duration(60).EUt(VA[HV]).save(provider);

        LARGE_CHEMICAL_RECIPES.recipeBuilder(GCYR.id("co_mn_br_catalyst"))
                .inputFluids(CobaltBromide.getFluid(1000))
                .inputFluids(ManganeseBromide.getFluid(1000))
                .inputFluids(ManganeseAcetate.getFluid(1000))
                .inputFluids(HydrobromicAcid.getFluid(1000))
                .outputFluids(CoMnBrCatalyst.getFluid(4000))
                .duration(100).EUt(VA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("bis_trichloromethyl_benzene"))
                .inputFluids(ParaXylene.getFluid(1000))
                .inputFluids(Chlorine.getFluid(12000))
                .outputFluids(BisTrichloromethylBenzene.getFluid(1000))
                .outputFluids(HydrochloricAcid.getFluid(6000))
                .duration(60).EUt(VHA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("therephthalic_acid"))
                .inputFluids(ParaXylene.getFluid(1000))
                .inputFluids(Oxygen.getFluid(2000))
                .inputFluids(CoMnBrCatalyst.getFluid(1000))
                .outputFluids(TerephthalicAcid.getFluid(1000))
                .outputFluids(Water.getFluid(1000))
                .duration(60).EUt(VHA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("terephthaloyl_chloride"))
                .inputFluids(BisTrichloromethylBenzene.getFluid(1000))
                .inputFluids(TerephthalicAcid.getFluid(1000))
                .outputFluids(TerephthaloylChloride.getFluid(2000))
                .outputFluids(HydrochloricAcid.getFluid(2000))
                .duration(60).EUt(VHA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("nitroaniline"))
                .inputFluids(Nitrochlorobenzene.getFluid(1000))
                .inputFluids(Ammonia.getFluid(2000))
                .outputFluids(Nitroaniline.getFluid(1000))
                .outputItems(dust, AmmoniumChloride, 1)
                .duration(60).EUt(VHA[HV]).save(provider);

        CHEMICAL_RECIPES.recipeBuilder(GCYR.id("para_phenylenediamine"))
                .inputFluids(Nitroaniline.getFluid(1000))
                .inputFluids(Hydrogen.getFluid(6000))
                .outputFluids(ParaPhenylenediamine.getFluid(1000))
                .outputFluids(Water.getFluid(3000))
                .duration(60).EUt(VHA[HV]).save(provider);

        LARGE_CHEMICAL_RECIPES.recipeBuilder(GCYR.id("para_aramid"))
                .inputFluids(ParaPhenylenediamine.getFluid(1000))
                .inputFluids(TerephthaloylChloride.getFluid(1000))
                .outputFluids(ParaAramid.getFluid(1000))
                .outputFluids(HydrochloricAcid.getFluid(2000))
                .duration(200).EUt(VA[HV]).save(provider);
    }
}
