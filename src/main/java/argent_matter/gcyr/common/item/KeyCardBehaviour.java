package argent_matter.gcyr.common.item;

import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.common.data.GCyRItems;
import argent_matter.gcyr.data.loader.PlanetData;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.widget.IntInputWidget;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.IItemUIFactory;
import com.lowdragmc.lowdraglib.gui.factory.HeldItemUIFactory;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class KeyCardBehaviour implements IAddInformation, IItemUIFactory {
    public static final String OWNER_KEY = "gcyr:key_card_owner";
    public static final String TARGET_X_KEY = "gcyr:target_x";
    public static final String TARGET_Z_KEY = "gcyr:target_z";

    @Nullable
    public static UUID getOwner(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains(OWNER_KEY, Tag.TAG_INT_ARRAY)) {
            return stack.getTag().getUUID(OWNER_KEY);
        }
        return null;
    }

    public static void setOwner(ItemStack stack, LivingEntity entity) {
        stack.getOrCreateTag().putUUID(OWNER_KEY, entity.getUUID());
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

    @Override
    public ModularUI createUI(HeldItemUIFactory.HeldItemHolder holder, Player entityPlayer) {
        return new ModularUI(200, 100, holder, entityPlayer).background(GuiTextures.BACKGROUND)
                .widget(new IntInputWidget(18, 18, 80, 24, () -> getTargetX(holder.getHeld()), targetX -> setTargetX(holder.getHeld(), targetX)))
                .widget(new LabelWidget(67, 9, "behaviour.gcyr.keycard.target_x"))
                .widget(new IntInputWidget(128, 18, 80, 24, () -> getTargetZ(holder.getHeld()), targetX -> setTargetZ(holder.getHeld(), targetX)))
                .widget(new LabelWidget(195, 9, "behaviour.gcyr.keycard.target_z"));
    }

    /*
    @Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (!level.isClientSide && !itemStack.getOrCreateTag().contains(OWNER_KEY, Tag.TAG_INT_ARRAY) && player.isCrouching()) {
            setOwner(itemStack, player);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }
    */
    
    public static int getTargetX(ItemStack stack) {
        if (!GCyRItems.KEYCARD.isIn(stack)) return 0;
        return stack.getOrCreateTag().getInt(TARGET_X_KEY);
    }

    public static void setTargetX(ItemStack stack, int targetX) {
        if (!GCyRItems.KEYCARD.isIn(stack)) return;
        stack.getOrCreateTag().putInt(TARGET_X_KEY, targetX);
    }

    public static int getTargetZ(ItemStack stack) {
        if (!GCyRItems.KEYCARD.isIn(stack)) return 0;
        return stack.getOrCreateTag().getInt(TARGET_Z_KEY);
    }

    public static void setTargetZ(ItemStack stack, int targetZ) {
        if (!GCyRItems.KEYCARD.isIn(stack)) return;
        stack.getOrCreateTag().putInt(TARGET_Z_KEY, targetZ);
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
