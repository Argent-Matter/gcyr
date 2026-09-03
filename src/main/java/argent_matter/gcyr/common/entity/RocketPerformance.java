package argent_matter.gcyr.common.entity;

import argent_matter.gcyr.api.block.IRocketMotorType;
import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.data.loader.PlanetData;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public final class RocketPerformance {

    private static final double BASELINE_FUEL_EFFICIENCY = 1.0D;
    private static final double BASELINE_FUEL_ENERGY = 13.0D;
    private static final double LAUNCH_ENERGY_SCALE = 7500.0D;

    private RocketPerformance() {}

    public static double motorThrust(IRocketMotorType motor) {
        return motor.getThrust();
    }

    public static double launchFuel(double mass, double thrust, double gravity, double efficiency, double energy) {
        if (energy <= 0.0 || thrust <= 0.0) return Double.POSITIVE_INFINITY;
        double demand = (mass + 1.0) * Math.max(0.01, gravity / RocketGravity.EARTH_GRAVITY) *
                LAUNCH_ENERGY_SCALE;
        return demand / (thrust * Math.max(0.01, efficiency) * energy);
    }

    /**
     * Fuel volume in mB required for orbital transfer from source to destination.
     * Efficiency is a property of the rocket motors, energy is the specific energy of the fuel.
     */
    public static double transferFuel(@Nullable Planet source, Planet destination, double efficiency, double energy) {
        if (energy <= 0.0) return Double.POSITIVE_INFINITY;
        double distance = transferDistance(source, destination);
        // you do not need fuel to go nowhere
        if (distance <= 0.0) return 0.0D;
        //
        double referenceFuel = -3000.0D + 15500.0D * Math.pow(distance, 0.1D) +
                8000.0D * (Math.exp(Math.max(0.0D, distance - 1.0D) / 15.0D) - 1.0D);
        return Math.max(0.0D, referenceFuel) * (BASELINE_FUEL_EFFICIENCY * BASELINE_FUEL_ENERGY) /
                Math.max(0.01D, efficiency * energy);
    }

    /**
     * Returns the normalized distance between source and destination.
     * For source/dest that orbit different bodies, the distance between the parent
     * bodies is used.
     */
    public static double transferDistance(@Nullable Planet source, Planet destination) {
        if (source == null) return destination.distanceFromParent();

        ResourceKey<Level> sourceParent = source.parentWorld();
        ResourceKey<Level> destinationParent = destination.parentWorld();

        // A moon and its parent are separated by the moon's orbit radius.
        if (source.level().equals(destinationParent)) return destination.distanceFromParent();
        if (destination.level().equals(sourceParent)) return source.distanceFromParent();

        // Bodies sharing a parent use the difference between their orbital radii.
        // This basically assumes planetary alignment, so eg. Jupiter at 5.2 AU is 4.2 AU
        // from Earth/OW, which is 1 AU from Sol, their shared parent.
        if (java.util.Objects.equals(sourceParent, destinationParent)) {
            return Math.abs(source.distanceFromParent() - destination.distanceFromParent());
        }

        // For bodies with different parents, use the parent distance
        Planet sourceOrbit = parentOrSelf(source);
        Planet destinationOrbit = parentOrSelf(destination);
        return Math.abs(sourceOrbit.distanceFromParent() - destinationOrbit.distanceFromParent());
    }

    @Nullable
    private static Planet parentOrSelf(Planet planet) {
        ResourceKey<Level> parent = planet.parentWorld();
        return parent == null ? planet : PlanetData.getPlanetFromLevelOrOrbit(parent).orElse(planet);
    }

    public static double landingFuel(double mass, double thrust, double gravity, double efficiency, double energy,
                                     double brakingDemand) {
        if (energy <= 0.0 || thrust <= 0.0) return Double.POSITIVE_INFINITY;
        return Math.max(0.0, brakingDemand) * (mass + 1.0) * Math.max(0.01, gravity / RocketGravity.EARTH_GRAVITY) /
                (thrust * Math.max(0.01, efficiency) * energy);
    }
}
