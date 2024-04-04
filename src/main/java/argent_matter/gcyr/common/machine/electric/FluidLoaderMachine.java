package argent_matter.gcyr.common.machine.electric;

import com.gregtechceu.gtceu.api.capability.IControllable;
import com.gregtechceu.gtceu.api.cover.CoverBehavior;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TieredEnergyMachine;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineModifyDrops;
import com.gregtechceu.gtceu.api.misc.IOFluidTransferList;
import com.gregtechceu.gtceu.common.machine.electric.ChargerMachine;
import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup;
import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;
import com.lowdragmc.lowdraglib.gui.widget.SlotWidget;
import com.lowdragmc.lowdraglib.gui.widget.TankWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.misc.FluidStorage;
import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import com.lowdragmc.lowdraglib.side.fluid.FluidTransferHelper;
import com.lowdragmc.lowdraglib.side.fluid.IFluidTransfer;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.annotation.RequireRerender;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.lowdragmc.lowdraglib.utils.Position;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FluidLoaderMachine extends TieredEnergyMachine implements IControllable, IFancyUIMachine, IMachineModifyDrops {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(FluidLoaderMachine.class, TieredEnergyMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    @Getter
    @Setter
    private boolean isWorkingEnabled;
    @Getter
    @Persisted
    protected final ItemStackTransfer loaderInventory;
    @Getter
    @Persisted
    protected final FluidStorage fluidStorage;

    public FluidLoaderMachine(IMachineBlockEntity holder, int tier, Object... args) {
        super(holder, tier, args);
        this.loaderInventory = createLoaderInventory(args);
        this.fluidStorage = createFluidStorage(tier, args);
    }

    protected ItemStackTransfer createLoaderInventory(Object... args) {
        var itemTransfer = new ItemStackTransfer(1);
        itemTransfer.setFilter(item -> FluidTransferHelper.getFluidTransfer(new ItemStackTransfer(item), 0) != null);
        return itemTransfer;
    }

    @Nullable
    @Override
    public IFluidTransfer getFluidTransferCap(@Nullable Direction side, boolean useCoverCapability) {
        if (!useCoverCapability || side == null) return fluidStorage;

        CoverBehavior cover = getCoverContainer().getCoverAtSide(side);
        return cover != null ? cover.getFluidTransferCap(fluidStorage) : fluidStorage;
    }

    protected FluidStorage createFluidStorage(int tier, Object... args) {
        long capacity = tier * 2500L;
        return new FluidStorage(capacity) {
            @Override
            public long fill(int tank, FluidStack resource, boolean simulate, boolean notifyChanges) {
                IFluidTransfer transfer = FluidTransferHelper.getFluidTransfer(loaderInventory, 0);
                long drained = 0;
                if (transfer != null) {
                    drained = transfer.fill(resource, simulate, notifyChanges);
                }

                FluidStack fluidStack = resource.copy();
                fluidStack.setAmount(fluidStack.getAmount() - drained);
                long filled = super.fill(tank, fluidStack, simulate, notifyChanges);
                return filled + drained;
            }
        };
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public void onDrops(List<ItemStack> drops, Player entity) {
        clearInventory(drops, loaderInventory);
    }

    //////////////////////////////////////
    //**********     GUI     ***********//
    //////////////////////////////////////

    @Override
    public Widget createUIWidget() {
        var template = new WidgetGroup(0, 0, 18 + 8 + 64 + 8, 18 + 8 + 32);

        template.addWidget(new SlotWidget(loaderInventory, 0, 16, 50, true, true)
                .setBackgroundTexture(new GuiTextureGroup(GuiTextures.SLOT, GuiTextures.CANISTER_OVERLAY)));
        template.addWidget(new TankWidget(fluidStorage, 16, 0, 18, 48, true, true)
                .setShowAmount(false).setBackground(GuiTextures.FLUID_SLOT).setFillDirection(ProgressTexture.FillDirection.DOWN_TO_UP));

        var group = new WidgetGroup(0, 0, Math.max(template.getSize().width + 4 + 8, 172), template.getSize().height + 8);
        var size = group.getSize();
        template.setSelfPosition(new Position(
                (size.width - 4 - template.getSize().width) / 2 + 2 + 2,
                (size.height - template.getSize().height) / 2));
        group.addWidget(template);
        return group;
    }
}
