package argent_matter.gcyr.common.recipe.type;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.api.data.IdContextOps;
import argent_matter.gcyr.common.data.recipe.GCYRRecipeSerializers;
import argent_matter.gcyr.common.data.recipe.GCYRRecipeTypes;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.InclusiveRange;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

// this should be a data map, but those don't exist in lexforge, so it's a recipe.
public class RocketFuelRecipe implements Recipe<Container> {

    // spotless:off
    private static final Codec<InclusiveRange<Integer>> RANGE_CODEC = Codec.either(InclusiveRange.codec(Codec.INT, 0, Integer.MAX_VALUE), ExtraCodecs.NON_NEGATIVE_INT)
            .comapFlatMap(either -> {
                return either.map(DataResult::success, min -> InclusiveRange.create(min, Integer.MAX_VALUE));
            }, range -> {
                if (range.maxInclusive() == Integer.MAX_VALUE) return Either.right(range.minInclusive());
                else return Either.left(range);
            });
    private static final InclusiveRange<Integer> ANY_TIER = new InclusiveRange<>(0, Integer.MAX_VALUE);

    public static final MapCodec<RocketFuelRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            IdContextOps.retrieveId().forGetter(RocketFuelRecipe::getId), // remove in 1.21
            FluidIngredient.CODEC.fieldOf("fuel").forGetter(RocketFuelRecipe::getFuel),
            ExtraCodecs.POSITIVE_FLOAT.fieldOf("specific_energy").forGetter(RocketFuelRecipe::getSpecificEnergy),
            RANGE_CODEC.optionalFieldOf("valid_rocket_tiers", ANY_TIER).forGetter(RocketFuelRecipe::getValidRocketTiers)
    ).apply(instance, RocketFuelRecipe::new));
    // spotless:on

    @Getter
    private final ResourceLocation id;
    // make this a neoforge fluid ingredient on 1.20
    // it's an ingredient so it supports tags/components/etc.
    @Getter
    private final FluidIngredient fuel;
    @Getter
    private final float specificEnergy;
    @Getter
    private final InclusiveRange<Integer> validRocketTiers;

    public RocketFuelRecipe(ResourceLocation id, FluidIngredient fuel, float specificEnergy,
                            InclusiveRange<Integer> validRocketTiers) {
        this.id = id;
        this.fuel = fuel;
        this.specificEnergy = specificEnergy;
        this.validRocketTiers = validRocketTiers;
    }

    public boolean matches(FluidStack fluid) {
        return this.getFuel().test(fluid);
    }

    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return GCYRRecipeSerializers.ROCKET_FUEL.get();
    }

    @Override
    public RecipeType<RocketFuelRecipe> getType() {
        return GCYRRecipeTypes.ROCKET_FUEL.get();
    }

    public static class Serializer implements RecipeSerializer<RocketFuelRecipe> {

        public RocketFuelRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
            IdContextOps<JsonElement> ops = IdContextOps.create(JsonOps.INSTANCE, id);
            return RocketFuelRecipe.CODEC.compressedDecode(ops, jsonObject)
                    .getOrThrow(false, GCYR.LOGGER::error);
        }

        public RocketFuelRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            FluidIngredient fuel = FluidIngredient.fromNetwork(buf);
            float specificEnergy = buf.readFloat();
            InclusiveRange<Integer> validRocketTiers = new InclusiveRange<>(buf.readVarInt(), buf.readVarInt());
            return new RocketFuelRecipe(id, fuel, specificEnergy, validRocketTiers);
        }

        public void toNetwork(FriendlyByteBuf buf, RocketFuelRecipe recipe) {
            recipe.fuel.toNetwork(buf);
            buf.writeFloat(recipe.specificEnergy);

            buf.writeVarInt(recipe.validRocketTiers.minInclusive());
            buf.writeVarInt(recipe.validRocketTiers.maxInclusive());
        }
    }
}
