package argent_matter.gcyr.common.data.recipe;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.recipe.type.RocketFuelRecipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GCYRRecipeTypes {

    // spotless:off
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, GCYR.MOD_ID);

    public static final RegistryObject<RecipeType<RocketFuelRecipe>> ROCKET_FUEL = register("rocket_fuel");

    // spotless:on

    @SuppressWarnings("SameParameterValue")
    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> register(final String name) {
        return RECIPE_TYPES.register(name, () -> RecipeType.simple(GCYR.id(name)));
    }

    public static void register(IEventBus modBus) {
        RECIPE_TYPES.register(modBus);
    }
}
