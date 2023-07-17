package argent_matter.gcys.common.data.forge;

import argent_matter.gcys.common.data.GcysBiomes;
import com.gregtechceu.gtceu.GTCEu;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class GcysBiomesImpl {
    private static final DeferredRegister<Biome> BIOME_REGISTER = DeferredRegister.create(ForgeRegistries.BIOMES, GTCEu.MOD_ID);

    public static void register(IEventBus bus) {
        BIOME_REGISTER.register(bus);
    }

    public static void init() {
        BIOME_REGISTER.register(GcysBiomes.SPACE.location().getPath(), GcysBiomes::space);
    }
}
