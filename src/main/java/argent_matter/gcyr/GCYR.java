package argent_matter.gcyr;

import argent_matter.gcyr.api.gui.factory.EntityUIFactory;
import argent_matter.gcyr.api.registries.GCYRRegistries;
import argent_matter.gcyr.common.data.*;
import argent_matter.gcyr.common.item.armor.GCYRArmorMaterials;
import argent_matter.gcyr.common.item.armor.SpaceSuitArmorItem;
import argent_matter.gcyr.config.GCYRConfig;
import argent_matter.gcyr.data.GCYRDatagen;
import argent_matter.gcyr.data.loader.PlanetResources;
import argent_matter.gcyr.data.recipe.GCYRTags;
import argent_matter.gcyr.mixin.RegisterClientReloadListenersEventAccessor;
import argent_matter.gcyr.mixin.ReloadableResourceManagerAccessor;
import com.gregtechceu.gtceu.api.material.material.registry.MaterialRegistry;
import com.lowdragmc.lowdraglib.gui.factory.UIFactory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
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

	public GCYR(IEventBus bus, ModContainer container) {
		modBus = bus;
		bus.register(this);

		bus.addListener(this::registerRegistries);
		GCYRRegistries.REGISTRATE.registerEventListeners(modBus);
		GCYRSatellites.SATELLITES.register(bus);

		GCYRDimensionTypes.register(bus);
		GCYREntityDataSerializers.register(bus);
		GCYRDataComponents.register(bus);
		GCYRArmorMaterials.register(bus);
		GCYRParticles.register(bus);
		GCYRRecipeTypes.register(bus);
		GCYRVanillaRecipeTypes.RECIPE_TYPE_DEFERRED_REGISTER.register(bus);

		GCYRConfig.init();
		UIFactory.register(EntityUIFactory.INSTANCE);
		GCYRCreativeModeTabs.init();
		GCYREntities.init();
		GCYRBlocks.init();
		GCYRItems.init();
		GCYRMenus.init();
		GCYRDatagen.init();
		GCYRDimensionTypes.init();

		if (FMLEnvironment.dist == Dist.CLIENT) {
			GCYRClient.init();
		}
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	@SubscribeEvent
	public void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
		// insert the resource loader into the first index forcefully, so we can load our data before shaders are loaded.
		ReloadableResourceManagerAccessor manager = (ReloadableResourceManagerAccessor)
				((RegisterClientReloadListenersEventAccessor) event).getResourceManager();
		manager.getListeners().addFirst(PlanetResources.INSTANCE);
	}

	@SubscribeEvent
	public void modifyMaterials(RegisterEvent event) {
		GCYRMaterials.initAndModify();
	}

	public void registerRegistries(NewRegistryEvent event) {
		event.register(GCYRRegistries.SATELLITES);
	}

	/* Changed from 1.20 idk whats the current impl now tbf
	@SubscribeEvent
	public void gtRegister(? event) {
		event.register(GTRegistries.RECIPE_CONDITIONS, GCYRRecipeConditions::init);
		event.register(GTRegistries.MACHINES, GCYRMachines::init);
		event.register(GTRegistries.DIMENSION_MARKERS, GCYRDimensionMarkers::init);
		event.register(GTRegistries.SOUNDS, GCYRSoundEntries::init);
		event.register(GTRegistries.ORE_VEIN_REGISTRY, GCYROres::init);
	}
	*/

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
