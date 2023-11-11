package argent_matter.gcyr.common.block;

import argent_matter.gcyr.api.block.IRocketMotorType;
import argent_matter.gcyr.api.block.IRocketPart;
import lombok.Getter;
import net.minecraft.world.level.block.Block;

public class RocketMotorBlock extends Block implements IRocketPart {

    @Getter
    private final IRocketMotorType motorType;

    public RocketMotorBlock(Properties properties, IRocketMotorType motorType) {
        super(properties);
        this.motorType = motorType;
    }

    @Override
    public int getTier() {
        return this.motorType.getTier();
    }

    public enum RocketMotorType implements IRocketMotorType {
        BASIC("basic", 1, 25, 1),
        ADVANCED("advanced", 2, 50, 2),
        ELITE("elite", 3, 75, 3),
        ;

        @Getter
        private final String serializedName;
        @Getter
        private final int tier, maxCarryWeight, motorCount;

        RocketMotorType(String name, int tier, int maxCarryWeight, int motorCount) {
            this.serializedName = name;
            this.tier = tier;
            this.maxCarryWeight = maxCarryWeight;
            this.motorCount = motorCount;
        }
    }
}
