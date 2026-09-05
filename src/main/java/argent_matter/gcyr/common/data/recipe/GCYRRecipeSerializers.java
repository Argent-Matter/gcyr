package argent_matter.gcyr.common.data.recipe;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.recipe.type.RocketFuelRecipe;
import argent_matter.gcyr.common.recipe.type.SmithingSpaceSuitRecipe;
import argent_matter.gcyr.common.recipe.type.SmithingThermalUpgradeRecipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GCYRRecipeSerializers {

    // spotless:off
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, GCYR.MOD_ID);

    public static final RegistryObject<RocketFuelRecipe.Serializer> ROCKET_FUEL = RECIPE_SERIALIZERS
            .register("rocket_fuel", RocketFuelRecipe.Serializer::new);

    public static final RegistryObject<SmithingSpaceSuitRecipe.Serializer> SMITHING_SPACESUIT = RECIPE_SERIALIZERS
            .register("smithing_spacesuit", SmithingSpaceSuitRecipe.Serializer::new);
    public static final RegistryObject<SmithingThermalUpgradeRecipe.Serializer> SMITHING_THERMAL_UPGRADE = RECIPE_SERIALIZERS
            .register("smithing_thermal_upgrade", SmithingThermalUpgradeRecipe.Serializer::new);
    // spotless:off

    public static void register(IEventBus modBus) {
        RECIPE_SERIALIZERS.register(modBus);
    }
}
