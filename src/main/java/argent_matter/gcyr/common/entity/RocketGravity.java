package argent_matter.gcyr.common.entity;

import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.data.loader.PlanetData;

import net.minecraft.world.level.Level;

public final class RocketGravity {

    // minecraft acceleration due to gravity in blocks/tick
    public static final double DEFAULT_GRAVITY = 0.08D;
    // orbit dimensions use one tenth of Earth gravity
    public static final double ORBIT_GRAVITY = DEFAULT_GRAVITY * 0.1D;
    // value of 'g' on earth in meters/sec; used to normalize realistic
    // m/sec values in json files to blocks/tick values in minecraft
    public static final double EARTH_GRAVITY = 9.806D;
    // maximum speed the rocket will fall at in blocks/tick
    public static final double MAX_DESCENT_SPEED = 5.0D;
    // slowest descent speed when accelerating the rocket in blocks/tick
    public static final double MIN_DESCENT_SPEED = 0.2D;
    // if the rocket hits the ground going > CRASH_SPEED and explosions
    // are on, the it explodes; CRASH_SPEED is in blocks/tick
    public static final double CRASH_SPEED = 1.75D;

    private RocketGravity() {}

    public static double get(Level level) {
        if (PlanetData.isOrbitLevel(level.dimension())) return ORBIT_GRAVITY;
        Planet planet = PlanetData.getPlanetFromLevel(level.dimension()).orElse(null);
        if (planet == null) return DEFAULT_GRAVITY;
        return DEFAULT_GRAVITY * (planet.gravity() / EARTH_GRAVITY);
    }

    public static double getCrashSpeed() {
        return CRASH_SPEED;
    }
}
