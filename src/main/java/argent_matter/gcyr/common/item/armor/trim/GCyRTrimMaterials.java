package argent_matter.gcyr.common.item.armor.trim;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.data.GCyRItems;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;

import java.util.Map;

public class GCyRTrimMaterials {

    public static final ResourceKey<TrimMaterial> SPACE = ResourceKey.create(Registries.TRIM_MATERIAL, GCyR.id("space"));

    public static void bootstrap(BootstapContext<TrimMaterial> ctx) {
        register(ctx, SPACE, GCyRItems.SPACE_FABRIC.get(), Style.EMPTY.withColor(0xc5991a), 1.1f);
    }

    private static void register(BootstapContext<TrimMaterial> context, ResourceKey<TrimMaterial> materialKey, Item ingredient, Style style, float itemModelIndex) {
        register(context, materialKey, ingredient, style, itemModelIndex, Map.of());
    }

    private static void register(
            BootstapContext<TrimMaterial> context,
            ResourceKey<TrimMaterial> materialKey,
            Item ingredient,
            Style style,
            float itemModelIndex,
            Map<ArmorMaterials, String> overrideArmorMaterials
    ) {
        TrimMaterial trimMaterial = TrimMaterial.create(
                materialKey.location().getPath(),
                ingredient,
                itemModelIndex,
                Component.translatable(Util.makeDescriptionId("trim_material", materialKey.location())).withStyle(style),
                overrideArmorMaterials
        );
        context.register(materialKey, trimMaterial);
    }

}
