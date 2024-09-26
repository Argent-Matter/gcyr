package argent_matter.gcyr.common.item.behaviour;

import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.api.space.station.SpaceStation;
import argent_matter.gcyr.common.data.GCYRDataComponents;
import argent_matter.gcyr.common.data.GCYRItems;
import argent_matter.gcyr.common.data.GCYRMenus;
import argent_matter.gcyr.common.item.component.IdChip;
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

    @Override
    public InteractionResultHolder<ItemStack> use(ItemStack item, Level level, Player player, InteractionHand usedHand) {
        if (player instanceof ServerPlayer serverPlayer) {
            GCYRMenus.PLANET_SELECTION.open(serverPlayer, Component.translatable("gui.gcyr.planet_selector"), PlanetData::writePlanetData);
            return InteractionResultHolder.consume(item);
        }
        return InteractionResultHolder.pass(item);
    }

    public static void setSpaceStation(ItemStack held, int stationId) {
        if (stationId == SpaceStation.ID_EMPTY) return;
        held.update(GCYRDataComponents.ID_CHIP, IdChip.EMPTY, chip -> chip.updateStation(stationId));
    }

    @Nullable
    public static Integer getSpaceStationId(ItemStack held) {
        IdChip chip = held.get(GCYRDataComponents.ID_CHIP);
        if (chip == null || !chip.hasStation()) return null;
        return chip.currentStation();
    }

    @Nullable
    public static Planet getPlanetFromStack(ItemStack stack) {
        if (!stack.has(GCYRDataComponents.ID_CHIP)) {
            return null;
        }
        return PlanetData.getPlanetFromLevelOrOrbit(ResourceKey.create(Registries.DIMENSION,
                        stack.get(GCYRDataComponents.ID_CHIP).currentPlanet().orElse(null)))
                .orElse(null);
    }

    public static void setSavedPosition(ItemStack stack, ResourceKey<Level> level, BlockPos pos) {
        stack.update(GCYRDataComponents.ID_CHIP, IdChip.EMPTY, chip -> chip.updatePlanet(level.location()).updatePos(pos));
    }

    @Nullable
    public static GlobalPos getSavedPosition(ItemStack stack) {
        if (!stack.has(GCYRDataComponents.ID_CHIP)) return null;
        IdChip idChip = stack.get(GCYRDataComponents.ID_CHIP);
        if (idChip.currentPos().isEmpty()) return null;
        ResourceLocation currentLevel = idChip.currentPlanet().orElse(null);
        return GlobalPos.of(ResourceKey.create(Registries.DIMENSION, currentLevel), idChip.currentPos().get());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Item.TooltipContext level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        Planet currentTarget = getPlanetFromStack(stack);
        if (currentTarget != null) {
            tooltipComponents.add(Component.translatable("metaitem.planet_id_circuit.id").append(Component.translatable(currentTarget.translation())));
        }
        Integer currentStationId = getSpaceStationId(stack);
        if (currentStationId != null) {
            tooltipComponents.add(Component.translatable("metaitem.planet_id_circuit.station", currentStationId));
        }
        BlockPos currentTargetPos = !stack.has(GCYRDataComponents.ID_CHIP) ? null : stack.get(GCYRDataComponents.ID_CHIP).currentPos().orElse(null);
        if (currentTargetPos != null) {
            tooltipComponents.add(Component.translatable("metaitem.planet_id_circuit.pos",
                    currentTargetPos.getX(), currentTargetPos.getY(), currentTargetPos.getZ()));
        }
    }
}
