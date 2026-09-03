package argent_matter.gcyr.common.entity;

import net.minecraft.util.StringRepresentable;

public enum RocketFlightStage implements StringRepresentable {

    IDLE("idle"),
    LAUNCH("launch"),
    TRANSFER("transfer"),
    LANDING("landing");

    private static final RocketFlightStage[] VALUES = values();
    private final String serializedName;

    RocketFlightStage(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }

    public static RocketFlightStage fromSerializedName(String name) {
        for (RocketFlightStage stage : VALUES) {
            if (stage.serializedName.equals(name)) return stage;
        }
        return IDLE;
    }
}
