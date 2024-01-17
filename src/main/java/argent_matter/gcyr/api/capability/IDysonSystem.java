package argent_matter.gcyr.api.capability;


import argent_matter.gcyr.api.space.dyson.DysonSphere;
import argent_matter.gcyr.common.satellite.DysonSwarmSatellite;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface IDysonSystem {
    /**
     * @return this solar system's active dyson sphere, or null if none
     */
    @Nullable
    DysonSphere activeDysonSphere();

    boolean isDysonSphereActive();

    /**
     * @return how many dyson swarm satellites are active in this dimension.
     */
    int activeDysonSwarmSatelliteCount();

    void addDysonSphere(BlockPos controllerPos);

    void disableDysonSphere(BlockPos controllerPos);

    void addDysonSatellite(BlockPos controllerPos, DysonSwarmSatellite satellite);

    void disableAllDysonSatellites(BlockPos controllerPos);

    void tick();

    void setChanged();
}
