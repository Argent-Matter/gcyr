package argent_matter.gcyr.common.entity;

public enum RocketFlightStage {

    IDLE,
    LAUNCH,
    TRANSFER,
    LANDING;

    public static RocketFlightStage fromId(int id) {
        RocketFlightStage[] values = values();
        return id >= 0 && id < values.length ? values[id] : IDLE;
    }
}
