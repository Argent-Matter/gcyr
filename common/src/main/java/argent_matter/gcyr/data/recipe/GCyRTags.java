package argent_matter.gcyr.data.recipe;

import argent_matter.gcyr.api.data.tag.GCyRTagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class GCyRTags {

    public static final TagKey<Item> SATELLITES = GCyRTagUtil.createModItemTag("satellites");
    public static final TagKey<Item> OXYGENATED_ARMOR = GCyRTagUtil.createModItemTag("oxygenated_armor");
    public static final TagKey<Item> FREEZE_RESISTANT = GCyRTagUtil.createModItemTag("freeze_resistant");
    public static final TagKey<Item> HEAT_RESISTANT = GCyRTagUtil.createModItemTag("heat_resistant");

    public static final TagKey<Block> INFINIBURN_SPACE = GCyRTagUtil.createModBlockTag("infiniburn_space");
    public static final TagKey<Block> BLOCKS_FLOOD_FILL = GCyRTagUtil.createModBlockTag("blocks_flood_fill");
    public static final TagKey<Block> PASSES_FLOOD_FILL = GCyRTagUtil.createModBlockTag("passes_flood_fill");

    public static final TagKey<Fluid> VEHICLE_FUELS = GCyRTagUtil.createModFluidTag("vehicle_fuels");
}
