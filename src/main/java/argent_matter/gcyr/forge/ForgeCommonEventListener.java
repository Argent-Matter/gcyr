package argent_matter.gcyr.forge;

import argent_matter.gcyr.GCYR;
import argent_matter.gcyr.api.capability.GCYRCapabilityHelper;
import argent_matter.gcyr.api.capability.IDysonSystem;
import argent_matter.gcyr.common.data.GCYRDataComponents;
import argent_matter.gcyr.common.networking.s2c.PacketSyncDysonSphereStatus;
import argent_matter.gcyr.data.loader.PlanetData;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.lowdragmc.lowdraglib.side.fluid.FluidTransferHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = GCYR.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ForgeCommonEventListener {

    @SubscribeEvent
    public static void registerServerReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new PlanetData());
    }

    @SubscribeEvent
    public static void playerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            IDysonSystem system = GCYRCapabilityHelper.getDysonSystem(player.serverLevel());
            if (system != null && system.isDysonSphereActive() && !system.activeDysonSphere().isCollapsed()) {
                PacketDistributor.sendToPlayer(player, new PacketSyncDysonSphereStatus(true));
            } else {
                PacketDistributor.sendToPlayer(player, new PacketSyncDysonSphereStatus(false));
            }
        }
    }

    @SubscribeEvent
    public static void entityJoined(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            IDysonSystem system = GCYRCapabilityHelper.getDysonSystem(player.serverLevel());
            if (system != null && system.isDysonSphereActive() && !system.activeDysonSphere().isCollapsed()) {
                PacketDistributor.sendToPlayer(player, new PacketSyncDysonSphereStatus(true));
            } else {
                PacketDistributor.sendToPlayer(player, new PacketSyncDysonSphereStatus(false));
            }
        }
    }

    private static final ThreadLocal<Set<IDysonSystem>> TICKED_SYSTEMS = ThreadLocal.withInitial(HashSet::new);

    @SubscribeEvent
    public static void levelTick(LevelTickEvent.Pre event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        if (!level.dimensionType().hasCeiling()) {
            var sat = GCYRCapabilityHelper.getSatellites(level);
            if (sat != null) sat.tickSatellites();
        }

        IDysonSystem system = GCYRCapabilityHelper.getDysonSystem(level);
        if (system == null || TICKED_SYSTEMS.get().contains(system)) return;
        system.tick();
    }

    public static void levelTickPost(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        TICKED_SYSTEMS.get().clear();
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onAddTooltips(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.has(GCYRDataComponents.SPACE_SUIT)) {
            if (stack.is(ItemTags.CHEST_ARMOR)) {
                IFluidHandler transfer = FluidTransferHelper.getFluidTransfer(new CustomItemStackHandler(stack), 0);
                if (transfer != null) {
                    event.getToolTip().add(1, Component.translatable("tooltip.gcyr.spacesuit.stored", transfer.getFluidInTank(0).getAmount(), transfer.getTankCapacity(0)));
                }
            }
            event.getToolTip().add(1, Component.translatable("tooltip.gcyr.spacesuit"));
        }
    }
}
