package argent_matter.gcyr.integration.kjs;

import argent_matter.gcyr.integration.kjs.builders.FuelTankBlockBuilder;
import argent_matter.gcyr.integration.kjs.builders.RocketMotorBlockBuilder;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;

public class GCYRKubeJSPlugin implements KubeJSPlugin {

    @Override
    public void init() {
        super.init();


        // TODO
        // RegistryInfo.BLOCK.addType("gcyr:fuel_tank", FuelTankBlockBuilder.class, FuelTankBlockBuilder::new);
        //RegistryInfo.BLOCK.addType("gcyr:rocket_motor", RocketMotorBlockBuilder.class, RocketMotorBlockBuilder::new);
    }
}
