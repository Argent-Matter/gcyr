package argent_matter.gcyr.common.item.behaviour;

import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.common.data.GCYRDataComponents;
import argent_matter.gcyr.common.data.GCYRItems;
import argent_matter.gcyr.common.item.component.IdChip;
import argent_matter.gcyr.data.loader.PlanetData;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class KeyCardBehaviour implements IAddInformation {

    @Nullable
    public static UUID getOwner(ItemStack stack) {
        return stack.get(GCYRDataComponents.KEYCARD);
    }

    public static void setOwner(ItemStack stack, LivingEntity entity) {
        stack.set(GCYRDataComponents.KEYCARD, entity.getUUID());
    }

    public static void setSavedStation(ItemStack stack, @Nullable Integer stationId, Planet planet) {
        if (!GCYRItems.KEYCARD.isIn(stack)) return;
        if (stationId == null) return;
        stack.set(GCYRDataComponents.ID_CHIP, new IdChip(stationId, planet.orbitWorld().location(), null));
    }

    @Nullable
    public static Integer getSavedStation(ItemStack stack) {
        IdChip idChip = stack.get(GCYRDataComponents.ID_CHIP);
        if (idChip == null || idChip.currentStation() == Integer.MIN_VALUE) {
            return null;
        }
        return idChip.currentStation();
    }

    @Nullable
    public static Planet getSavedPlanet(ItemStack stack) {
        IdChip idChip = stack.get(GCYRDataComponents.ID_CHIP);
        if (idChip == null) {
            return null;
        }
        return PlanetData.getPlanet(idChip.currentPlanet());
    }

    //@Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (!level.isClientSide && !itemStack.has(GCYRDataComponents.KEYCARD) && player.isCrouching()) {
            setOwner(itemStack, player);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Item.TooltipContext level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        Planet currentTarget = getSavedPlanet(stack);
        if (currentTarget != null) {
            tooltipComponents.add(Component.translatable("metaitem.planet_id_circuit.id").append(Component.translatable(currentTarget.translation())));
        }
        Integer currentStationId = getSavedStation(stack);
        if (currentStationId != null) {
            tooltipComponents.add(Component.translatable("metaitem.planet_id_circuit.station", currentStationId));
        }

    }
}
