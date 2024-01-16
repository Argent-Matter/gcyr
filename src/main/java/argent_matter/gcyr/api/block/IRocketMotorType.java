package argent_matter.gcyr.api.block;

import net.minecraft.util.StringRepresentable;

public interface IRocketMotorType extends StringRepresentable {

    /**
     * This is used for what galaxies you can travel to with this rocket motor.
     *
     * @return the tier of the fuel tank
     */
    int getTier();

    /**
     * @return how much weight this rocket motor can carry at maximum.
     */
    int getMaxCarryWeight();

    /**
     * @return how many rocket motors this type counts as.
     */
    int getMotorCount();
}
