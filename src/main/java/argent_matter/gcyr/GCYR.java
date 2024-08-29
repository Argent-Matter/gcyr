package argent_matter.gcyr;

import argent_matter.gcyr.api.gui.factory.EntityUIFactory;
import argent_matter.gcyr.api.registries.GCYRRegistries;
import argent_matter.gcyr.common.data.*;
import argent_matter.gcyr.common.gui.EntityOxygenHUD;
import argent_matter.gcyr.common.item.armor.GCYRArmorMaterials;
import argent_matter.gcyr.common.item.armor.SpaceSuitArmorItem;
import argent_matter.gcyr.common.worldgen.GCYROres;
import argent_matter.gcyr.config.GCYRConfig;
import argent_matter.gcyr.data.GCYRDatagen;
import argent_matter.gcyr.data.loader.PlanetResources;
import argent_matter.gcyr.data.recipe.GCYRTags;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.material.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.material.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.material.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.material.material.registry.MaterialRegistry;
import com.gregtechceu.gtceu.api.misc.forge.FilteredFluidHandlerItemStack;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.lowdragmc.lowdraglib.gui.factory.UIFactory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(GCYR.MOD_ID)
public class GCYR {
	public static final String
			MOD_ID = "gcyr",
			NAME = "Gregicality Rocketry";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static MaterialRegistry MATERIAL_REGISTRY;

	private static IEventBus modBus;

	public GCYR(IEventBus bus) {
		modBus = bus;
		GCYR.init(bus);
		bus.register(this);

		GCYRDimensionTypes.register(bus);
		GCYREntityDataSerializers.register(bus);
		GCYRDataComponents.register(bus);
		GCYRArmorMaterials.register(bus);
		GCYRParticles.register(bus);

		GCYRVanillaRecipeTypes.RECIPE_TYPE_DEFERRED_REGISTER.register(bus);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			GCYRClient.init();
		}
	}

	public static void init(IEventBus modBus) {
		GCYRConfig.init();
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
		event.register(GTRegistries.RECIPE_TYPES, () -> GCYRRecipeTypes.register(modBus));
		event.register(GTRegistries.RECIPE_CONDITIONS, GCYRRecipeConditions::init);
		event.register(GTRegistries.MACHINES, GCYRMachines::init);
		event.register(GTRegistries.DIMENSION_MARKERS, GCYRDimensionMarkers::init);
		event.register(GTRegistries.SOUNDS, GCYRSoundEntries::init);
		event.register(GTRegistries.ORE_VEINS, GCYROres::init);
	}

	@SubscribeEvent
	public void registerCapabilities(RegisterCapabilitiesEvent event) {
		for (Item item : BuiltInRegistries.ITEM) {
			if (item instanceof ArmorItem) {
				event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> {
					if (stack.has(GCYRDataComponents.SPACE_SUIT)) {
						return new FluidHandlerItemStack(GCYRDataComponents.SPACE_SUIT, stack, SpaceSuitArmorItem.CAPACITY) {
							@Override
							public boolean canFillFluidType(FluidStack fluid) {
								return fluid.is(GCYRTags.OXYGEN);
							}
						};
					}
					return null;
				}, item);
			}
		}
	}
}
