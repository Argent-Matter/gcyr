package argent_matter.gcys.common.item;

import argent_matter.gcys.api.space.planet.Planet;
import argent_matter.gcys.api.space.station.SpaceStation;
import argent_matter.gcys.common.data.GCySItems;
import argent_matter.gcys.data.loader.PlanetData;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public class KeyCardBehaviour implements IInteractionItem {

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

    public static void setSavedStation(ItemStack stack, int stationId, Planet planet) {
        if (!GCySItems.KEYCARD.isIn(stack)) return;
        stack.getOrCreateTag().putInt("StationId", stationId);
        stack.getTag().putString(PlanetIdChipBehaviour.CURRENT_PLANET_TAG_ID, planet.level().location().toString());

    }

    public static int getSavedStation(ItemStack stack) {
        if (!GCySItems.KEYCARD.isIn(stack)) return SpaceStation.ID_EMPTY;
        return stack.getOrCreateTag().getInt("StationId");
    }

    @Nullable
    public static Planet getSavedPlanet(ItemStack stack) {
        if (!GCySItems.KEYCARD.isIn(stack)) return null;
        return PlanetData.getPlanetFromLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(stack.getOrCreateTag().getString(PlanetIdChipBehaviour.CURRENT_PLANET_TAG_ID)))).orElse(null);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (!level.isClientSide && !itemStack.getOrCreateTag().contains("KeyCardOwner", Tag.TAG_INT_ARRAY) && player.isCrouching()) {
            setOwner(itemStack, player);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
