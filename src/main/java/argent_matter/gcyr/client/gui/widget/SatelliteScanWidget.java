package argent_matter.gcyr.client.gui.widget;

import argent_matter.gcyr.client.gui.texture.SatelliteProspectingTexture;
import argent_matter.gcyr.common.machine.electric.OreFinderScannerMachine;
import argent_matter.gcyr.common.satellite.OreFinderSatellite;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.misc.PacketProspecting;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.item.ProspectorScannerBehavior;
import com.lowdragmc.lowdraglib.gui.editor.ColorPattern;
import com.lowdragmc.lowdraglib.gui.util.DrawerHelper;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.mojang.datafixers.util.Pair;
import lombok.Getter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public class SatelliteScanWidget extends WidgetGroup {
    private final int chunkRadius;
    private final int scanTick;
    public final OreFinderScannerMachine machine;
    @Getter
    private boolean darkMode = false;
    private final DraggableScrollableWidgetGroup itemList;
    @OnlyIn(Dist.CLIENT)
    private SatelliteProspectingTexture texture;
    private int playerChunkX;
    private int playerChunkZ;
    //runtime
    private int chunkIndex = 0;
    private final Queue<Pair<BlockState[][][], ChunkPos>> packetQueue = new LinkedBlockingQueue<>();
    private final Set<Object> items = new CopyOnWriteArraySet<>();
    private final Map<String, SelectableWidgetGroup> selectedMap = new ConcurrentHashMap<>();

    public SatelliteScanWidget(int xPosition, int yPosition, int width, int height, int chunkRadius, int scanTick, OreFinderScannerMachine machine) {
        super(xPosition, yPosition, width, height);
        this.chunkRadius = chunkRadius;
        this.scanTick = scanTick;
        this.machine = machine;
        int imageWidth = (chunkRadius * 2 - 1) * 16;
        int imageHeight = (chunkRadius * 2 - 1) * 16;
        addWidget(new ImageWidget(0, (height - imageHeight) / 2 - 4, imageWidth + 8, imageHeight + 8, GuiTextures.BACKGROUND_INVERSE));
        var group = (WidgetGroup) new WidgetGroup(imageWidth + 10, 0, width - (imageWidth + 10), height).setBackground(GuiTextures.BACKGROUND_INVERSE);
        group.addWidget(itemList = new DraggableScrollableWidgetGroup(4, 28, group.getSize().width - 8, group.getSize().height - 32)
                .setYScrollBarWidth(2).setYBarStyle(null, ColorPattern.T_WHITE.rectTexture().setRadius(1)));
        addWidget(group);
    }

    @Override
    public void writeInitialData(FriendlyByteBuf buffer) {
        super.writeInitialData(buffer);
        buffer.writeVarInt(playerChunkX = gui.entityPlayer.chunkPosition().x);
        buffer.writeVarInt(playerChunkZ = gui.entityPlayer.chunkPosition().z);
        buffer.writeVarInt(gui.entityPlayer.getBlockX());
        buffer.writeVarInt(gui.entityPlayer.getBlockZ());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void readInitialData(FriendlyByteBuf buffer) {
        super.readInitialData(buffer);
        texture = new SatelliteProspectingTexture(
                buffer.readVarInt(),
                buffer.readVarInt(),
                buffer.readVarInt(),
                buffer.readVarInt(),
                gui.entityPlayer.getVisualRotationYInDegrees(), chunkRadius, darkMode, machine::getItemColor);
    }

    public void setDarkMode(boolean mode) {
        if (darkMode != mode) {
            darkMode = mode;
            if (isRemote()) {
                texture.setDarkMode(darkMode);
            }
        }
    }

    private void addOresToList(Object[][][] data) {
        var newItems = new HashSet<>();
        for (int x = 0; x < OreFinderSatellite.CELL_SIZE; x++) {
            for (int z = 0; z < OreFinderSatellite.CELL_SIZE; z++) {
                newItems.addAll(Arrays.asList(data[x][z]));
            }
        }
        items.addAll(newItems);
    }

    @Override
    public void detectAndSendChanges() {
        var player = gui.entityPlayer;
        var world = player.level();
        if (gui.getTickCount() % scanTick == 0 && chunkIndex < (chunkRadius * 2 - 1) * (chunkRadius * 2 - 1)) {

            int row = chunkIndex / (chunkRadius * 2 - 1);
            int column = chunkIndex % (chunkRadius * 2 - 1);

            int ox = column - chunkRadius + 1;
            int oz = row - chunkRadius + 1;

            var chunk = world.getChunk(playerChunkX + ox, playerChunkZ + oz);
            BlockState[][][] data = new BlockState[OreFinderSatellite.CELL_SIZE][OreFinderSatellite.CELL_SIZE][0];
            machine.scanOres(data);
            //writeUpdateInfo(-1, packet::writePacketData);
            chunkIndex++;
        }
        var held = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (held.getItem() instanceof ComponentItem componentItem) {
            for (var component : componentItem.getComponents()) {
                if (component instanceof ProspectorScannerBehavior prospector) {
                    if (!player.isCreative() && !prospector.drainEnergy(held, false)) {
                        player.closeContainer();
                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void readUpdateInfo(int id, FriendlyByteBuf buffer) {
        if (id == -1) {
            //addPacketToQueue(PacketProspecting.readPacketData(mode, buffer));
        } else {
            super.readUpdateInfo(id, buffer);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void updateScreen() {
        super.updateScreen();
        if (packetQueue != null) {
            int max = 10;
            while (max-- > 0 && !packetQueue.isEmpty()) {
                var packet = packetQueue.poll();
                texture.updateTexture(packet.getFirst(), packet.getSecond());
                addOresToList(packet.getFirst());
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    private void addPacketToQueue(Pair<BlockState[][][], ChunkPos> packet) {
        packetQueue.add(packet);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawInBackground(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.drawInBackground(graphics, mouseX, mouseY, partialTicks);
        var position = getPosition();
        var size = getSize();
        //draw background
        var x = position.x + 3;
        var y = position.y + (size.getHeight() - texture.getImageHeight()) / 2 - 1;
        texture.draw(graphics, x, y);
        int cX = (mouseX - x) / 16;
        int cZ = (mouseY - y) / 16;
        if (cX >= 0 && cZ >= 0 && cX < chunkRadius * 2 - 1 && cZ < chunkRadius * 2 - 1) {
            // draw hover layer
            DrawerHelper.drawSolidRect(graphics, cX * 16 + x, cZ * 16 + y, 16, 16,0x4B6C6C6C);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void drawInForeground(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.drawInForeground(graphics, mouseX, mouseY, partialTicks);
        // draw tooltips
        var position = getPosition();
        var size = getSize();
        var x = position.x + 3;
        var y = position.y + (size.getHeight() - texture.getImageHeight()) / 2 - 1;
        int cX = (mouseX - x) / 16;
        int cZ = (mouseY - y) / 16;
        if (cX >= 0 && cZ >= 0 && cX < chunkRadius * 2 - 1 && cZ < chunkRadius * 2 - 1) {
            // draw hover layer
            List<Component> tooltips = new ArrayList<>();
            gui.getModularUIGui().setHoverTooltip(tooltips, ItemStack.EMPTY, null, null);
        }
    }
}
