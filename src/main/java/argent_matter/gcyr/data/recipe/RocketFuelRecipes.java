package argent_matter.gcyr.data.recipe;

import argent_matter.gcyr.GCYR;

import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static argent_matter.gcyr.common.data.GCYRRecipeTypes.ROCKET_FUEL_RECIPES;

public class RocketFuelRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        ROCKET_FUEL_RECIPES.recipeBuilder(GCYR.id("gasoline"))
                .inputFluids(GTMaterials.Gasoline.getFluid(1))
                .addData("specific_energy", 12.0F)
                .addData("hide_duration", true)
                .save(provider);

        ROCKET_FUEL_RECIPES.recipeBuilder(GCYR.id("diesel"))
                .inputFluids(GTMaterials.Diesel.getFluid(1))
                .addData("specific_energy", 10.0F)
                .addData("hide_duration", true)
                .save(provider);

        ROCKET_FUEL_RECIPES.recipeBuilder(GCYR.id("rocket_fuel"))
                .inputFluids(GTMaterials.RocketFuel.getFluid(1))
                .addData("specific_energy", 13.0F)
                .addData("hide_duration", true)
                .save(provider);
    }
}
