package argent_matter.gcys;

import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.api.capability.IDysonSystem;
import argent_matter.gcys.api.gui.factory.EntityUIFactory;
import argent_matter.gcys.api.registries.GcysRegistries;
import argent_matter.gcys.api.space.dyson.DysonSystemSavedData;
import argent_matter.gcys.common.data.*;
import argent_matter.gcys.config.GcysConfig;
import argent_matter.gcys.data.GCySDatagen;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.lowdragmc.lowdraglib.gui.factory.UIFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

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

	private static final ThreadLocal<Set<IDysonSystem>> TICKED_SYSTEMS = ThreadLocal.withInitial(HashSet::new);

	public static void onLevelTick(Level ticked, boolean isStart) {
		if (!(ticked instanceof ServerLevel level)) return;

		if (isStart) {
			if (!level.dimensionType().hasCeiling()) {
				var sat = GcysCapabilityHelper.getSatellites(level);
				if (sat != null) sat.tickSatellites();
			}

			IDysonSystem system = DysonSystemSavedData.getOrCreate(level);
			if (system == null || TICKED_SYSTEMS.get().contains(system)) return;
			system.tick();
		} else {
			TICKED_SYSTEMS.get().clear();
		}
	}
}