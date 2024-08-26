package argent_matter.gcyr.common.gui;

import argent_matter.gcyr.GCYRClient;
import argent_matter.gcyr.common.data.GCYRMenus;
import argent_matter.gcyr.data.loader.PlanetData;
import lombok.Getter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class PlanetSelectionMenu extends AbstractContainerMenu {

    @Getter
    private final Player player;

    public PlanetSelectionMenu(int syncId, Player player, FriendlyByteBuf buf) {
        this(syncId, player);
        if (player.level().isClientSide) {
            PlanetData.readPlanetData(buf);
            GCYRClient.hasUpdatedPlanets = true;
        }
    }

    public PlanetSelectionMenu(int syncId, Player player) {
        super(GCYRMenus.PLANET_SELECTION.get(), syncId);
        this.player = player;
    }

    @Override
    public boolean stillValid(Player player) {
        return !player.isDeadOrDying();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}
