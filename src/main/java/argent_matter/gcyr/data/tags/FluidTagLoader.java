package argent_matter.gcyr.data.tags;

import com.gregtechceu.gtceu.common.data.GTMaterials;

import argent_matter.gcyr.data.recipe.GCYRTags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import com.tterrag.registrate.providers.RegistrateTagsProvider;

public class FluidTagLoader {

    public static void init(RegistrateTagsProvider.IntrinsicImpl<Fluid> provider) {
        provider.addTag(GCYRTags.VEHICLE_FUELS)
                .addOptional(GTMaterials.RocketFuel.getFluid());
    }
}
