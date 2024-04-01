package argent_matter.gcyr.forge;

import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.api.capability.GCyRCapabilityHelper;
import argent_matter.gcyr.api.capability.IDysonSystem;
import argent_matter.gcyr.common.data.GCyRItems;
import argent_matter.gcyr.common.data.GCyRNetworking;
import argent_matter.gcyr.common.item.armor.SpaceSuitArmorItem;
import argent_matter.gcyr.common.networking.s2c.PacketSyncDysonSphereStatus;
import argent_matter.gcyr.common.recipe.type.SmithingSpaceSuitRecipe;
import argent_matter.gcyr.data.loader.PlanetData;
import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;
import com.lowdragmc.lowdraglib.side.fluid.FluidTransferHelper;
import com.lowdragmc.lowdraglib.side.fluid.IFluidTransfer;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = GCyR.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEventListener {

    @SubscribeEvent
    public static void registerItemStackCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        final ItemStack itemStack = event.getObject();
        if (itemStack.is(GCyRItems.SPACE_SUIT_CHEST.get()) || (itemStack.hasTag() && itemStack.getTag().getBoolean(SmithingSpaceSuitRecipe.SPACE_SUIT_ARMOR_KEY))) {
            event.addCapability(GCyR.id("spacesuit"), new ICapabilityProvider() {
                @Override
                public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
                    return SpaceSuitArmorItem.getCapability(itemStack, capability);
                }
            });

        }
    }

    @SubscribeEvent
    public static void registerServerReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new PlanetData());
    }

    @SubscribeEvent
    public static void playerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            IDysonSystem system = GCyRCapabilityHelper.getDysonSystem(player.serverLevel());
            if (system != null && system.isDysonSphereActive() && !system.activeDysonSphere().isCollapsed()) {
                GCyRNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(true), player);
            } else {
                GCyRNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(false), player);
            }
        }
    }

    @SubscribeEvent
    public static void entityJoined(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            IDysonSystem system = GCyRCapabilityHelper.getDysonSystem(player.serverLevel());
            if (system != null && system.isDysonSphereActive() && !system.activeDysonSphere().isCollapsed()) {
                GCyRNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(true), player);
            } else {
                GCyRNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(false), player);
            }
        }
    }

    private static final ThreadLocal<Set<IDysonSystem>> TICKED_SYSTEMS = ThreadLocal.withInitial(HashSet::new);

    @SubscribeEvent
    public static void levelTick(TickEvent.LevelTickEvent event) {
        if (!(event.level instanceof ServerLevel level)) return;

        if (event.phase == TickEvent.Phase.START) {
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

    @SubscribeEvent
    public static void onAddTooltips(ItemTooltipEvent event) {
        if (event.getItemStack().hasTag() && event.getItemStack().getTag().getBoolean(SmithingSpaceSuitRecipe.SPACE_SUIT_ARMOR_KEY)) {
            event.getToolTip().add(1, Component.translatable("tooltip.gcyr.spacesuit"));
            IFluidTransfer transfer = FluidTransferHelper.getFluidTransfer(new ItemStackTransfer(event.getItemStack()), 0);
            if (transfer != null) {
                event.getToolTip().add(1, Component.translatable("tooltip.gcyr.spacesuit.stored", transfer.getFluidInTank(0), transfer.getTankCapacity(0)));
            }
        }
    }
}
