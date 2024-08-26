package argent_matter.gcyr.common.item.armor.trim;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.common.data.GCYRItems;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;

public class GCYRTrimPatterns {

    public static final ResourceKey<TrimPattern> SPACE = ResourceKey.create(Registries.TRIM_PATTERN, GCYR.id("space"));

    public static void bootstrap(BootstapContext<TrimPattern> ctx) {
        register(ctx, GCYRItems.SPACE_UPGRADE_SMITHING_TEMPLATE.get(), SPACE);
    }

    private static void register(BootstapContext<TrimPattern> context, Item templateItem, ResourceKey<TrimPattern> trimPatternKey) {
        TrimPattern trimPattern = new TrimPattern(
                trimPatternKey.location(),
                BuiltInRegistries.ITEM.wrapAsHolder(templateItem),
                Component.translatable(Util.makeDescriptionId("trim_pattern", trimPatternKey.location()))
        );
        context.register(trimPatternKey, trimPattern);
    }

}
