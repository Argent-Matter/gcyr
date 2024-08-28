package argent_matter.gcyr;

import argent_matter.gcyr.api.gui.factory.EntityUIFactory;
import argent_matter.gcyr.api.registries.GCYRRegistries;
import argent_matter.gcyr.common.data.*;
import argent_matter.gcyr.common.gui.EntityOxygenHUD;
import argent_matter.gcyr.config.GCYRConfig;
import argent_matter.gcyr.data.GCYRDatagen;
import argent_matter.gcyr.data.loader.PlanetResources;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.material.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.material.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.material.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.material.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.lowdragmc.lowdraglib.gui.factory.UIFactory;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(GCYR.MOD_ID)
public class GCYR {
	public static final String
			MOD_ID = "gcyr",
			NAME = "Gregicality Rocketry";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static MaterialRegistry MATERIAL_REGISTRY;

	public static IEventBus modBus;

	public GCYR(IEventBus bus) {
		GCYR.init();
		modBus = bus;
		bus.register(this);

		GCYRDimensionTypes.register(bus);
		GCYREntityDataSerializers.register(bus);
		GCYRDataComponents.register(bus);

		GCYRVanillaRecipeTypes.RECIPE_TYPE_DEFERRED_REGISTER.register(bus);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			GCYRClient.init();
		}
	}

	public static void init() {
		GCYRConfig.init();
		GCYRNetworking.init();
		UIFactory.register(EntityUIFactory.INSTANCE);

		GCYRSatellites.init();
		GCYRCreativeModeTabs.init();
		GCYREntities.init();
		GCYRBlocks.init();
		GCYRItems.init();
		GCYRMenus.init();

		GCYRDatagen.init();

		GCYRRegistries.REGISTRATE.registerRegistrate(modBus);
		GCYRDimensionTypes.init();
		GCYRParticles.init();
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	@SubscribeEvent
	public void registerGuiOverlays(RegisterGuiLayersEvent event) {
		event.registerBelowAll(GCYR.id("oxygen_tank"), new EntityOxygenHUD());
	}


	@SubscribeEvent
	public void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
		event.registerReloadListener(PlanetResources.INSTANCE);
	}

	@SubscribeEvent
	public void registerMaterialRegistry(MaterialRegistryEvent event) {
		MATERIAL_REGISTRY = GTCEuAPI.materialManager.createRegistry(GCYR.MOD_ID);
	}

	@SubscribeEvent
	public void registerMaterials(MaterialEvent event) {
		GCYRMaterials.init();
	}

	@SubscribeEvent
	public void modifyMaterials(PostMaterialEvent event) {
		GCYRMaterials.modifyMaterials();
	}

	@SubscribeEvent
	public void gtRegister(GTCEuAPI.RegisterEvent event) {
		event.register(GTRegistries.RECIPE_TYPES, GCYRRecipeTypes::init);
		event.register(GTRegistries.RECIPE_CONDITIONS, GCYRRecipeConditions::init);
		event.register(GTRegistries.MACHINES, GCYRMachines::init);
		event.register(GTRegistries.DIMENSION_MARKERS, GCYRDimensionMarkers::init);
	}
}
