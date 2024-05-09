package argent_matter.gcyr.client.gui.widget;

import argent_matter.gcyr.client.gui.texture.SatelliteProspectingTexture;
import argent_matter.gcyr.common.gui.widget.PacketSatelliteProspecting;
import argent_matter.gcyr.common.machine.electric.OreFinderScannerMachine;
import argent_matter.gcyr.common.satellite.OreFinderSatellite;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.lowdragmc.lowdraglib.gui.editor.ColorPattern;
import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.gui.texture.ItemStackTexture;
import com.lowdragmc.lowdraglib.gui.texture.TextTexture;
import com.lowdragmc.lowdraglib.gui.util.DrawerHelper;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;
import lombok.Getter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
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
import java.util.function.Consumer;

public class SatelliteScanWidget extends DraggableWidgetGroup implements SearchComponentWidget.IWidgetSearch<BlockState> {
    private final Map<BlockState, IGuiTexture> ICON_CACHE = new HashMap<>();

    private final int chunkRadius;
    private final int scanTick;
    public final OreFinderScannerMachine machine;
    @Getter
    private boolean darkMode = false;
    private final DraggableScrollableWidgetGroup itemList;
    @OnlyIn(Dist.CLIENT)
    private SatelliteProspectingTexture texture;
    private int centerChunkX;
    private int centerChunkZ;
    //runtime
    private int chunkIndex = 0;
    private final Queue<PacketSatelliteProspecting> packetQueue = new LinkedBlockingQueue<>();
    private final Set<BlockState> items = new CopyOnWriteArraySet<>();
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
        group.addWidget(new SearchComponentWidget<>(6, 6, group.getSize().width - 12, 18, this));
        addWidget(group);

