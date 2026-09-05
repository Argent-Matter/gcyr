package argent_matter.gcyr.data.recipe.pregenerated;

import com.gregtechceu.gtceu.common.data.GTMaterials;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;

import static argent_matter.gcyr.data.recipe.builder.RocketFuelRecipeBuilder.rocketFuel;

public class RocketFuelRecipes {

    public static void init(RegistrateRecipeProvider provider) {
        rocketFuel(GTMaterials.Gasoline.getFluidTag(), 12.0f, 0).save(provider);
        rocketFuel(GTMaterials.Diesel.getFluidTag(), 10.0f, 0).save(provider);
        rocketFuel(GTMaterials.RocketFuel.getFluidTag(), 13.0f, 1).save(provider);
        // rocketFuel(GTMaterials.Hydrogen.getFluidTag(), 5.5f, 1).save(provider);
        // rocketFuel(GTMaterials.Hydrogen.getFluid(FluidStorageKeys.PLASMA), 10.0f, 3).save(provider);
    }
}
