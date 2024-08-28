package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.recipe.type.SmithingSpaceSuitRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GCYRVanillaRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_TYPE_DEFERRED_REGISTER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, GCYR.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, SmithingSpaceSuitRecipe.Serializer> SMITHING_SPACESUIT_SERIALIZER = RECIPE_TYPE_DEFERRED_REGISTER.register("smithing_spacesuit", SmithingSpaceSuitRecipe.Serializer::new);
}
