package argent_matter.gcys.data.recipe;

import argent_matter.gcys.GCyS;
import argent_matter.gcys.common.data.GCySItems;
import argent_matter.gcys.common.data.GCySMaterials;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static argent_matter.gcys.common.data.GCySMaterials.*;
import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class MiscRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, true, GCyS.id("casing_atomic"),
                GCyMBlocks.CASING_ATOMIC.asStack(2),
                "PhP", "PFP", "PwP",
                'P', new UnificationEntry(TagPrefix.plateDouble, GCySMaterials.Trinaquadalloy),
                'F', new UnificationEntry(TagPrefix.frameGt, GTMaterials.NaquadahAlloy));

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(GCyS.id("casing_atomic"))
                .inputItems(TagPrefix.plateDouble, GCySMaterials.Trinaquadalloy, 6)
                .inputItems(TagPrefix.frameGt, GTMaterials.NaquadahAlloy)
                .circuitMeta(6)
                .outputItems(GCyMBlocks.CASING_ATOMIC.asStack(2))
                .duration(50).EUt(16).save(provider);

        VanillaRecipeHelper.addShapelessRecipe(provider, GCyS.id("id_chip"), GCySItems.ID_CHIP.asStack(),
                new UnificationEntry(foil, IronMagnetic),
                new UnificationEntry(plate, Polyethylene)
        );

        VanillaRecipeHelper.addShapedRecipe(provider, GCyS.id("photovoltaic_cell"), GCySItems.PHOTOVOLTAIC_CELL.asStack(),
                "WVW", "VGV", "CVC",
                'W', GTItems.NAQUADAH_WAFER.asStack(), 'G', GTBlocks.CASING_TEMPERED_GLASS.asStack(), 'C', CustomTags.LuV_CIRCUITS, 'V', new UnificationEntry(wireFine, Gold)
        );

        GTRecipeTypes.CHEMICAL_BATH_RECIPES.recipeBuilder(GCyS.id("fiberglass"))
                .inputItems(dust, SiliconDioxide, 2)
                .inputFluids(Epoxy.getFluid(250))
                .outputFluids(FiberGlass.getFluid(250))
                .duration(200).EUt(VA[EV]).save(provider);

        //region Spacesuit stuff
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(GCyS.id("space_fabric"))
                .inputItems(foil, Polytetrafluoroethylene, 4)
                .inputItems(foil, ParaAramid, 4)
                .inputItems(dust, FiberGlass, 4)
                .outputItems(GCySItems.SPACE_FABRIC.asStack(1))
                .duration(100).EUt(VA[HV]).save(provider);

        VanillaRecipeHelper.addShapedRecipe(provider, GCyS.id("space_helmet"), GCySItems.SPACE_SUIT_HELMET.asStack(),
                "SFS", "FFF", "SCS",
                'S', GCySItems.SPACE_FABRIC.asStack(), 'F', new UnificationEntry(foil, Gold), 'C', CustomTags.EV_CIRCUITS
        );

        VanillaRecipeHelper.addShapedRecipe(provider, GCyS.id("space_chest"), GCySItems.SPACE_SUIT_CHEST.asStack(),
                "STS", "SCS", "SCS",
                'S', GCySItems.SPACE_FABRIC.asStack(), 'T', GTItems.FLUID_CELL_LARGE_TUNGSTEN_STEEL.asStack(), 'C', CustomTags.EV_CIRCUITS
        );

        VanillaRecipeHelper.addShapedRecipe(provider, GCyS.id("space_legs"), GCySItems.SPACE_SUIT_LEGS.asStack(),
                "SCS", "S S", "S S",
                'S', GCySItems.SPACE_FABRIC.asStack(), 'C', CustomTags.EV_CIRCUITS
        );

        VanillaRecipeHelper.addShapedRecipe(provider, GCyS.id("space_boots"), GCySItems.SPACE_SUIT_BOOTS.asStack(),
                "S S", "S S",
                'S', GCySItems.SPACE_FABRIC.asStack()
        );

        //endregion
    }

}
