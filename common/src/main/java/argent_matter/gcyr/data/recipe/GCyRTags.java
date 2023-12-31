package argent_matter.gcyr.data.recipe;

import argent_matter.gcyr.api.data.tag.GCyRTagUtil;
import com.gregtechceu.gtceu.api.data.tag.TagUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class GCyRTags {
    // Item
    public static final TagKey<Item> SATELLITES = GCyRTagUtil.createModItemTag("satellites");
    public static final TagKey<Item> OXYGENATED_ARMOR = GCyRTagUtil.createModItemTag("oxygenated_armor");
    public static final TagKey<Item> FREEZE_RESISTANT = GCyRTagUtil.createModItemTag("freeze_resistant");
    public static final TagKey<Item> HEAT_RESISTANT = GCyRTagUtil.createModItemTag("heat_resistant");

    // Block
    public static final TagKey<Block> MOON_ORE_REPLACEABLES = TagUtil.createBlockTag("moon_ore_replaceables");
    public static final TagKey<Block> MARS_ORE_REPLACEABLES = TagUtil.createBlockTag("mars_ore_replaceables");
    public static final TagKey<Block> INFINIBURN_SPACE = GCyRTagUtil.createModBlockTag("infiniburn_space");
    public static final TagKey<Block> BLOCKS_FLOOD_FILL = GCyRTagUtil.createModBlockTag("blocks_flood_fill");
    public static final TagKey<Block> PASSES_FLOOD_FILL = GCyRTagUtil.createModBlockTag("passes_flood_fill");

    // Fluid
    public static final TagKey<Fluid> VEHICLE_FUELS = GCyRTagUtil.createModFluidTag("vehicle_fuels");

    // Biome
    public static final TagKey<Biome> IS_SPACE = GCyRTagUtil.createModTag(Registries.BIOME, "is_space");
    public static final TagKey<Biome> IS_MOON = GCyRTagUtil.createModTag(Registries.BIOME, "is_moon");
    public static final TagKey<Biome> IS_MERCURY = GCyRTagUtil.createModTag(Registries.BIOME, "is_mercury");
    public static final TagKey<Biome> IS_MARS = GCyRTagUtil.createModTag(Registries.BIOME, "is_mars");
    public static final TagKey<Biome> IS_VENUS = GCyRTagUtil.createModTag(Registries.BIOME, "is_venus");

    // Entity
    public static final TagKey<EntityType<?>> IGNORE_OXYGEN = GCyRTagUtil.createModTag(Registries.ENTITY_TYPE, "ignore_oxygen");
    public static final TagKey<EntityType<?>> IGNORE_TEMPERATURE = GCyRTagUtil.createModTag(Registries.ENTITY_TYPE, "ignore_temperature");
}
