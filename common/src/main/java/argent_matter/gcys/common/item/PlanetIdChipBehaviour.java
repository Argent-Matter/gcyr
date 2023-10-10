package argent_matter.gcys.common.item;

import argent_matter.gcys.api.space.planet.Planet;
import argent_matter.gcys.client.gui.screen.PlanetSelectionScreen;
import argent_matter.gcys.common.data.GcysMenus;
import argent_matter.gcys.data.loader.PlanetData;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
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
    
    @Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
        ItemStack heldItem = player.getItemInHand(usedHand);
        if (player instanceof ServerPlayer serverPlayer) {
            GcysMenus.PLANET_SELECTION.open(serverPlayer, Component.translatable("gui.gcys.planet_selector"), PlanetData::writePlanetData);
            return InteractionResultHolder.consume(heldItem);
        }
        return InteractionResultHolder.pass(heldItem);
    }

    public String getPlanetName(Planet currentTarget) {
        return PLANET_NAME_CACHE.inverse().computeIfAbsent(currentTarget, planet -> PlanetData.getLevelFromPlanet(planet).map(level -> "level." + level.location().toLanguageKey()).orElse("UNKNOWN LEVEL"));
    }

    public void setPlanetFromName(String planetName, ItemStack held) {
        Planet currentTarget = PLANET_NAME_CACHE.computeIfAbsent(planetName, (name) -> PlanetData.getPlanetFromLevel(ResourceKey.create(Registry.DIMENSION_REGISTRY, ResourceLocation.of(name.substring(6), '.'))).orElse(null));
        held.getOrCreateTag().putString("gcys:current_planet", currentTarget.level().location().toString());
    }

    public static Planet getPlanetFromStack(ItemStack stack) {
        return PlanetData.getPlanetFromLevel(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(stack.getOrCreateTag().getString("gcys:current_planet")))).orElse(null);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        Planet currentTarget;
        currentTarget = getPlanetFromStack(stack);
        if (currentTarget == null) return;

        tooltipComponents.add(Component.translatable("metaitem.planet_id_circuit.id").append(Component.translatable(getPlanetName(currentTarget))));
    }
}
