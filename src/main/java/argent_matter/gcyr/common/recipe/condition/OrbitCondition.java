package argent_matter.gcyr.common.recipe.condition;

import argent_matter.gcyr.GCyRClient;
import argent_matter.gcyr.common.data.GCyRNetworking;
import argent_matter.gcyr.common.networking.c2s.PacketRequestPlanetData;
import argent_matter.gcyr.data.loader.PlanetData;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import lombok.NoArgsConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
public class OrbitCondition extends RecipeCondition {
    public final static OrbitCondition INSTANCE = new OrbitCondition();

    @Override
    public String getType() {
        return "orbit";
    }

    @Override
    public Component getTooltips() {
        return Component.translatable("gcyr.condition.space");
    }

    @Override
    public boolean test(@NotNull GTRecipe recipe, @NotNull RecipeLogic recipeLogic) {
        Level level = recipeLogic.getMachine().getLevel();
        if (level.isClientSide && !GCyRClient.hasUpdatedPlanets) {
            GCyRNetworking.NETWORK.sendToServer(new PacketRequestPlanetData());
            GCyRClient.hasUpdatedPlanets = true;
        }
        return PlanetData.isOrbitLevel(level.dimension());
    }

    @Override
    public RecipeCondition createTemplate() {
        return new OrbitCondition();
    }
}
