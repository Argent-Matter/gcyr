package argent_matter.gcyr.common.item;

import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.api.space.station.SpaceStation;
import argent_matter.gcyr.common.data.GCyRItems;
import argent_matter.gcyr.common.data.GCyRMenus;
import argent_matter.gcyr.data.loader.PlanetData;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import net.minecraft.core.Registry;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlanetIdChipBehaviour implements IInteractionItem, IAddInformation {
    public static final String CURRENT_STATION_TAG_ID = "gcyr:current_station";
    public static final String CURRENT_PLANET_TAG_ID = "gcyr:current_planet";

    @Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
        ItemStack heldItem = player.getItemInHand(usedHand);
        if (player instanceof ServerPlayer serverPlayer) {
            GCyRMenus.PLANET_SELECTION.open(serverPlayer, Component.translatable("gui.gcyr.planet_selector"), PlanetData::writePlanetData);
            return InteractionResultHolder.consume(heldItem);
        }
        return InteractionResultHolder.pass(heldItem);
    }

    public static void setSpaceStation(ItemStack held, int stationId) {
        if (!GCyRItems.ID_CHIP.isIn(held) || stationId == SpaceStation.ID_EMPTY) return;
        held.getOrCreateTag().putInt(CURRENT_STATION_TAG_ID, stationId);
    }

    public static int getSpaceStationId(ItemStack held) {
        if (!GCyRItems.ID_CHIP.isIn(held) || !held.getOrCreateTag().contains(CURRENT_STATION_TAG_ID, Tag.TAG_INT)) return SpaceStation.ID_EMPTY;
        return held.getOrCreateTag().getInt(CURRENT_STATION_TAG_ID);
    }

    public String getPlanetName(Planet currentTarget) {
        return currentTarget.translation();
    }

    @Nullable
    public static Planet getPlanetFromStack(ItemStack stack) {
        return PlanetData.getPlanetFromLevelOrOrbit(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(stack.getOrCreateTag().getString(CURRENT_PLANET_TAG_ID)))).orElse(null);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        Planet currentTarget = getPlanetFromStack(stack);
        if (currentTarget != null) {
            tooltipComponents.add(Component.translatable("metaitem.planet_id_circuit.id").append(Component.translatable(getPlanetName(currentTarget))));
        }
        int currentStationId = getSpaceStationId(stack);
        if (currentStationId != SpaceStation.ID_EMPTY) {
            tooltipComponents.add(Component.translatable("metaitem.planet_id_circuit.station", currentStationId));
        }

    }
}
