package argent_matter.gcyr.data.recipe;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static argent_matter.gcyr.common.data.GCyRMaterials.*;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;

public class RecipeOverrides {

    public static void init(Consumer<FinishedRecipe> provider) {
        DISTILLATION_RECIPES.recipeBuilder("distill_coal_tar")
                .inputFluids(CoalTar.getFluid(1000))
                .outputItems(dustSmall, Coke)
                .outputFluids(Naphthalene.getFluid(300))
                .outputFluids(HydrogenSulfide.getFluid(300))
                .outputFluids(Creosote.getFluid(200))
                .outputFluids(Phenol.getFluid(100))
                .outputFluids(Durene.getFluid(100))
                .duration(80).EUt(VA[MV])
                .save(provider);

        DISTILLATION_RECIPES.recipeBuilder("distill_lightly_steam_cracked_naphtha")
                .inputFluids(LightlySteamCrackedNaphtha.getFluid(1000))
                .outputItems(dustTiny, Carbon)
                .outputFluids(HeavyFuel.getFluid(75))
                .outputFluids(LightFuel.getFluid(150))
                .outputFluids(Toluene.getFluid(40))
                .outputFluids(Benzene.getFluid(150))
                .outputFluids(ParaXylene.getFluid(75))
                .outputFluids(Butene.getFluid(80))
                .outputFluids(Butadiene.getFluid(150))
                .outputFluids(Propane.getFluid(15))
                .outputFluids(Propene.getFluid(150))
                .outputFluids(Ethane.getFluid(35))
                .outputFluids(Ethylene.getFluid(175))
                .outputFluids(Methane.getFluid(200))
                .duration(120).EUt(VA[MV]).save(provider);

        DISTILLATION_RECIPES.recipeBuilder("distill_severely_steam_cracked_naphtha")
                .inputFluids(SeverelySteamCrackedNaphtha.getFluid(1000))
                .outputItems(dustTiny, Carbon, 3)
                .outputFluids(HeavyFuel.getFluid(25))
                .outputFluids(LightFuel.getFluid(50))
                .outputFluids(Toluene.getFluid(20))
                .outputFluids(Benzene.getFluid(100))
                .outputFluids(ParaXylene.getFluid(100))
                .outputFluids(Butene.getFluid(50))
                .outputFluids(Butadiene.getFluid(50))
                .outputFluids(Propane.getFluid(15))
                .outputFluids(Propene.getFluid(250))
                .outputFluids(Ethane.getFluid(65))
                .outputFluids(Ethylene.getFluid(450))
                .outputFluids(Methane.getFluid(500))
                .duration(120).EUt(VA[MV]).save(provider);
    }
}
