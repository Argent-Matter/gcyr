package argent_matter.gcyr.api.block.impl;

import argent_matter.gcyr.api.block.IRocketMotorType;

import lombok.Getter;

public class SimpleRocketMotorType implements IRocketMotorType {

    @Getter
    private final String serializedName;
    @Getter
    private final int tier;
    @Getter
    private final double thrust;

    public SimpleRocketMotorType(String name, int tier, double thrust) {
        this.tier = tier;
        this.thrust = thrust;
        this.serializedName = name;
    }
}
