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
    @Getter
    private final double efficiency;

    public SimpleRocketMotorType(String name, int tier, double thrust, double efficiency) {
        this.tier = tier;
        this.thrust = thrust;
        this.efficiency = efficiency;
        this.serializedName = name;
    }
}
