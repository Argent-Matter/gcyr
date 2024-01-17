package argent_matter.gcyr.api.block.impl;

import argent_matter.gcyr.api.block.IRocketMotorType;
import lombok.Getter;

public class SimpleRocketMotorType implements IRocketMotorType {

    @Getter
    private final String serializedName;
    @Getter
    private final int tier, maxCarryWeight, motorCount;

    public SimpleRocketMotorType(String name, int tier, int maxCarryWeight, int motorCount) {
        this.tier = tier;
        this.maxCarryWeight = maxCarryWeight;
        this.motorCount = motorCount;
        this.serializedName = name;
    }
}
