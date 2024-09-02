package argent_matter.gcyr.common.item;

import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.api.space.station.SpaceStation;
import argent_matter.gcyr.common.data.GCYRItems;
import argent_matter.gcyr.common.data.GCYRMenus;
import argent_matter.gcyr.data.loader.PlanetData;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtUtils;
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
    public static final String CURRENT_STATION_KEY = "gcyr:current_station";
    public static final String CURRENT_PLANET_KEY = "gcyr:current_planet";
    public static final String CURRENT_POS_KEY = "gcyr:current_position";

    @Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
        ItemStack heldItem = player.getItemInHand(usedHand);
        if (player instanceof ServerPlayer serverPlayer) {
            GCYRMenus.PLANET_SELECTION.open(serverPlayer, Component.translatable("gui.gcyr.planet_selector"), PlanetData::writePlanetData);
            return InteractionResultHolder.consume(heldItem);
        }
        return InteractionResultHolder.pass(heldItem);
    }

    public static void setSpaceStation(ItemStack held, int stationId) {
        if (!GCYRItems.ID_CHIP.isIn(held) || stationId == SpaceStation.ID_EMPTY) return;
        held.getOrCreateTag().putInt(CURRENT_STATION_KEY, stationId);
    }

    @Nullable
    public static Integer getSpaceStationId(ItemStack held) {
        if (!GCYRItems.ID_CHIP.isIn(held) || !held.getOrCreateTag().contains(CURRENT_STATION_KEY, Tag.TAG_INT)) return null;
        return held.getOrCreateTag().getInt(CURRENT_STATION_KEY);
    }

    @Nullable
    public static Planet getPlanetFromStack(ItemStack stack) {
        return PlanetData.getPlanetFromLevelOrOrbit(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(stack.getOrCreateTag().getString(CURRENT_PLANET_KEY)))).orElse(null);
    }

    public static void setSavedPosition(ItemStack stack, ResourceKey<Level> level, BlockPos pos) {
        if (!GCYRItems.ID_CHIP.isIn(stack)) return;
        stack.getOrCreateTag().putString(CURRENT_PLANET_KEY, level.location().toString());
        stack.getTag().put(CURRENT_POS_KEY, NbtUtils.writeBlockPos(pos));
    }

    @Nullable
    public static GlobalPos getSavedPosition(ItemStack stack) {
        if (!GCYRItems.ID_CHIP.isIn(stack)) return null;
        if (!stack.hasTag() || !stack.getOrCreateTag().contains(CURRENT_POS_KEY)) return null;
        ResourceLocation currentLevel = new ResourceLocation(stack.getTag().getString(CURRENT_PLANET_KEY));
        return GlobalPos.of(ResourceKey.create(Registries.DIMENSION, currentLevel),
                NbtUtils.readBlockPos(stack.getTag().getCompound(CURRENT_POS_KEY)));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        Planet currentTarget = getPlanetFromStack(stack);
        if (currentTarget != null) {
            tooltipComponents.add(Component.translatable("metaitem.planet_id_circuit.id").append(Component.translatable(currentTarget.translation())));
        }
        Integer currentStationId = getSpaceStationId(stack);
        if (currentStationId != null) {
            tooltipComponents.add(Component.translatable("metaitem.planet_id_circuit.station", currentStationId));
        }
        BlockPos currentTargetPos = !stack.hasTag() || !stack.getTag().contains(CURRENT_POS_KEY) ? null :
                NbtUtils.readBlockPos(stack.getTag().getCompound(CURRENT_POS_KEY));
        if (currentTargetPos != null) {
            tooltipComponents.add(Component.translatable("metaitem.planet_id_circuit.pos",
                    currentTargetPos.getX(), currentTargetPos.getY(), currentTargetPos.getZ()));
        }
    }
}
