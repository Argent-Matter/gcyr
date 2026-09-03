package argent_matter.gcyr.integration.kjs.builders;

import argent_matter.gcyr.api.block.impl.SimpleRocketMotorType;
import argent_matter.gcyr.common.block.RocketMotorBlock;
import argent_matter.gcyr.common.data.GCYRBlocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true, fluent = true)
public class RocketMotorBlockBuilder extends BlockBuilder {

    @Setter
    public transient int tier = 0;
    @Setter
    public transient double thrust = 10.0D;
    @Setter
    public transient double efficiency = 1.0D;
    @Setter
    public transient String typeId = "";

    public RocketMotorBlockBuilder(ResourceLocation i) {
        super(i);
    }

    @Override
    public Block createObject() {
        SimpleRocketMotorType motorType = new SimpleRocketMotorType(this.typeId, tier, thrust, efficiency);
        RocketMotorBlock result = new RocketMotorBlock(this.createProperties(), motorType);
        GCYRBlocks.ALL_ROCKET_MOTORS.put(motorType, () -> result);
        return result;
    }
}
