package argent_matter.gcyr.common.machine.multiblock;

import argent_matter.gcyr.api.capability.GCYRCapabilityHelper;
import argent_matter.gcyr.api.capability.ISpaceStationHolder;
import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.common.data.GCYRItems;
import argent_matter.gcyr.common.item.KeyCardBehaviour;
import argent_matter.gcyr.common.item.StationContainerBehaviour;
import argent_matter.gcyr.data.loader.PlanetData;
import argent_matter.gcyr.util.PosWithState;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.UITemplate;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup;
import com.lowdragmc.lowdraglib.gui.texture.TextTexture;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ButtonWidget;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.SlotWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.mojang.datafixers.util.Pair;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SpaceStationPackagerMachine extends PlatformMultiblockMachine {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(SpaceStationPackagerMachine.class, PlatformMultiblockMachine.MANAGED_FIELD_HOLDER);

    @Getter
    @Persisted
    private final ItemStackTransfer packageSlot, keycardSlot, outputSlots;

    public SpaceStationPackagerMachine(IMachineBlockEntity holder) {
        super(holder);
        this.packageSlot = new ItemStackTransfer(1);
        this.packageSlot.setFilter(GCYRItems.SPACE_STATION_PACKAGE::isIn);
        this.keycardSlot = new ItemStackTransfer(1);
        this.outputSlots = new ItemStackTransfer(2);
    }

    @Override
    public ModularUI createUI(Player entityPlayer) {
        ModularUI modularUI = new ModularUI(176, 166, this, entityPlayer).background(GuiTextures.BACKGROUND);
        modularUI.widget(new LabelWidget(4, 5, self().getBlockState().getBlock().getDescriptionId()));

        WidgetGroup buttons = new WidgetGroup(7, 16, 0, 0);
        buttons.addWidget(new ButtonWidget(0, 24+18, 80, 24,
                new GuiTextureGroup(GuiTextures.BUTTON.copy().setColor(0xFFAA0000), new TextTexture("gcyr.multiblock.space_station.pack")),
                this::onBuildButtonClick));
        modularUI.widget(buttons);

        WidgetGroup slots = new WidgetGroup(128, 24, 0, 0);
        slots.addWidget(new SlotWidget(packageSlot, 0, 0, 0)
                .setHoverTooltips(Component.translatable("gcyr.multiblock.space_station.package_slot.tooltip")));
        slots.addWidget(new SlotWidget(keycardSlot, 0, 18, 0)
                .setHoverTooltips(Component.translatable("gcyr.multiblock.space_station.keycard_slot.tooltip")));
        slots.addWidget(new SlotWidget(outputSlots, 0, 0, 22, true, false));
        slots.addWidget(new SlotWidget(outputSlots, 1, 18, 22, true, false));
        modularUI.widget(slots);

        modularUI.widget(UITemplate.bindPlayerInventory(entityPlayer.getInventory(), GuiTextures.SLOT, 7, 86, true));
        return modularUI;
    }

    private void onBuildButtonClick(ClickData data) {
        if (getLevel().isClientSide || !this.isFormed) return;
        if (!outputSlots.getStackInSlot(0).isEmpty() || !outputSlots.getStackInSlot(1).isEmpty()
                || !GCYRItems.SPACE_STATION_PACKAGE.isIn(packageSlot.getStackInSlot(0))
                || !GCYRItems.ID_CHIP.isIn(keycardSlot.getStackInSlot(0))) {
            return;
        }
        ISpaceStationHolder spaceStationHolder = GCYRCapabilityHelper.getSpaceStations((ServerLevel) this.getLevel());
        if (spaceStationHolder == null) return;
        Planet thisPlanet = PlanetData.getPlanetFromLevel(this.getLevel().dimension()).orElse(null);
        if (thisPlanet == null) return;

        boolean isZAxis = this.getFrontFacing().getAxis() == Direction.Axis.Z;
        Direction back = this.getFrontFacing().getOpposite();
        Direction left = this.getFrontFacing().getCounterClockWise();
        Direction right = left.getOpposite();
        BlockPos current = getPos().relative(back, 1);
        int startX = current.get(back.getAxis());
        int endX = current.relative(back, bDist - 1).get(back.getAxis());
        int startZ = current.relative(left, lDist).get(left.getAxis());
        int endZ = current.relative(right, rDist).get(right.getAxis());
        int startY = current.getY();
        int endY = current.offset(0, hDist, 0).getY();

        if (isZAxis) {
            // swap x & z coords if we're on the Z axis
            int temp = startX;
            startX = startZ;
            startZ = temp;
            temp = endX;
            endX = endZ;
            endZ = temp;
        }

        AABB bounds = new AABB(startX, startY, startZ, endX, endY, endZ);
        startX = (int) bounds.minX;
        endX = (int) bounds.maxX;
        startY = (int) bounds.minY;
        endY = (int) bounds.maxY;
        startZ = (int) bounds.minZ;
        endZ = (int) bounds.maxZ;

        boolean allAir = true;
        BlockPos startPos = BlockPos.ZERO;

        Set<PosWithState> blocks = new HashSet<>();
        LinkedHashMap<BlockPos, Pair<BlockState, CompoundTag>> states = new LinkedHashMap<>();
        for (BlockPos pos : BlockPos.betweenClosed(startX, startY, startZ, endX, endY, endZ)) {
            BlockState state = this.getLevel().getBlockState(pos);
            if (state.isAir()) continue;
            else if (allAir) startPos = pos.immutable();
            allAir = false;

            CompoundTag entityTag = null;
            BlockEntity entity = this.getLevel().getBlockEntity(pos);
            if (entity != null) {
                entityTag = entity.saveWithId();
            }
            states.put(pos.immutable(), Pair.of(state, entityTag));
            if (startPos.compareTo(pos) < 0) startPos = new BlockPos(
                    Math.min(startPos.getX(), pos.getX()),
                    Math.min(startPos.getY(), pos.getY()),
                    Math.min(startPos.getZ(), pos.getZ()));
        }
        if (allAir) return;

        for (var entry : states.entrySet()) {
            BlockPos pos = entry.getKey();
            BlockState state = entry.getValue().getFirst();
            @Nullable CompoundTag entityTag = entry.getValue().getSecond();
            blocks.add(new PosWithState(pos.subtract(startPos), state, entityTag));
            getLevel().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }

        packageSlot.setStackInSlot(0, ItemStack.EMPTY);
        ItemStack packageStack = GCYRItems.SPACE_STATION_PACKAGE.asStack();
        StationContainerBehaviour.setStationBlocks(packageStack, blocks);
        outputSlots.setStackInSlot(0, packageStack);

        ItemStack keycardStack = GCYRItems.KEYCARD.asStack();
        KeyCardBehaviour.setSavedStation(keycardStack, spaceStationHolder.allocateStation(thisPlanet).getFirst(), thisPlanet);
        outputSlots.setStackInSlot(1, keycardStack);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
