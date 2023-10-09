package argent_matter.gcys.forge;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.GregicalitySpaceClient;
import argent_matter.gcys.common.data.GcysKeyMappings;
import argent_matter.gcys.common.data.forge.GcysBiomesImpl;
import argent_matter.gcys.common.data.forge.GcysDimensionTypesImpl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GregicalitySpace.MOD_ID)
public class GregicalitySpaceForge {
    public GregicalitySpaceForge() {
        GregicalitySpace.init();
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        GcysBiomesImpl.register(bus);
        GcysDimensionTypesImpl.register(bus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> GregicalitySpaceClient::init);
    }
}