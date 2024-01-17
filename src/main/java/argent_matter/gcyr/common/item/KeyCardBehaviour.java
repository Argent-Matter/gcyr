package argent_matter.gcyr.common.item;

import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.api.space.station.SpaceStation;
import argent_matter.gcyr.common.data.GCyRItems;
import argent_matter.gcyr.data.loader.PlanetData;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class KeyCardBehaviour implements IAddInformation/*, IInteractionItem*/ {

    @Nullable
    public static UUID getOwner(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("KeyCardOwner", Tag.TAG_INT_ARRAY)) {
            return stack.getTag().getUUID("KeyCardOwner");
        }
        return null;
    }

    public static void setOwner(ItemStack stack, LivingEntity entity) {
        stack.getOrCreateTag().putUUID("KeyCardOwner", entity.getUUID());
    }

    public static void setSavedStation(ItemStack stack, @Nullable Integer stationId, Planet planet) {
        if (!GCyRItems.KEYCARD.isIn(stack)) return;
        if (stationId == null) return;
        stack.getOrCreateTag().putInt(PlanetIdChipBehaviour.CURRENT_STATION_KEY, stationId);
        stack.getTag().putString(PlanetIdChipBehaviour.CURRENT_PLANET_KEY, PlanetData.getPlanetId(planet).toString());
    }

    public static Integer getSavedStation(ItemStack stack) {
        if (!GCyRItems.KEYCARD.isIn(stack)) return null;
        return stack.getOrCreateTag().getInt(PlanetIdChipBehaviour.CURRENT_STATION_KEY);
    }

    @Nullable
    public static Planet getSavedPlanet(ItemStack stack) {
        if (!GCyRItems.KEYCARD.isIn(stack)) return null;
        return PlanetData.getPlanet(new ResourceLocation(stack.getOrCreateTag().getString(PlanetIdChipBehaviour.CURRENT_PLANET_KEY)));
    }

    //@Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (!level.isClientSide && !itemStack.getOrCreateTag().contains("KeyCardOwner", Tag.TAG_INT_ARRAY) && player.isCrouching()) {
            setOwner(itemStack, player);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
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
