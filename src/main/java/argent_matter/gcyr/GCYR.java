package argent_matter.gcyr;

import argent_matter.gcyr.api.gui.factory.EntityUIFactory;
import argent_matter.gcyr.api.registries.GCYRRegistries;
import argent_matter.gcyr.common.data.*;
import argent_matter.gcyr.common.gui.EntityOxygenHUD;
import argent_matter.gcyr.config.GCYRConfig;
import argent_matter.gcyr.data.GCYRDatagen;
import argent_matter.gcyr.data.loader.PlanetResources;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.DimensionMarker;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
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

@Mod(GCYR.MOD_ID)
public class GCYR {
	public static final String
			MOD_ID = "gcyr",
			NAME = "Gregicality Rocketry";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static MaterialRegistry MATERIAL_REGISTRY;

	public GCYR() {
		GCYR.init();
		var bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.register(this);

		bus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
		bus.addGenericListener(Class.class, this::registerRecipeConditions);
		bus.addGenericListener(MachineDefinition.class, this::registerMachines);
		bus.addGenericListener(DimensionMarker.class, this::registerDimensionMarkers);
		GCYRDimensionTypes.register(bus);

		GCYRVanillaRecipeTypes.RECIPE_TYPE_DEFERRED_REGISTER.register(bus);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> GCYRClient::init);
	}

	public static void init() {
		GCYRConfig.init();
		GCYRNetworking.init();
		UIFactory.register(EntityUIFactory.INSTANCE);

		GCYRSatellites.init();
		GCYREntityDataSerializers.init();
		GCYRCreativeModeTabs.init();
		GCYREntities.init();
		GCYRBlocks.init();
		GCYRItems.init();
		GCYRMenus.init();

		GCYRDatagen.init();

		GCYRRegistries.REGISTRATE.registerRegistrate();
		GCYRDimensionTypes.init();
		GCYRParticles.init();
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

	public void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
		GCYRRecipeTypes.init();
	}

	public void registerRecipeConditions(GTCEuAPI.RegisterEvent<String, Class<? extends RecipeCondition>> event) {
		GCYRRecipeConditions.init();
	}

	public void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
		GCYRMachines.init();
	}

	public void registerDimensionMarkers(GTCEuAPI.RegisterEvent<ResourceLocation, DimensionMarker> event) {
		GCYRDimensionMarkers.init();
	}
}
