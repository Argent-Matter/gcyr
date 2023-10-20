package argent_matter.gcys.data.recipe;

import argent_matter.gcys.api.data.tag.GcysTagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class GCySTags {

    public static final TagKey<Item> SATELLITES = GcysTagUtil.createModItemTag("satellites");
    public static final TagKey<Item> OXYGENATED_ARMOR = GcysTagUtil.createModItemTag("oxygenated_armor");
    public static final TagKey<Item> FREEZE_RESISTANT = GcysTagUtil.createModItemTag("freeze_resistant");
    public static final TagKey<Item> HEAT_RESISTANT = GcysTagUtil.createModItemTag("heat_resistant");

    public static final TagKey<Block> INFINIBURN_SPACE = GcysTagUtil.createModBlockTag("infiniburn_space");
    public static final TagKey<Block> BLOCKS_FLOOD_FILL = GcysTagUtil.createModBlockTag("blocks_flood_fill");
    public static final TagKey<Block> PASSES_FLOOD_FILL = GcysTagUtil.createModBlockTag("passes_flood_fill");

    public static final TagKey<Fluid> VEHICLE_FUELS = GcysTagUtil.createModFluidTag("vehicle_fuels");
}
