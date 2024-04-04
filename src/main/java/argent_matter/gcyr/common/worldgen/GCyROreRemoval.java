/*package argent_matter.gcyr.common.worldgen;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GCyROreRemoval {
    private static final Set<ResourceLocation> VeinsRemoved = new HashSet<>();

    static {
        VeinsRemoved.addAll(Arrays.asList(
                GTCEu.id("banded_iron_vein"),
                GTCEu.id("manganese_vein"),
                GTCEu.id("monazite_vein"),
                GTCEu.id("redstone_vein"),
                GTCEu.id("topaz_vein"),
                GTCEu.id("bauxite_vein_end"),
                GTCEu.id("magnetite_vein_end"),
                GTCEu.id("naquadah_vein"),
                GTCEu.id("pitchblende_vein_end"),
                GTCEu.id("scheelite_vein"),
                GTCEu.id("sheldonite_vein")
        ));
    }

    public static void AAAAAAA {
        for (ResourceLocation id : VeinsRemoved) {
            GTRegistries.ORE_VEINS.remove(id);
        }
    }
}

 */