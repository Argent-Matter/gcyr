package argent_matter.gcyr.integration.kjs.builders;

import argent_matter.gcyr.api.block.impl.SimpleRocketMotorType;
import argent_matter.gcyr.common.block.RocketMotorBlock;
import argent_matter.gcyr.common.data.GCYRBlocks;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

@Accessors(chain = true, fluent = true)
public class RocketMotorBlockBuilder extends BlockBuilder {
    @Setter
    public transient int tier = 0;
    @Setter
    public transient int maxCarryWeight = 0;
    @Setter
    public transient int motorCount = 0;
    @Setter
    public transient String typeId = "";

    public RocketMotorBlockBuilder(ResourceLocation i) {
        super(i);
    }

    @Override
    public Block createObject() {
        SimpleRocketMotorType fuelTankProperties = new SimpleRocketMotorType(this.typeId, tier, maxCarryWeight, motorCount);
        RocketMotorBlock result = new RocketMotorBlock(this.createProperties(), fuelTankProperties);
        GCYRBlocks.ALL_ROCKET_MOTORS.put(fuelTankProperties, () -> result);
        return result;
    }
}
