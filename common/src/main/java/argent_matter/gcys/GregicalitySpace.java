package argent_matter.gcys;

import argent_matter.gcys.common.data.*;
import argent_matter.gcys.config.GcysConfig;
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
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}