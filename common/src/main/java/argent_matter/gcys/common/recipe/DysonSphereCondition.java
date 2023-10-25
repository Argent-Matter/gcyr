package argent_matter.gcys.common.recipe;

import argent_matter.gcys.GCySClient;
import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.api.capability.IDysonSystem;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import lombok.NoArgsConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
public class DysonSphereCondition extends RecipeCondition {
    public final static DysonSphereCondition INSTANCE = new DysonSphereCondition();

    @Override
    public String getType() {
        return "dyson_sphere";
    }

    @Override
    public Component getTooltips() {
        return Component.translatable(isReverse ? "gcys.condition.requires_dyson_sphere.false" : "gcys.condition.requires_dyson_sphere.true");
    }

    @Override
    public boolean test(@NotNull GTRecipe recipe, @NotNull RecipeLogic recipeLogic) {
        Level level = recipeLogic.getMachine().getLevel();
        if (!level.isClientSide) {
            IDysonSystem system = GcysCapabilityHelper.getDysonSystem((ServerLevel) level);
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
