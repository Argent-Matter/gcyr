package argent_matter.gcyr.data.recipe.pregenerated;

import com.gregtechceu.gtceu.common.data.GTMaterials;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;

import static argent_matter.gcyr.data.recipe.builder.RocketFuelRecipeBuilder.rocketFuel;

public class RocketFuelRecipes {

    // spotless:off
    public static void init(RegistrateRecipeProvider provider) {
        rocketFuel(GTMaterials.Gasoline.getFluidTag(), 12.0f, 0).save(provider); // 34.2 MJ/L IRL
        rocketFuel(GTMaterials.Diesel.getFluidTag(), 10.0f, 0).save(provider); // 38.6 MJ/L IRL
        rocketFuel(GTMaterials.RocketFuel.getFluidTag(), 13.0f, 1).save(provider); // 93.9 MJ/L IRL (C2H8N2)
        // rocketFuel(GTMaterials.Hydrogen.getFluidTag(), 5.5f, 1).save(provider); // 4.5 MJ/L IRL
        // rocketFuel(GTMaterials.Hydrogen.getFluid(FluidStorageKeys.LIQUID), 5.5f, 1).save(provider); // 8.4 MJ/L IRL
        // rocketFuel(GTMaterials.Hydrogen.getFluid(FluidStorageKeys.PLASMA), 10.0f, 3).save(provider); // ??? MJ/L IRL
    }
    // spotless:on
}
