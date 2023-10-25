package argent_matter.gcyr.common.data.forge;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.data.GCyRBiomes;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GCyRBiomesImpl {
    private static final DeferredRegister<Biome> BIOME_REGISTER = DeferredRegister.create(ForgeRegistries.BIOMES, GCyR.MOD_ID);

    public static void register(IEventBus bus) {
        BIOME_REGISTER.register(bus);
    }

    public static void init() {
        BIOME_REGISTER.register(GCyRBiomes.SPACE.location().getPath(), GCyRBiomes::space);
    }
}
