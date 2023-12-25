package argent_matter.gcyr.data.recipe;

import argent_matter.gcyr.GCyR;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static argent_matter.gcyr.common.data.GCyRRecipeTypes.ROCKET_FUEL_RECIPES;

public class RocketFuelRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        ROCKET_FUEL_RECIPES.recipeBuilder(GCyR.id("gasoline"))
                .inputFluids(GTMaterials.Gasoline.getFluid(1)) // amount doesn't matter
                .duration(25) // duration is used / 10 as a divisor to fuel usage
                .EUt(0) // use EUt as a bogus tier indicator. more than a rocket's motor tier fuels aren't allowed to be used in the rocket.
                .save(provider);
        ROCKET_FUEL_RECIPES.recipeBuilder(GCyR.id("diesel"))
                .inputFluids(GTMaterials.Gasoline.getFluid(1))
                .duration(18).EUt(0)
                .save(provider);

        ROCKET_FUEL_RECIPES.recipeBuilder(GCyR.id("rocket_fuel"))
                .inputFluids(GTMaterials.RocketFuel.getFluid(1))
                .duration(75).EUt(1)
                .save(provider);
        ROCKET_FUEL_RECIPES.recipeBuilder(GCyR.id("hydrogen"))
                .inputFluids(GTMaterials.Hydrogen.getFluid(1))
                .duration(10).EUt(1)
                .save(provider);

        ROCKET_FUEL_RECIPES.recipeBuilder(GCyR.id("hydrogen_plasma"))
                .inputFluids(GTMaterials.Hydrogen.getFluid(FluidStorageKeys.PLASMA, 1))
                .duration(18).EUt(3)
                .save(provider);
    }
}
