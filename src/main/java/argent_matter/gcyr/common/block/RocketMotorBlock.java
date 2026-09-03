package argent_matter.gcyr.common.block;

import argent_matter.gcyr.api.block.IRocketMotorType;
import argent_matter.gcyr.api.block.IRocketPart;

import net.minecraft.world.level.block.Block;

import lombok.Getter;

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

        BASIC("basic", 1, 10.0, 1.0),
        ADVANCED("advanced", 2, 15.0, 1.02),
        ELITE("elite", 3, 20.0, 1.05),
        ;

        @Getter
        private final String serializedName;
        @Getter
        private final int tier;
        @Getter
        private final double thrust;
        @Getter
        private final double efficiency;

        RocketMotorType(String name, int tier, double thrust, double efficiency) {
            this.serializedName = name;
            this.tier = tier;
            this.thrust = thrust;
            this.efficiency = efficiency;
        }
    }
}
