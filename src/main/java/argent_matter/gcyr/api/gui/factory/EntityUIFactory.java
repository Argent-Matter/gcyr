package argent_matter.gcyr.api.gui.factory;

import argent_matter.gcyr.GCYR;
import com.lowdragmc.lowdraglib.gui.factory.UIFactory;
import com.lowdragmc.lowdraglib.gui.modular.IUIHolder;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class EntityUIFactory extends UIFactory<Entity> {
    public static final EntityUIFactory INSTANCE = new EntityUIFactory();

    private EntityUIFactory() {
        super(GCYR.id("entity"));
    }

    @Override
    protected ModularUI createUITemplate(Entity entity, Player entityPlayer) {
        if (entity instanceof IUIHolder holder) {
            return holder.createUI(entityPlayer);
        }
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected Entity readHolderFromSyncData(RegistryFriendlyByteBuf syncData) {
        Level world = Minecraft.getInstance().level;
        return world == null ? null : world.getEntity(syncData.readVarInt());
    }

    @Override
    protected void writeHolderToSyncData(RegistryFriendlyByteBuf syncData, Entity holder) {
        syncData.writeVarLong(holder.getId());
    }
}
