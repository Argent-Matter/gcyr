package argent_matter.gcyr.integration.kjs.builders;

import argent_matter.gcyr.api.block.impl.SimpleFuelTankProperties;
import argent_matter.gcyr.common.block.FuelTankBlock;
import argent_matter.gcyr.common.data.GCYRBlocks;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

@Accessors(chain = true, fluent = true)
public class FuelTankBlockBuilder extends BlockBuilder {
    @Setter
    public transient int tier;
    @Setter
    public transient long fuelStorage;
    @Setter
    public transient String typeId = "";

    public FuelTankBlockBuilder(ResourceLocation i) {
        super(i);
    }

    @Override
    public Block createObject() {
        SimpleFuelTankProperties fuelTankProperties = new SimpleFuelTankProperties(this.typeId, tier, fuelStorage);
        FuelTankBlock result = new FuelTankBlock(this.createProperties(), fuelTankProperties);
        GCYRBlocks.ALL_FUEL_TANKS.put(fuelTankProperties, () -> result);
        return result;
    }
}
