package argent_matter.gcyr.api.block;

import net.minecraft.util.StringRepresentable;

public interface IRocketMotorType extends StringRepresentable {

    /**
     * Tier of the motor. The rocket's tier is the minimum tier of its rocket parts.
     * The tier of the rocket determines what worlds you can travel to.
     *
     * @return the tier of the motor
     */
    int getTier();

    /**
     * The thrust produced by one motor in Newtons.
     */
    double getThrust();

    /**
     * Fuel efficiency for this motor. Fuel energy is multiplied by this value, so
     * values > 1.0 increase fuel efficiency and values < 1.0 reduce it from baseline.
     */
    double getEfficiency();
}
