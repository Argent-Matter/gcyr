package argent_matter.gcyr.data.tags;

import argent_matter.gcyr.data.recipe.GCyRTags;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class FluidTagLoader {
    public static void init(RegistrateTagsProvider<Fluid> provider) {
        createFluid(provider, GCyRTags.VEHICLE_FUELS, "gtceu:rocket_fuel");
    }

    private static void createFluid(RegistrateTagsProvider<Fluid> provider, TagKey<Fluid> tagKey, String... rls) {
        var  builder = provider.addTag(tagKey);
        for (String str : rls) {
            if (str.startsWith("#")) builder.addOptionalTag(rl(str.substring(1)));
            else builder.addOptional(rl(str));
        }
    }

    private static ResourceLocation rl(String name) {
        return new ResourceLocation(name);
    }

}
