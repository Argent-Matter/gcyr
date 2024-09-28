package argent_matter.gcyr.common.machine.multiblock;

import argent_matter.gcyr.common.data.GCYREntities;
import argent_matter.gcyr.common.data.GCYRItems;
import argent_matter.gcyr.common.entity.RocketEntity;
import argent_matter.gcyr.common.item.KeyCardBehaviour;
import argent_matter.gcyr.common.item.PlanetIdChipBehaviour;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ButtonWidget;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;
import com.lowdragmc.lowdraglib.gui.widget.SlotWidget;
import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.mojang.datafixers.util.Pair;
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RocketScannerMachine extends PlatformMultiblockMachine implements IDisplayUIMachine {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(RocketScannerMachine.class, PlatformMultiblockMachine.MANAGED_FIELD_HOLDER);

    @Persisted @DescSynced
    @Getter
    private boolean rocketBuilt;
    @Getter
    private final ItemStackTransfer posSaveSlot;

    public RocketScannerMachine(IMachineBlockEntity holder) {
        super(holder);
        this.posSaveSlot = new ItemStackTransfer(1);
        this.posSaveSlot.setFilter(GCYRItems.ID_CHIP::isIn);
    }

    @Override
    public ModularUI createUI(Player entityPlayer) {
        ModularUI modularUI = super.createUI(entityPlayer);
        modularUI.registerCloseListener(() -> {
            Block.popResource(getLevel(), getPos(), posSaveSlot.getStackInSlot(0));
        });
        modularUI.widget(new SlotWidget(posSaveSlot, 0, 149, 105));
        modularUI.widget(new ButtonWidget(129, 105, 18, 18, this::onSaveButtonClick)
                .setButtonTexture(GuiTextures.BUTTON)
                .setHoverTooltips(Component.translatable("menu.gcyr.save_destination_position")));
        return modularUI;
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (!this.isFormed) {
            MutableComponent base = Component.translatable("gtceu.multiblock.invalid_structure")
                    .withStyle(ChatFormatting.RED);
            Component hover = Component.translatable("gtceu.multiblock.invalid_structure.tooltip")
                    .withStyle(ChatFormatting.GRAY);
            textList.add(base
                    .withStyle(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover))));
            return;
        }

        textList.add(ComponentPanelWidget.withButton(Component.translatable("gcyr.multiblock.rocket.build").withStyle(ChatFormatting.GREEN), "build_rocket"));
    }

    @Override
    public void handleDisplayClick(String componentData, ClickData clickData) {
        if (!clickData.isRemote) {
            if (componentData.equals("build_rocket")) {
                setRocketBuilt(true);
            }
        }
    }

    private void onSaveButtonClick(ClickData data) {
        if (data.isRemote) return;

        ItemStack saveStack = this.posSaveSlot.getStackInSlot(0);
        if (GCYRItems.ID_CHIP.isIn(saveStack)) {
            Direction back = this.getFrontFacing().getOpposite();
            BlockPos landPos = getPos().relative(back, bDist / 2);
            ResourceKey<Level> level = this.getLevel().dimension();
            PlanetIdChipBehaviour.setSavedPosition(saveStack, level, landPos);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    public void setRocketBuilt(boolean rocketBuilt) {
        this.rocketBuilt = rocketBuilt && isFormed;
        if (getLevel().isClientSide || !this.isFormed) return;

        boolean isNegative = this.getFrontFacing().getAxisDirection() == Direction.AxisDirection.NEGATIVE;
        Direction back = this.getFrontFacing().getOpposite();
        BlockPos current = getPos().relative(back, 1);
        int startX = current.getX() + (isNegative ? -1 : 1);
        int endX = current.getX() - (isNegative ? -(bDist - 1) : (bDist - 1));
        int startZ = current.getZ() - (isNegative ? -(lDist + 1) : (lDist + 1));
        int endZ = current.getZ() - (isNegative ? (rDist - 1) : -(rDist - 1));
        int startY = current.getY();
        int endY = current.getY() + hDist;

        AABB bounds = new AABB(startX, startY, startZ, endX, endY, endZ);
        startX = (int) bounds.minX;
        endX = (int) bounds.maxX;
        startY = (int) bounds.minY;
        endY = (int) bounds.maxY;
        startZ = (int) bounds.minZ;
        endZ = (int) bounds.maxZ;

        if (this.rocketBuilt) {
            List<RocketEntity> rockets = getLevel().getEntitiesOfClass(RocketEntity.class, bounds);
            if (!rockets.isEmpty()) {
                return;
            }

            boolean allAir = true;
            BlockPos startPos = new BlockPos(startX, startY, startZ);
            RocketEntity rocket = GCYREntities.ROCKET.create(this.getLevel());

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

            // sort by descending Y to avoid duping seats etc. when building
            // because BlockPos.betweenClosed has to be ascending Y
            states = states.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
            for (var entry : states.entrySet()) {
                BlockPos pos = entry.getKey();
                BlockState state = entry.getValue().getFirst();
                @Nullable CompoundTag entityTag = entry.getValue().getSecond();
                rocket.addBlock(pos.subtract(startPos), state, entityTag);
                getLevel().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            }
            rocket.setPos(startPos.getX(), startPos.getY(), startPos.getZ());
            this.getLevel().addFreshEntity(rocket);
        }
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
