package argent_matter.gcyr.common.recipe.condition;

import argent_matter.gcyr.api.capability.GCYRCapabilityHelper;
import argent_matter.gcyr.api.capability.IDysonSystem;
import argent_matter.gcyr.common.data.GCYRRecipeConditions;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeCondition;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.NoArgsConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
public class DysonSphereCondition extends RecipeCondition {

    public static final MapCodec<DysonSphereCondition> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return RecipeCondition.isReverse(instance).apply(instance, DysonSphereCondition::new);
    });
    public final static DysonSphereCondition INSTANCE = new DysonSphereCondition();

    public DysonSphereCondition(boolean isReverse) {
        super(isReverse);
    }

    @Override
    public RecipeConditionType<?> getType() {
        return GCYRRecipeConditions.DYSON_SPHERE;
    }

    @Override
    public Component getTooltips() {
        return Component.translatable(isReverse ? "gcyr.condition.requires_dyson_sphere.false" : "gcyr.condition.requires_dyson_sphere.true");
    }

    @Override
    public boolean test(@NotNull GTRecipe recipe, @NotNull RecipeLogic recipeLogic) {
        Level level = recipeLogic.getMachine().getLevel();
        if (!level.isClientSide) {
            IDysonSystem system = GCYRCapabilityHelper.getDysonSystem((ServerLevel) level);
            if (system == null) return false;
            return system.isDysonSphereActive() && !system.activeDysonSphere().isCollapsed();
        }
        return false;
    }

    @Override
    public RecipeCondition createTemplate() {
        return new DysonSphereCondition();
    }
}
