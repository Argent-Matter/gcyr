package argent_matter.gcyr.integration.recipeviewer;

import argent_matter.gcyr.api.mui.drawable.GCYRGuiTextures;
import argent_matter.gcyr.common.recipe.type.RocketFuelRecipe;

import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;

import brachy.modularui.api.drawable.Text;
import brachy.modularui.api.widget.IWidget;
import brachy.modularui.drawable.GuiTextures;
import brachy.modularui.drawable.progress.ProgressDrawable;
import brachy.modularui.drawable.text.ModularComponent;
import brachy.modularui.integration.recipeviewer.RecipeSlotRole;
import brachy.modularui.integration.recipeviewer.RecipeViewerSlotWidget;
import brachy.modularui.integration.recipeviewer.entry.fluid.FluidEntryList;
import brachy.modularui.integration.recipeviewer.entry.fluid.FluidStackList;
import brachy.modularui.screen.ModularPanel;
import brachy.modularui.utils.FormattingUtil;
import brachy.modularui.value.DoubleValue;
import brachy.modularui.widgets.ProgressWidget;
import brachy.modularui.widgets.layout.Flow;

import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;

import org.jetbrains.annotations.NotNull;

public final class RocketFuelRecipeDisplay {

    private RocketFuelRecipeDisplay() {}

    @SuppressWarnings("UnstableApiUsage")
    public static IWidget createWidgetForRecipe(RocketFuelRecipe recipe) {
        var panel = new ModularPanel<>("recipe_viewer_recipe")
                .coverChildren(152, 32)
                .invisible();

        ModularComponent specificEnergyText = Text.lang("gcyr.recipe.rocket_fuel.specific_energy",
                FormattingUtil.DECIMAL_FORMAT_1F.format(recipe.getSpecificEnergy()));
        ModularComponent tierRangeText = getTierRangeText(recipe);

        IWidget recipeUI = Flow.col()
                .coverChildren()
                .childPadding(2)
                .child(Flow.row().name("slots")
                        .coverChildren()
                        .childPadding(2)
                        .child(RecipeViewerSlotWidget.create(FluidStack.class)
                                .size(18)
                                .recipeSlotRole(RecipeSlotRole.INPUT)
                                .value(toEntryList(recipe.getFuel()))
                                .background(GuiTextures.SLOT_FLUID))
                        .child(new ProgressWidget()
                                .value(DoubleValue.simulateProgress((int) (recipe.getSpecificEnergy() * 1000f)))
                                .size(20)
                                .texture(GCYRGuiTextures.PROGRESS_BAR_ROCKET, ProgressDrawable.Direction.RIGHT))
                        .child(specificEnergyText.asWidget()))
                .child(tierRangeText.asWidget());
        return panel.child(recipeUI);
    }

    private static @NotNull ModularComponent getTierRangeText(RocketFuelRecipe recipe) {
        ModularComponent tierRangeText;

        var tierRange = recipe.getValidRocketTiers();
        String translationKey = "gcyr.recipe.rocket_fuel.tier_range";
        if (tierRange.minInclusive() <= 0 && tierRange.maxInclusive() == Integer.MAX_VALUE) {
            translationKey += ".any";
        } else if (tierRange.maxInclusive() == Integer.MAX_VALUE) {
            translationKey += ".min";
        } else if (tierRange.minInclusive() <= 0) {
            translationKey += ".max";
        }

        tierRangeText = Text.lang(translationKey, tierRange.minInclusive(), tierRange.maxInclusive());
        return tierRangeText;
    }

    public static FluidEntryList toEntryList(FluidIngredient ingredient) {
        return FluidStackList.of(Arrays.asList(ingredient.getStacks()));
    }
}
