package screret.gcys.forge;

import screret.gcys.GregicalitySpace;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GregicalitySpace.MOD_ID)
public class GregicalitySpaceForge {
    public GregicalitySpaceForge() {
        GregicalitySpace.init();
    }
}