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
        EVAPORATION_RECIPES.recipeBuilder(GCyR.id("brine"))
                .inputFluids(SaltWater.getFluid(20000))
                .outputFluids(Brine.getFluid(1000))
                .duration(1000).EUt(VA[HV]).save(provider);
        CENTRIFUGE_RECIPES.recipeBuilder(GCyR.id("brine_separation"))
                .inputFluids(Brine.getFluid(1000))
                .outputItems(dust, Salt, 20)
                .outputItems(dust, Magnesium, 3)
                .outputItems(dust, Lithium, 2)
                .outputFluids(Bromine.getFluid(33))
                .duration(200).EUt(VA[HV]).save(provider);
    }

}
