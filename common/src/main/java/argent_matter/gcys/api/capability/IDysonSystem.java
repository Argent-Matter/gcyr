package argent_matter.gcys.api.capability;


import argent_matter.gcys.common.satellite.DysonSwarmSatellite;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface IDysonSystem {
    /**
     * @return if a dyson sphere active in this dimension.
     */
    boolean isDysonSphereActive();

    /**
     * @return how many dyson swarm satellites are active in this dimension.
     */
    int activeDysonSwarmSatelliteCount();

    void addDysonSphere(BlockPos controllerPos);

    void disableDysonSphere(BlockPos controllerPos);

    void addDysonSatellite(BlockPos controllerPos, DysonSwarmSatellite satellite);

    void disableAllDysonSatellites(BlockPos controllerPos);
}
