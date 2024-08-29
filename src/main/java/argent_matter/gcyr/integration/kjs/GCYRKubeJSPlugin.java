package argent_matter.gcyr.integration.kjs;

import argent_matter.gcyr.integration.kjs.builders.FuelTankBlockBuilder;
import argent_matter.gcyr.integration.kjs.builders.RocketMotorBlockBuilder;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import net.minecraft.core.registries.Registries;

public class GCYRKubeJSPlugin implements KubeJSPlugin {

    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(Registries.BLOCK, reg -> {
            reg.add("gcyr:fuel_tank", FuelTankBlockBuilder.class, FuelTankBlockBuilder::new);
            reg.add("gcyr:rocket_motor", RocketMotorBlockBuilder.class, RocketMotorBlockBuilder::new);
        });
    }
}
