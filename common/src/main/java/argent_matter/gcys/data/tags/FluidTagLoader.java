package argent_matter.gcys.data.tags;

import argent_matter.gcys.data.recipe.GcysTags;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class FluidTagLoader {
    public static void init(RegistrateTagsProvider<Fluid> provider) {
        createBlock(provider, GcysTags.VEHICLE_FUELS, "gtceu:rocket_fuel");
    }

    private static void createBlock(RegistrateTagsProvider<Fluid> provider, TagKey<Fluid> tagKey, String... rls) {
        TagBuilder builder = provider.getOrCreateRawBuilder(tagKey);
        for (String str : rls) {
            if (str.startsWith("#")) builder.addOptionalTag(rl(str.substring(1)));
            else builder.addElement(rl(str));
        }
        builder.build();
    }

    private static ResourceLocation rl(String name) {
        return new ResourceLocation(name);
    }

}
