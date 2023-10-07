package argent_matter.gcys.common.machine.electric;

import argent_matter.gcys.common.data.GcysBlocks;
import argent_matter.gcys.common.data.GcysEntities;
import argent_matter.gcys.common.entity.RocketEntity;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.lowdragmc.lowdraglib.gui.texture.TextTexture;
import com.lowdragmc.lowdraglib.gui.widget.ButtonWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RocketScannerMachine extends MetaMachine implements IFancyUIMachine {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(RocketScannerMachine.class, MetaMachine.MANAGED_FIELD_HOLDER);

    @Persisted @DescSynced
    @Getter
    private boolean built;

    public RocketScannerMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public Widget createUIWidget() {
        WidgetGroup group = new WidgetGroup();
        group.addWidget(new ButtonWidget(15, 15, 48, 18, new TextTexture("Unbuild"), clickData -> setBuilt(false)).setBackground(GuiTextures.BUTTON.copy().setColor(0xAA0000)));
        group.addWidget(new ButtonWidget(15, 37, 48, 18, new TextTexture("Build"), clickData -> setBuilt(true)).setBackground(GuiTextures.BUTTON.copy().setColor(0x00AA00)));

        return group;
    }

    public void setBuilt(boolean built) {
        this.built = built;
        if (getLevel().isClientSide) return;

        Direction behind = this.getFrontFacing().getOpposite();
        BlockPos start = this.getPos().relative(behind, 1).relative(this.getFrontFacing().getCounterClockWise(), 1);
        BlockPos end = this.getPos().relative(behind, 3).relative(this.getFrontFacing().getClockWise(), 1);

        if (this.built && checkLaunchPlatform()) {
            RocketEntity rocket = GcysEntities.ROCKET.create(this.getLevel());
            rocket.setPos(end.getX() + getFrontFacing().getStepX(), end.getY(), end.getZ() + getFrontFacing().getStepZ());
            for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
                BlockState state = this.getLevel().getBlockState(pos);
                rocket.addBlock(pos, state);
            }
            this.getLevel().addFreshEntity(rocket);
        } else {
            List<RocketEntity> rockets = getLevel().getEntitiesOfClass(RocketEntity.class, new AABB(start, end), entity -> true);
            if (!rockets.isEmpty()) {
                RocketEntity rocket = rockets.get(0);
                rocket.unBuild();
            }
        }
    }

    public boolean checkLaunchPlatform() {
        Direction behind = this.getFrontFacing().getOpposite();
        BlockPos start = this.getPos().relative(behind, 1).relative(this.getFrontFacing().getCounterClockWise(), 1).relative(Direction.DOWN, 1);
        BlockPos end = this.getPos().relative(behind, 3).relative(this.getFrontFacing().getClockWise(), 1).relative(Direction.DOWN, 1);

        for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
            if (!getLevel().getBlockState(pos).is(GcysBlocks.LAUNCH_PAD.get())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
