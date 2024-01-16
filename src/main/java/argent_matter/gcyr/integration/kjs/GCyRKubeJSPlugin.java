package argent_matter.gcyr.integration.kjs;

import argent_matter.gcyr.integration.kjs.builders.FuelTankBlockBuilder;
import argent_matter.gcyr.integration.kjs.builders.RocketMotorBlockBuilder;
import com.gregtechceu.gtceu.integration.kjs.builders.block.CoilBlockBuilder;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;

public class GCyRKubeJSPlugin extends KubeJSPlugin {

    @Override
    public void init() {
        super.init();

        RegistryInfo.BLOCK.addType("gcyr:fuel_tank", FuelTankBlockBuilder.class, FuelTankBlockBuilder::new);
        RegistryInfo.BLOCK.addType("gcyr:rocket_motor", RocketMotorBlockBuilder.class, RocketMotorBlockBuilder::new);
    }
}
