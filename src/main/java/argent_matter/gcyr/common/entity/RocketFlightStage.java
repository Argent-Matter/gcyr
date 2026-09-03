package argent_matter.gcyr.common.entity;

public enum RocketFlightStage {

    IDLE,
    LAUNCH,
    TRANSFER,
    LANDING;

    private static final RocketFlightStage[] VALUES = values();

    public static RocketFlightStage fromId(int id) {
        return id >= 0 && id < VALUES.length ? VALUES[id] : IDLE;
    }
}
