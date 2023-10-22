package argent_matter.gcys.common.recipe;

import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.api.capability.IDysonSystem;
import com.google.gson.JsonObject;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
public class DysonSphereCondition extends RecipeCondition {
    public final static DysonSphereCondition INSTANCE = new DysonSphereCondition();

    @Getter
    private boolean isActive = true;

    @Override
    public String getType() {
        return "dyson_sphere";
    }

    @Override
    public Component getTooltips() {
        return Component.translatable(isActive ? "gcys.condition.requires_dyson_sphere.true" : "gcys.condition.requires_dyson_sphere.false");
    }

    @Override
    public boolean test(@NotNull GTRecipe recipe, @NotNull RecipeLogic recipeLogic) {
        Level level = recipeLogic.getMachine().getLevel();
        if (!level.isClientSide) {
            IDysonSystem system = GcysCapabilityHelper.getDysonSystem((ServerLevel) level, recipeLogic.getMachine().getPos());
            if (system == null || !system.isDysonSphereActive() || !system.activeDysonSphere().isCollapsed() && !isActive) return true;
            else return system.isDysonSphereActive() && !system.activeDysonSphere().isCollapsed() && isActive;
        }
        return false;
    }

    @NotNull
    @Override
    public JsonObject serialize() {
        JsonObject value = super.serialize();
        value.addProperty("isActive", isActive);
        return value;
    }

    @Override
    public RecipeCondition deserialize(@NotNull JsonObject config) {
        super.deserialize(config);
        this.isActive = GsonHelper.getAsBoolean(config, "isActive", true);
        return this;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        super.toNetwork(buf);
        buf.writeBoolean(this.isActive);
    }

    @Override
    public RecipeCondition fromNetwork(FriendlyByteBuf buf) {
        super.fromNetwork(buf);
        this.isActive = buf.readBoolean();
        return this;
    }

    @Override
    public RecipeCondition createTemplate() {
        return new DysonSphereCondition();
    }
}
