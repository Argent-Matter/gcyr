package argent_matter.gcys.common.item;

import argent_matter.gcys.api.space.planet.Planet;
import argent_matter.gcys.api.space.station.SpaceStation;
import argent_matter.gcys.common.data.GCySItems;
import argent_matter.gcys.common.data.GCySMenus;
import argent_matter.gcys.data.loader.PlanetData;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
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
    private static final BiMap<String, Planet> PLANET_NAME_CACHE = HashBiMap.create();

    public static final String CURRENT_STATION_TAG_ID = "gcys:current_station";
    public static final String CURRENT_PLANET_TAG_ID = "gcys:current_planet";

    @Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
        ItemStack heldItem = player.getItemInHand(usedHand);
        if (player instanceof ServerPlayer serverPlayer) {
            GCySMenus.PLANET_SELECTION.open(serverPlayer, Component.translatable("gui.gcys.planet_selector"), PlanetData::writePlanetData);
            return InteractionResultHolder.consume(heldItem);
        }
        return InteractionResultHolder.pass(heldItem);
    }

    public static void setSpaceStation(ItemStack held, int stationId) {
        if (!GCySItems.ID_CHIP.isIn(held) || stationId == SpaceStation.ID_EMPTY) return;
        held.getOrCreateTag().putInt(CURRENT_STATION_TAG_ID, stationId);
    }

    public static int getSpaceStationId(ItemStack held) {
        if (!GCySItems.ID_CHIP.isIn(held) || !held.getOrCreateTag().contains(CURRENT_STATION_TAG_ID, Tag.TAG_INT)) return SpaceStation.ID_EMPTY;
        return held.getOrCreateTag().getInt(CURRENT_STATION_TAG_ID);
    }

    public String getPlanetName(Planet currentTarget) {
        return PLANET_NAME_CACHE.inverse().computeIfAbsent(currentTarget, planet -> PlanetData.getLevelFromPlanet(planet).map(level -> "level." + level.location().toLanguageKey()).orElse("UNKNOWN LEVEL"));
    }

    public static void setPlanetFromName(String planetName, ItemStack held) {
        if (!GCySItems.ID_CHIP.isIn(held)) return;
        Planet currentTarget = PLANET_NAME_CACHE.computeIfAbsent(planetName, (name) -> PlanetData.getPlanetFromLevel(ResourceKey.create(Registries.DIMENSION, ResourceLocation.of(name.substring(6), '.'))).orElse(null));
        held.getOrCreateTag().putString(CURRENT_PLANET_TAG_ID, currentTarget.level().location().toString());
    }

    @Nullable
    public static Planet getPlanetFromStack(ItemStack stack) {
        return PlanetData.getPlanetFromLevel(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(stack.getOrCreateTag().getString(CURRENT_PLANET_TAG_ID)))).orElse(null);
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
