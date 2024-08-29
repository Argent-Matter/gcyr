package argent_matter.gcyr.common.recipe.condition;

import argent_matter.gcyr.GCYRClient;
import argent_matter.gcyr.common.data.GCYRRecipeConditions;
import argent_matter.gcyr.common.networking.c2s.PacketRequestPlanetData;
import argent_matter.gcyr.data.loader.PlanetData;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeCondition;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.NoArgsConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
public class OrbitCondition extends RecipeCondition {
    public final static OrbitCondition INSTANCE = new OrbitCondition();

    public static final MapCodec<OrbitCondition> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return RecipeCondition.isReverse(instance).apply(instance, OrbitCondition::new);
    });

    public OrbitCondition(boolean isReverse) {
        super(isReverse);
    }

    @Override
    public RecipeConditionType<?> getType() {
        return GCYRRecipeConditions.ORBIT;
    }

    @Override
    public Component getTooltips() {
        return Component.translatable("gcyr.condition.space");
    }

    @Override
    public boolean test(@NotNull GTRecipe recipe, @NotNull RecipeLogic recipeLogic) {
        Level level = recipeLogic.getMachine().getLevel();
        if (level.isClientSide && !GCYRClient.hasUpdatedPlanets) {
            PacketDistributor.sendToServer(new PacketRequestPlanetData());
            GCYRClient.hasUpdatedPlanets = true;
        }
        return PlanetData.isOrbitLevel(level.dimension());
    }

    @Override
    public RecipeCondition createTemplate() {
        return new OrbitCondition();
    }
}
