package argent_matter.gcys.common.data.forge;

import argent_matter.gcys.GCyS;
import argent_matter.gcys.common.data.GCySBiomes;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GCySBiomesImpl {
    private static final DeferredRegister<Biome> BIOME_REGISTER = DeferredRegister.create(ForgeRegistries.BIOMES, GCyS.MOD_ID);

    public static void register(IEventBus bus) {
        BIOME_REGISTER.register(bus);
    }

    public static void init() {
        BIOME_REGISTER.register(GCySBiomes.SPACE.location().getPath(), GCySBiomes::space);
    }
}
