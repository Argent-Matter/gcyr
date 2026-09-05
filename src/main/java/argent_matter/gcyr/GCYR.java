package argent_matter.gcyr;

import argent_matter.gcyr.api.gui.factory.EntityUIFactory;
import argent_matter.gcyr.api.registries.GCYRRegistries;
import argent_matter.gcyr.common.data.*;
import argent_matter.gcyr.common.data.block.GCYRBlocks;
import argent_matter.gcyr.common.data.client.*;
import argent_matter.gcyr.common.data.dimension.*;
import argent_matter.gcyr.common.data.entity.*;
import argent_matter.gcyr.common.data.item.*;
import argent_matter.gcyr.common.data.machine.GCYRMachines;
import argent_matter.gcyr.common.data.material.GCYRMaterials;
import argent_matter.gcyr.common.data.network.GCYRNetworking;
import argent_matter.gcyr.common.data.recipe.*;
import argent_matter.gcyr.common.gui.EntityOxygenHUD;
import argent_matter.gcyr.config.GCYRConfig;
import argent_matter.gcyr.core.mixin.RegisterClientReloadListenersEventAccessor;
import argent_matter.gcyr.core.mixin.ReloadableResourceManagerAccessor;
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
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;

import com.lowdragmc.lowdraglib.gui.factory.UIFactory;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(GCYR.MOD_ID)
public class GCYR {

    public static final String MOD_ID = "gcyr",
            NAME = "Gregicality Rocketry";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
    public static MaterialRegistry MATERIAL_REGISTRY;

    private static final ResourceLocation TEMPLATE_ID = ResourceLocation.fromNamespaceAndPath(MOD_ID, "");

    public GCYR(FMLJavaModLoadingContext ctx) {
        GCYR.init();
        IEventBus modBus = ctx.getModEventBus();
        modBus.register(this);

        modBus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
        modBus.addGenericListener(RecipeConditionType.class, this::registerRecipeConditions);
        modBus.addGenericListener(MachineDefinition.class, this::registerMachines);
        modBus.addGenericListener(DimensionMarker.class, this::registerDimensionMarkers);
        GCYRDimensionTypes.register(modBus);

        GCYRVanillaRecipeTypes.RECIPE_TYPE_DEFERRED_REGISTER.register(modBus);

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
        return TEMPLATE_ID.withPath(path);
    }

    @SubscribeEvent
    public void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerBelowAll("oxygen_tank", new EntityOxygenHUD());
    }

    @SubscribeEvent
    public void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        // insert the resource loader into the first index forcefully, so we can load our data before shaders are
        // loaded.
        ReloadableResourceManagerAccessor manager = (ReloadableResourceManagerAccessor) ((RegisterClientReloadListenersEventAccessor) event)
                .getResourceManager();
        manager.getListeners().add(0, PlanetResources.INSTANCE);
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

    public void registerRecipeConditions(GTCEuAPI.RegisterEvent<String, RecipeConditionType<?>> event) {
        GCYRRecipeConditions.init();
    }

    public void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
        GCYRMachines.init();
    }

    public void registerDimensionMarkers(GTCEuAPI.RegisterEvent<ResourceLocation, DimensionMarker> event) {
        GCYRDimensionMarkers.init();
    }
}
