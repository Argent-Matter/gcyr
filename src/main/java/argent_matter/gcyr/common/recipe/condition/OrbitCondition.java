package argent_matter.gcyr.common.recipe.condition;

import argent_matter.gcyr.GCYRClient;
import argent_matter.gcyr.common.data.network.GCYRNetworking;
import argent_matter.gcyr.common.data.recipe.GCYRRecipeConditions;
import argent_matter.gcyr.common.networking.c2s.PacketRequestPlanetData;
import argent_matter.gcyr.data.loader.PlanetData;

import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import lombok.NoArgsConstructor;

import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
public class OrbitCondition extends RecipeCondition<OrbitCondition> {

    public static final Codec<OrbitCondition> CODEC = RecordCodecBuilder
            .create(instance -> RecipeCondition.isReverse(instance).apply(instance, OrbitCondition::new));
    public final static OrbitCondition INSTANCE = new OrbitCondition();

    public OrbitCondition(boolean isReverse) {
        super(isReverse);
    }

    @Override
    public RecipeConditionType<OrbitCondition> getType() {
        return GCYRRecipeConditions.ORBIT;
    }

    @Override
    public Component getTooltips() {
        return Component.translatable("gcyr.condition.space");
    }

    @Override
    public boolean testCondition(@NotNull GTRecipe recipe, @NotNull RecipeLogic recipeLogic) {
        Level level = recipeLogic.getMachine().getLevel();
        if (level.isClientSide && !GCYRClient.hasUpdatedPlanets) {
            GCYRNetworking.NETWORK.sendToServer(new PacketRequestPlanetData());
            GCYRClient.hasUpdatedPlanets = true;
        }
        return PlanetData.isOrbitDimension(level.dimension());
    }

    @Override
    public OrbitCondition createTemplate() {
        return new OrbitCondition();
    }
}
