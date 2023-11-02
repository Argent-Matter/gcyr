package argent_matter.gcyr.common.machine.multiblock;

import argent_matter.gcyr.api.capability.GCyRCapabilityHelper;
import argent_matter.gcyr.api.capability.ISpaceStationHolder;
import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.common.data.GCyRItems;
import argent_matter.gcyr.common.item.KeyCardBehaviour;
import argent_matter.gcyr.common.item.StationContainerBehaviour;
import argent_matter.gcyr.data.loader.PlanetData;
import argent_matter.gcyr.util.PosWithState;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.UITemplate;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ButtonWidget;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.SlotWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class SpaceStationPackagerMachine extends PlatformMultiblockMachine {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(SpaceStationPackagerMachine.class, PlatformMultiblockMachine.MANAGED_FIELD_HOLDER);

    @Getter
    @Persisted
    private final ItemStackTransfer packageSlot, keycardSlot, outputSlots;

    public SpaceStationPackagerMachine(IMachineBlockEntity holder) {
        super(holder);
        this.packageSlot = new ItemStackTransfer(1);
        this.packageSlot.setFilter(GCyRItems.SPACE_STATION_PACKAGE::isIn);
        this.keycardSlot = new ItemStackTransfer(1);
        this.outputSlots = new ItemStackTransfer(2);
    }

    @Override
    public ModularUI createUI(Player entityPlayer) {
        ModularUI modularUI = new ModularUI(176, 166, this, entityPlayer).background(GuiTextures.BACKGROUND);
        modularUI.widget(new LabelWidget(4, 5, self().getBlockState().getBlock().getDescriptionId()));

        WidgetGroup buttons = new WidgetGroup(7, 24, 0, 0);
        //buttons.addWidget(new ButtonWidget(0, 0, 80, 24, GuiTextures.BUTTON.copy().setColor(0xFFAA0000), this::onBuildButtonClick));
        buttons.addWidget(new ButtonWidget(0, 24+18, 80, 24, GuiTextures.BUTTON.copy().setColor(0xFFAA0000), this::onBuildButtonClick));
        modularUI.widget(buttons);

        WidgetGroup slots = new WidgetGroup(128, 24, 0, 0);
        slots.addWidget(new SlotWidget(packageSlot, 0, 0, 0));
        slots.addWidget(new SlotWidget(keycardSlot, 0, 18, 0));
        slots.addWidget(new SlotWidget(outputSlots, 0, 0, 22, true, false));
        slots.addWidget(new SlotWidget(outputSlots, 1, 18, 22, true, false));
        modularUI.widget(slots);

        modularUI.widget(UITemplate.bindPlayerInventory(entityPlayer.getInventory(), GuiTextures.SLOT, 7, 134, true));
        return modularUI;
    }

    private void onBuildButtonClick(ClickData data) {
        if (getLevel().isClientSide || !this.isFormed) return;
        if (!outputSlots.getStackInSlot(0).isEmpty() || !outputSlots.getStackInSlot(1).isEmpty()
                || !GCyRItems.SPACE_STATION_PACKAGE.isIn(packageSlot.getStackInSlot(0))
                || !GCyRItems.ID_CHIP.isIn(keycardSlot.getStackInSlot(0))) {
            return;
        }
        ISpaceStationHolder spaceStationHolder = GCyRCapabilityHelper.getSpaceStations((ServerLevel) this.getLevel());
        if (spaceStationHolder == null) return;
        Planet thisPlanet = PlanetData.getPlanetFromLevel(this.getLevel().dimension()).orElse(null);
        if (thisPlanet == null) return;

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

        int tmp = startX;
        startX = Math.min(startX, endX);
        endX = Math.max(tmp, endX);
        tmp = startY;
        startY = Math.min(startY, endY);
        endY = Math.max(tmp, endY);
        tmp = startZ;
        startZ = Math.min(startZ, endZ);
        endZ = Math.max(tmp, endZ);

        boolean allAir = true;
        BlockPos startPos = BlockPos.ZERO;

        Set<PosWithState> blocks = new HashSet<>();
        Map<BlockPos, BlockState> states = new HashMap<>();
        for (BlockPos pos : BlockPos.betweenClosed(startX, startY, startZ, endX, endY, endZ)) {
            BlockState state = this.getLevel().getBlockState(pos);
            if (state.isAir()) continue;
            else if (allAir) startPos = pos.immutable();
            allAir = false;
            states.put(pos.immutable(), state);
            if (startPos.compareTo(pos) < 0) startPos = new BlockPos(
                    Math.min(startPos.getX(), pos.getX()),
                    Math.min(startPos.getY(), pos.getY()),
                    Math.min(startPos.getZ(), pos.getZ()));
        }
        if (allAir) return;

        for (Map.Entry<BlockPos, BlockState> entry : states.entrySet()) {
            BlockPos pos = entry.getKey();
            BlockState state = entry.getValue();
            blocks.add(new PosWithState(pos.subtract(startPos), state));
            getLevel().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }

        packageSlot.setStackInSlot(0, ItemStack.EMPTY);
        ItemStack packageStack = GCyRItems.SPACE_STATION_PACKAGE.asStack();
        StationContainerBehaviour.setSatelliteBlocks(packageStack, blocks);
        outputSlots.setStackInSlot(0, packageStack);

        ItemStack keycardStack = GCyRItems.KEYCARD.asStack();
        KeyCardBehaviour.setSavedStation(keycardStack, spaceStationHolder.allocateStation(thisPlanet).getFirst(), thisPlanet);
        outputSlots.setStackInSlot(1, keycardStack);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
