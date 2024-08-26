package argent_matter.gcyr.common.machine.multiblock;

import argent_matter.gcyr.api.space.planet.Planet;
import argent_matter.gcyr.common.data.GCyREntities;
import argent_matter.gcyr.common.data.GCyRItems;
import argent_matter.gcyr.common.entity.RocketEntity;
import argent_matter.gcyr.common.item.KeyCardBehaviour;
import argent_matter.gcyr.common.item.PlanetIdChipBehaviour;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
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
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

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
    @Persisted
    private final ItemStackTransfer configSaveSlot, configLoadSlot;

    public RocketScannerMachine(IMachineBlockEntity holder) {
        super(holder);
        this.configSaveSlot = new ItemStackTransfer(1);
        this.configSaveSlot.setFilter(GCyRItems.ID_CHIP::isIn);

        this.configLoadSlot = new ItemStackTransfer(1);
        this.configLoadSlot.setFilter(GCyRItems.KEYCARD::isIn);
    }

    @Override
    public ModularUI createUI(Player entityPlayer) {
        ModularUI modularUI = super.createUI(entityPlayer);
        modularUI.widget(new SlotWidget(configSaveSlot, 0, 149, 83));
        modularUI.widget(new SlotWidget(configLoadSlot, 0, 149, 105));
        modularUI.widget(new ButtonWidget(129, 83, 18, 18, this::onSaveButtonClick)
                .setButtonTexture(GuiTextures.BUTTON)
                .setHoverTooltips(Component.translatable("menu.gcyr.save_destination_station")));
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

        ItemStack saveStack = this.configSaveSlot.getStackInSlot(0);
        if (GCyRItems.ID_CHIP.isIn(saveStack)) {
            Planet planet = PlanetIdChipBehaviour.getPlanetFromStack(saveStack);
            if (planet == null) return;

            ItemStack keyCardStack = GCyRItems.KEYCARD.asStack(1);
            KeyCardBehaviour.setSavedStation(keyCardStack, PlanetIdChipBehaviour.getSpaceStationId(saveStack), planet);
            this.configLoadSlot.setStackInSlot(0, keyCardStack);
        }
    }

    public void setRocketBuilt(boolean rocketBuilt) {
        this.rocketBuilt = rocketBuilt && isFormed;
        if (getLevel().isClientSide || !this.isFormed) return;

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

        if (this.rocketBuilt) {
            List<RocketEntity> rockets = getLevel().getEntitiesOfClass(RocketEntity.class, bounds);
            if (!rockets.isEmpty()) {
                return;
            }

            boolean allAir = true;
            BlockPos startPos = new BlockPos(startX, startY, startZ);
            RocketEntity rocket = GCyREntities.ROCKET.create(this.getLevel());

            LinkedHashMap<BlockPos, BlockState> states = new LinkedHashMap<>();
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

            // sort by descending Y to avoid duping seats etc. when building
            // because BlockPos.betweenClosed has to be ascending Y
            states = states.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
            for (Map.Entry<BlockPos, BlockState> entry : states.entrySet()) {
                BlockPos pos = entry.getKey();
                BlockState state = entry.getValue();
                rocket.addBlock(pos.subtract(startPos), state);
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
