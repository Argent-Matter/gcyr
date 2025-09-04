package argent_matter.gcyr.api.registries;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.api.space.satellite.SatelliteType;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.BaseMappedRegistry;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class GCYRRegistries {
    public static final GTRegistrate REGISTRATE = GTRegistrate.create(GCYR.MOD_ID);
    static {
        REGISTRATE.defaultCreativeTab((ResourceKey<CreativeModeTab>) null);
    }

    public static final ResourceKey<Registry<SatelliteType<?>>> SATELLITE_KEY = ResourceKey.createRegistryKey(GCYR.id("satellite"));

    public static final Registry<SatelliteType<?>> SATELLITES = new RegistryBuilder<>(SATELLITE_KEY).sync(true).create();
}
