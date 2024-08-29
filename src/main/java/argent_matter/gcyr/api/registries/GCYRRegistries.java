package argent_matter.gcyr.api.registries;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.api.space.satellite.SatelliteType;
import com.gregtechceu.gtceu.api.registry.GTRegistry;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

public class GCYRRegistries {
    public static final GTRegistrate REGISTRATE = GTRegistrate.create(GCYR.MOD_ID);
    static {
        REGISTRATE.defaultCreativeTab((ResourceKey<CreativeModeTab>) null);
    }

    public static final GTRegistry.RL<SatelliteType<?>> SATELLITES = new GTRegistry.RL<>(GCYR.id("satellite"));
}
