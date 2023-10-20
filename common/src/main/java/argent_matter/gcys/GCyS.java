package argent_matter.gcys;

import argent_matter.gcys.api.gui.factory.EntityUIFactory;
import argent_matter.gcys.api.registries.GcysRegistries;
import argent_matter.gcys.common.data.*;
import argent_matter.gcys.config.GcysConfig;
import argent_matter.gcys.data.GCySDatagen;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.lowdragmc.lowdraglib.gui.factory.UIFactory;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GCyS {
	public static final String
			MOD_ID = "gcys",
			NAME = "Gregicality Space";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	public static void init() {
		ConfigHolder.init(); // Forcefully init GT config because fabric doesn't allow dependents to load after dependencies
		GcysConfig.init();
		GCySNetworking.init();
		UIFactory.register(EntityUIFactory.INSTANCE);

		GCySRecipeConditions.init();
		GCySSatellites.init();
		GCySEntityDataSerializers.init();
		GCySCreativeModeTabs.init();
		GCySEntities.init();
		GCySBlocks.init();
		GCySRecipeTypes.init();
		GCySMachines.init();
		GCySItems.init();
		GCySMenus.init();

		GCySDatagen.init();

		GcysRegistries.REGISTRATE.registerRegistrate();
		GCySDimensionTypes.init();
		GCySParticles.init();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static void onKeyPressed(int key, int action, int modifiers) {
		/* use GUI instead, this was just another annoying useless keybind
		if (GCySKeyMappings.START_ROCKET.isDown()) {
			GCySNetworking.NETWORK.sendToServer(new PacketLaunchRocket());
		}
		 */
	}
}