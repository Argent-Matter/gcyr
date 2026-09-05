package argent_matter.gcyr.common.data.recipe;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.recipe.type.SmithingSpaceSuitRecipe;
import argent_matter.gcyr.common.recipe.type.SmithingThermalUpgradeRecipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GCYRVanillaRecipeTypes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_TYPE_DEFERRED_REGISTER = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, GCYR.MOD_ID);

    public static final RegistryObject<SmithingSpaceSuitRecipe.Serializer> SMITHING_SPACESUIT_SERIALIZER = RECIPE_TYPE_DEFERRED_REGISTER
            .register("smithing_spacesuit", SmithingSpaceSuitRecipe.Serializer::new);

    public static final RegistryObject<SmithingThermalUpgradeRecipe.Serializer> SMITHING_THERMAL_UPGRADE_SERIALIZER = RECIPE_TYPE_DEFERRED_REGISTER
            .register("smithing_thermal_upgrade", SmithingThermalUpgradeRecipe.Serializer::new);
}
