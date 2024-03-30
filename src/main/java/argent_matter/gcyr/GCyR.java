package argent_matter.gcyr;

import argent_matter.gcyr.api.gui.factory.EntityUIFactory;
import argent_matter.gcyr.api.registries.GCyRRegistries;
import argent_matter.gcyr.common.data.*;
import argent_matter.gcyr.common.gui.EntityOxygenHUD;
import argent_matter.gcyr.config.GCyRConfig;
import argent_matter.gcyr.data.GCyRDatagen;
import argent_matter.gcyr.data.loader.PlanetResources;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.lowdragmc.lowdraglib.gui.factory.UIFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(GCyR.MOD_ID)
public class GCyR {
	public static final String
			MOD_ID = "gcyr",
			NAME = "Gregicality Rocketry";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static MaterialRegistry MATERIAL_REGISTRY;

	public GCyR() {
		GCyR.init();
		var bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.register(this);

		bus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
		bus.addGenericListener(Class.class, this::registerRecipeConditions);
		bus.addGenericListener(MachineDefinition.class, this::registerMachines);
		GCyRDimensionTypes.register(bus);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> GCyRClient::init);
	}

	public static void init() {
		ConfigHolder.init(); // Forcefully init GT config because fabric doesn't allow dependents to load after dependencies
		GCyRConfig.init();
		GCyRNetworking.init();
		UIFactory.register(EntityUIFactory.INSTANCE);

		//GCyRSatellites.init();
		GCyREntityDataSerializers.init();
		GCyRCreativeModeTabs.init();
		GCyREntities.init();
		GCyRBlocks.init();
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

	@SubscribeEvent
	public void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
		event.registerBelowAll("oxygen_tank", new EntityOxygenHUD());
	}


	@SubscribeEvent
	public void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
		event.registerReloadListener(PlanetResources.INSTANCE);
	}

	@SubscribeEvent
	public void registerMaterialRegistry(MaterialRegistryEvent event) {
		MATERIAL_REGISTRY = GTCEuAPI.materialManager.createRegistry(GCyR.MOD_ID);
	}

	@SubscribeEvent
	public void registerMaterials(MaterialEvent event) {
		GCyRMaterials.init();
	}

	@SubscribeEvent
	public void modifyMaterials(PostMaterialEvent event) {
		GCyRMaterials.modifyMaterials();
	}

	@SubscribeEvent
	public void registerRecipeConditions(GTCEuAPI.RegisterEvent<String, Class<? extends RecipeCondition>> event) {
		GCyRRecipeConditions.init();
	}

	@SubscribeEvent
	public void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
		GCyRRecipeTypes.init();
	}

	@SubscribeEvent
	public void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
		GCyRMachines.init();
	}
}