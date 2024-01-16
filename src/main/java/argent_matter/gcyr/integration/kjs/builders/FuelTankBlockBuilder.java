package argent_matter.gcyr.integration.kjs.builders;

import argent_matter.gcyr.api.block.impl.SimpleFuelTankProperties;
import argent_matter.gcyr.api.block.impl.SimpleRocketMotorType;
import argent_matter.gcyr.common.block.FuelTankBlock;
import argent_matter.gcyr.common.block.RocketMotorBlock;
import argent_matter.gcyr.common.data.GCyRBlocks;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

@Accessors(chain = true, fluent = true)
public class FuelTankBlockBuilder extends BlockBuilder {
    @Getter
    public transient int tier, maxCarryWeight, motorCount;
    @Setter
    public transient String typeId = "";

    public FuelTankBlockBuilder(ResourceLocation i) {
        super(i);
    }

    @Override
    public Block createObject() {
        SimpleRocketMotorType fuelTankProperties = new SimpleRocketMotorType(this.typeId, tier, maxCarryWeight, motorCount);
        RocketMotorBlock result = new RocketMotorBlock(this.createProperties(), fuelTankProperties);
        GCyRBlocks.ALL_ROCKET_MOTORS.put(fuelTankProperties, () -> result);
        return result;
    }
}
