package argent_matter.gcyr;

import argent_matter.gcyr.api.capability.GCyRCapabilityHelper;
import argent_matter.gcyr.api.capability.IDysonSystem;
import argent_matter.gcyr.api.gui.factory.EntityUIFactory;
import argent_matter.gcyr.api.registries.GCyRRegistries;
import argent_matter.gcyr.common.data.*;
import argent_matter.gcyr.config.GCyRConfig;
import argent_matter.gcyr.data.GCyRDatagen;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.lowdragmc.lowdraglib.gui.factory.UIFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class GCyR {
	public static final String
			MOD_ID = "gcyr",
			NAME = "Gregicality Rocketry";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	public static void init() {
		ConfigHolder.init(); // Forcefully init GT config because fabric doesn't allow dependents to load after dependencies
		GCyRConfig.init();
		GCyRNetworking.init();
		UIFactory.register(EntityUIFactory.INSTANCE);

		GCyRRecipeConditions.init();
		GCyRSatellites.init();
		GCyREntityDataSerializers.init();
		GCyRCreativeModeTabs.init();
		GCyREntities.init();
		GCyRBlocks.init();
		GCyRRecipeTypes.init();
		GCyRMachines.init();
		GCyRItems.init();
		GCyRMenus.init();

		GCyRDatagen.init();

		GCyRRegistries.REGISTRATE.registerRegistrate();
		GCyRDimensionTypes.init();
		GCyRParticles.init();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static void onKeyPressed(int key, int action, int modifiers) {
		/* use GUI instead, this was just another annoying useless keybind
		if (GCyRKeyMappings.START_ROCKET.isDown()) {
			GCyRNetworking.NETWORK.sendToServer(new PacketLaunchRocket());
		}
		 */
	}

	private static final ThreadLocal<Set<IDysonSystem>> TICKED_SYSTEMS = ThreadLocal.withInitial(HashSet::new);

	public static void onLevelTick(Level ticked, boolean isStart) {
		if (!(ticked instanceof ServerLevel level)) return;

		if (isStart) {
			if (!level.dimensionType().hasCeiling()) {
				var sat = GCyRCapabilityHelper.getSatellites(level);
				if (sat != null) sat.tickSatellites();
			}

			IDysonSystem system = GCyRCapabilityHelper.getDysonSystem(level);
			if (system == null || TICKED_SYSTEMS.get().contains(system)) return;
			system.tick();
		} else {
			TICKED_SYSTEMS.get().clear();
		}
	}
}