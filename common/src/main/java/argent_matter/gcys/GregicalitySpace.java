package argent_matter.gcys;

import argent_matter.gcys.api.registries.GcysRegistries;
import argent_matter.gcys.common.data.*;
import argent_matter.gcys.config.GcysConfig;
import argent_matter.gcys.data.GcysDatagen;
import com.gregtechceu.gtceu.data.GregTechDatagen;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GregicalitySpace {
	public static final String
			MOD_ID = "gcys",
			NAME = "Gregicality Space";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	public static void init() {
		GcysConfig.init();

		GcysSatellites.init();
		GcysBlocks.init();
		GcysItems.init();
		//GcysEntityTypes.init();

		GcysDatagen.init();

		GcysRegistries.REGISTRATE.registerRegistrate();
		GcysDimensionTypes.init();
		GcysBiomes.init();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}