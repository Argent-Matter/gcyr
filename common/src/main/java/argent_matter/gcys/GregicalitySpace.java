package argent_matter.gcys;

import argent_matter.gcys.api.gui.factory.EntityUIFactory;
import argent_matter.gcys.api.registries.GcysRegistries;
import argent_matter.gcys.common.data.*;
import argent_matter.gcys.common.networking.c2s.PacketLaunchRocket;
import argent_matter.gcys.config.GcysConfig;
import argent_matter.gcys.data.GcysDatagen;
import com.lowdragmc.lowdraglib.gui.factory.UIFactory;
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
		GcysNetworking.init();
		UIFactory.register(EntityUIFactory.INSTANCE);

		GcysRecipeConditions.init();
		GcysSatellites.init();
		GcysEntityDataSerializers.init();
		GcysEntities.init();
		GcysBlocks.init();
		GcysItems.init();
		GcysMenus.init();

		GcysDatagen.init();

		GcysRegistries.REGISTRATE.registerRegistrate();
		GcysDimensionTypes.init();
		GcysBiomes.init();
		GcysParticles.init();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static void onKeyPressed(int key, int action, int modifiers) {
		/* use GUI instead, this was just another annoying useless keybind
		if (GcysKeyMappings.START_ROCKET.isDown()) {
			GcysNetworking.NETWORK.sendToServer(new PacketLaunchRocket());
		}
		 */
	}
}