package argent_matter.gcyr.common.block;

import argent_matter.gcyr.api.block.IFuelTankProperties;
import argent_matter.gcyr.api.block.IRocketPart;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import lombok.Getter;
import net.minecraft.world.level.block.RotatedPillarBlock;

public class FuelTankBlock extends RotatedPillarBlock implements IRocketPart {

    @Getter
    private final IFuelTankProperties tankProperties;

    public FuelTankBlock(Properties properties, IFuelTankProperties tankProperties) {
        super(properties);
        this.tankProperties = tankProperties;
    }

    @Override
    public int getTier() {
        return this.tankProperties.getTier();
    }

    public enum FuelTankProperties implements IFuelTankProperties {
        BASIC("basic", 1, 5 * FluidHelper.getBucket()),
        ADVANCED("advanced", 2, 7 * FluidHelper.getBucket()),
        ELITE("elite", 3, 12 * FluidHelper.getBucket()),
        ;

        @Getter
        private final String serializedName;
        @Getter
        private final int tier;
        @Getter
        private final long fuelStorage;

        FuelTankProperties(String serializedName, int tier, long fuelStorage) {
            this.tier = tier;
            this.fuelStorage = fuelStorage;
            this.serializedName = serializedName;
        }
    }
}