        addNewItem("[all]", "all resources", IGuiTexture.EMPTY, -1);
    }

    @Override
    public void writeInitialData(FriendlyByteBuf buffer) {
        super.writeInitialData(buffer);
        buffer.writeVarInt(centerChunkX = gui.entityPlayer.chunkPosition().x);
        buffer.writeVarInt(centerChunkZ = gui.entityPlayer.chunkPosition().z);
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
                gui.entityPlayer.getVisualRotationYInDegrees(), chunkRadius, darkMode, this::getItemColor);
    }

    public void setDarkMode(boolean mode) {
        if (darkMode != mode) {
            darkMode = mode;
            if (isRemote()) {
                texture.setDarkMode(darkMode);
            }
        }
    }

    private void addOresToList(BlockState[][][] data) {
        for (int x = 0; x < OreFinderSatellite.CELL_SIZE; x++) {
            for (int z = 0; z < OreFinderSatellite.CELL_SIZE; z++) {
                for (var item : data[x][z]) {
                    items.add(item);
                    addNewItem(getUniqueID(item), resultDisplay(item), getItemIcon(item), getItemColor(item));
                }
            }
        }
    }

    private void addNewItem(String uniqueID, String renderingName, IGuiTexture icon, int color) {
        if (!selectedMap.containsKey(uniqueID)) {
            var index = itemList.widgets.size();
            var selectableWidgetGroup = new SelectableWidgetGroup(0, index * 15, itemList.getSize().width - 4, 15);
            var size = selectableWidgetGroup.getSize();
            selectableWidgetGroup.addWidget(new ImageWidget(0, 0, 15, 15, icon));
            selectableWidgetGroup.addWidget(new ImageWidget(15, 0, size.width - 15, 15, new TextTexture(renderingName).setWidth(size.width - 15).setType(TextTexture.TextType.LEFT_HIDE)));
            selectableWidgetGroup.setOnSelected(s -> {
                if (isRemote()) {
                    texture.setSelected(uniqueID);
                }
            });
            selectableWidgetGroup.setSelectedTexture(ColorPattern.WHITE.borderTexture(-1));
            itemList.addWidget(selectableWidgetGroup);
            selectedMap.put(uniqueID, selectableWidgetGroup);
        }
    }

    @Override
    public void detectAndSendChanges() {
        if (gui.getTickCount() % scanTick == 0 && chunkIndex < (chunkRadius * 2 - 1) * (chunkRadius * 2 - 1)) {
            int row = chunkIndex / (chunkRadius * 2 - 1);
            int column = chunkIndex % (chunkRadius * 2 - 1);

            int ox = column - chunkRadius + 1;
            int oz = row - chunkRadius + 1;

            PacketSatelliteProspecting packet = new PacketSatelliteProspecting(centerChunkX + ox, centerChunkZ + oz);
            machine.scanOres(packet.data, this.centerChunkX, this.centerChunkZ);
            writeUpdateInfo(-1, packet::writePacketData);
            chunkIndex++;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void readUpdateInfo(int id, FriendlyByteBuf buffer) {
        if (id == -1) {
            addPacketToQueue(PacketSatelliteProspecting.readPacketData(buffer));
        } else if (id == -2) {
            texture = new SatelliteProspectingTexture(
                    buffer.readVarInt(),
                    buffer.readVarInt(),
                    buffer.readVarInt(),
                    buffer.readVarInt(),
                    gui.entityPlayer.getVisualRotationYInDegrees(), chunkRadius, darkMode, this::getItemColor);
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
                texture.updateTexture(packet.data, new ChunkPos(packet.chunkX, packet.chunkZ));
                addOresToList(packet.data);
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    private void addPacketToQueue(PacketSatelliteProspecting packet) {
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

    public IGuiTexture getItemIcon(BlockState item) {
        return ICON_CACHE.computeIfAbsent(item, state -> {
            if (TagPrefix.ORES.containsKey(ChemicalHelper.getOrePrefix(state).orElse(null))) {
                var mat = ChemicalHelper.getMaterial(state.getBlock().asItem()).material();
                if (mat != null) {
                    var list = new ArrayList<ItemStack>();
                    for (TagPrefix oreTag : TagPrefix.ORES.keySet()) {
                        for (var block : ChemicalHelper.getBlocks(new UnificationEntry(oreTag, mat))) {
                            list.add(new ItemStack(block));
                        }
                    }
                    return new ItemStackTexture(list.toArray(ItemStack[]::new)).scale(0.8f);
                }
            }
            return new ItemStackTexture(new ItemStack(state.getBlock().asItem())).scale(0.8f);
        });
    }

    public int getItemColor(BlockState state) {
        if (TagPrefix.ORES.containsKey(ChemicalHelper.getOrePrefix(state).orElse(null))) {
            var mat = ChemicalHelper.getMaterial(state.getBlock().asItem()).material();
            if (mat != null) {
                return mat.getMaterialRGB();
            }
        }
        return state.getBlock().defaultMapColor().col;
    }

    public String getUniqueID(BlockState state) {
        if (TagPrefix.ORES.containsKey(ChemicalHelper.getOrePrefix(state).orElse(null))) {
            return "material_" + ChemicalHelper.getMaterial(state.getBlock().asItem()).material().getResourceLocation();
        }
        return state.getBlock().getDescriptionId();
    }

    @Override
    public String resultDisplay(BlockState state) {
        if (TagPrefix.ORES.containsKey(ChemicalHelper.getOrePrefix(state).orElse(null))) {
            return ChemicalHelper.getMaterial(state.getBlock().asItem()).material().getUnlocalizedName();
        }
        return state.getBlock().getDescriptionId();
    }

    @Override
    public void selectResult(BlockState item) {
        if (isRemote()) {
            var uid = getUniqueID(item);
            texture.setSelected(uid);
            var selected = selectedMap.get(uid);
            if (selected != null) {
                itemList.setSelected(selected);
            }
        }
    }

    @Override
    public void search(String s, Consumer<BlockState> consumer) {
        var added = new HashSet<String>();
        for (var item : this.items) {
            var id = getUniqueID(item);
            if (!added.contains(id)) {
                added.add(id);
                var localized = LocalizationUtils.format(resultDisplay(item));
                if (item.toString().toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT)) || localized.toLowerCase(Locale.ROOT).contains(s.toLowerCase(Locale.ROOT))) {
                    consumer.accept(item);
                }
            }
        }
    }

    @Override
    public boolean dragging(double mouseX, double mouseY, double deltaX, double deltaY) {
        this.centerChunkX += ((int) deltaX % 16);
        this.centerChunkZ += ((int) deltaY % 16);
        writeUpdateInfo(-2, buf -> {
            buf.writeVarInt(this.centerChunkX);
            buf.writeVarInt(this.centerChunkZ);
            buf.writeVarInt(this.centerChunkX * 16);
            buf.writeVarInt(this.centerChunkZ * 16);
        });
        return super.dragging(mouseX, mouseY, deltaX, deltaY);
    }
}
