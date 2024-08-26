package argent_matter.gcyr.common.data;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.recipe.type.SmithingSpaceSuitRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GCYRVanillaRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_TYPE_DEFERRED_REGISTER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, GCYR.MOD_ID);

    public static final RegistryObject<SmithingSpaceSuitRecipe.Serializer> SMITHING_SPACESUIT_SERIALIZER = RECIPE_TYPE_DEFERRED_REGISTER.register("smithing_spacesuit", SmithingSpaceSuitRecipe.Serializer::new);
}
