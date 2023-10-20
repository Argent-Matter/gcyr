package argent_matter.gcys.forge;

import argent_matter.gcys.GCyS;
import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.api.capability.IDysonSystem;
import argent_matter.gcys.common.data.GCySNetworking;
import argent_matter.gcys.common.item.armor.forge.SpaceSuitArmorItemImpl;
import argent_matter.gcys.common.networking.s2c.PacketSyncDysonSphereStatus;
import argent_matter.gcys.data.loader.PlanetData;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = GCyS.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEventListener {

    @SubscribeEvent
    public static void registerItemStackCapabilities(AttachCapabilitiesEvent<ItemStack> event) {

        if (event.getObject().getItem() instanceof SpaceSuitArmorItemImpl spaceSuitItem) {
            final ItemStack itemStack = event.getObject();
            event.addCapability(GCyS.id("fluid"), new ICapabilityProvider() {
                @Override
                public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
                    return spaceSuitItem.getCapability(itemStack, capability);
                }
            });
        }
    }

    @SubscribeEvent
    public static void inputKey(InputEvent.Key event) {
        GCyS.onKeyPressed(event.getKey(), event.getAction(), event.getModifiers());
    }

    @SubscribeEvent
    public static void registerServerReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new PlanetData());
    }

    @SubscribeEvent
    public static void playerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            IDysonSystem system = GcysCapabilityHelper.getDysonSystem(player.getLevel(), player.getOnPos());
            if (system != null && system.isDysonSphereActive()) {
                GCySNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(true), player);
            } else {
                GCySNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(false), player);
            }
        }
    }

    @SubscribeEvent
    public static void entityJoined(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            IDysonSystem system = GcysCapabilityHelper.getDysonSystem(player.getLevel(), player.getOnPos());
            if (system != null && system.isDysonSphereActive()) {
                GCySNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(true), player);
            } else {
                GCySNetworking.NETWORK.sendToPlayer(new PacketSyncDysonSphereStatus(false), player);
            }
        }
    }
}
