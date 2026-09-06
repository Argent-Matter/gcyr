package argent_matter.gcyr.data.recipe.builder;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.recipe.GCYRRecipeSerializers;
import argent_matter.gcyr.common.recipe.type.RocketFuelRecipe;

import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.InclusiveRange;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluid;

import com.mojang.serialization.JsonOps;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

public class RocketFuelRecipeBuilder {

    // make this a neoforge fluid ingredient on 1.20
    private final FluidIngredient fuel;
    private final float specificEnergy;
    private final InclusiveRange<Integer> validRocketTiers;

    private @Nullable ResourceLocation defaultId;

    private RocketFuelRecipeBuilder(FluidIngredient fuel, float specificEnergy,
                                    InclusiveRange<Integer> validRocketTiers) {
        this.fuel = fuel;
        this.specificEnergy = specificEnergy;
        this.validRocketTiers = validRocketTiers;
    }

    public static RocketFuelRecipeBuilder rocketFuel(FluidIngredient fuel, float specificEnergy,
                                                     InclusiveRange<Integer> validRocketTiers) {
        return new RocketFuelRecipeBuilder(fuel, specificEnergy, validRocketTiers);
    }

    public static RocketFuelRecipeBuilder rocketFuel(FluidIngredient fuel, float specificEnergy, int minRocketTier) {
        return new RocketFuelRecipeBuilder(fuel, specificEnergy, minOf(minRocketTier));
    }

    public static RocketFuelRecipeBuilder rocketFuel(TagKey<Fluid> fuel, float specificEnergy,
                                                     InclusiveRange<Integer> validRocketTiers) {
        RocketFuelRecipeBuilder builder = rocketFuel(FluidIngredient.of(fuel, 1), specificEnergy, validRocketTiers);
        builder.defaultId = GCYR.id(fuel.location().getPath().replace('/', '_'));
        return builder;
    }

    public static RocketFuelRecipeBuilder rocketFuel(TagKey<Fluid> fuel, float specificEnergy, int minRocketTier) {
        return rocketFuel(fuel, specificEnergy, minOf(minRocketTier));
    }

    public static RocketFuelRecipeBuilder rocketFuel(Fluid fuel, float specificEnergy,
                                                     InclusiveRange<Integer> validRocketTiers) {
        RocketFuelRecipeBuilder builder = rocketFuel(FluidIngredient.of(fuel, 1), specificEnergy, validRocketTiers);
        builder.defaultId = GCYR.id(BuiltInRegistries.FLUID.getKey(fuel).getPath().replace('/', '_'));
        return builder;
    }

    public static RocketFuelRecipeBuilder rocketFuel(Fluid fuel, float specificEnergy, int minRocketTier) {
        return rocketFuel(fuel, specificEnergy, minOf(minRocketTier));
    }

    public static RocketFuelRecipeBuilder rocketFuel(Supplier<? extends Fluid> fuel, float specificEnergy,
                                                     InclusiveRange<Integer> validRocketTiers) {
        return rocketFuel(fuel.get(), specificEnergy, validRocketTiers);
    }

    public static RocketFuelRecipeBuilder rocketFuel(Supplier<? extends Fluid> fuel, float specificEnergy,
                                                     int minRocketTier) {
        return rocketFuel(fuel.get(), specificEnergy, minRocketTier);
    }

    public void save(Consumer<FinishedRecipe> provider) {
        Objects.requireNonNull(defaultId, "Cannot create a recipe using a default id without a valid default id!");
        save(provider, defaultId);
    }

    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation recipeId) {
        consumer.accept(new Result(recipeId.withPrefix("rocket_fuel/"),
                this.fuel, this.specificEnergy, this.validRocketTiers));
    }

    public record Result(@Getter ResourceLocation id, FluidIngredient fuel, float specificEnergy,
                         InclusiveRange<Integer> validRocketTiers)
            implements FinishedRecipe {

        @Override
        public void serializeRecipeData(JsonObject json) {}

        @Override
        public JsonObject serializeRecipe() {
            JsonObject prefix = FinishedRecipe.super.serializeRecipe();

            JsonElement full = RocketFuelRecipe.CODEC
                    .encode(new RocketFuelRecipe(id, fuel, specificEnergy, validRocketTiers),
                            JsonOps.INSTANCE, JsonOps.INSTANCE.mapBuilder())
                    .build(prefix)
                    .getOrThrow(false, GCYR.LOGGER::error);
            return GsonHelper.convertToJsonObject(full, "serialized recipe");
        }

        public RecipeSerializer<RocketFuelRecipe> getType() {
            return GCYRRecipeSerializers.ROCKET_FUEL.get();
        }

        public @Nullable JsonObject serializeAdvancement() {
            return null;
        }

        public @Nullable ResourceLocation getAdvancementId() {
            return null;
        }
    }

    private static InclusiveRange<Integer> minOf(int min) {
        Preconditions.checkArgument(min >= 0, "Minimum tier must be at least 0");
        return new InclusiveRange<>(min, Integer.MAX_VALUE);
    }
}
