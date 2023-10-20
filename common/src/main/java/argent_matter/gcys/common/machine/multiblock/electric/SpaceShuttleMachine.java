package argent_matter.gcys.common.machine.multiblock.electric;

import argent_matter.gcys.GCyS;
import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.api.capability.ISpaceStationHolder;
import argent_matter.gcys.api.gui.widget.GcysGuiTextures;
import argent_matter.gcys.api.registries.GcysRegistries;
import argent_matter.gcys.api.space.satellite.SatelliteType;
import argent_matter.gcys.api.space.satellite.data.SatelliteData;
import argent_matter.gcys.api.space.station.SpaceStation;
import argent_matter.gcys.common.data.GCySDimensionTypes;
import argent_matter.gcys.common.data.GCySItems;
import argent_matter.gcys.common.data.GCySRecipeTypes;
import argent_matter.gcys.common.item.KeyCardBehaviour;
import argent_matter.gcys.common.item.PlanetIdChipBehaviour;
import argent_matter.gcys.common.worldgen.SpaceLevelSource;
import argent_matter.gcys.data.lang.LangHandler;
import argent_matter.gcys.data.loader.PlanetData;
import argent_matter.gcys.util.Vec2i;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.UITemplate;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.lowdragmc.lowdraglib.syncdata.ISubscription;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public class SpaceShuttleMachine extends WorkableElectricMultiblockMachine {
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(SpaceShuttleMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);
    public static final int SATELLITE_RANGE_MULTIPLIER = 32;

    @Persisted
    public final NotifiableItemStackHandler inventory;
    @Nullable
    protected ISubscription inventorySubs;
    @Nullable
    private UUID currentSatelliteOwnerUUID;

    public SpaceShuttleMachine(IMachineBlockEntity holder) {
        super(holder);
        this.inventory = createInventory();
    }

    @Override
    public boolean isWorkingEnabled() {
        return super.isWorkingEnabled();
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    protected NotifiableItemStackHandler createInventory() {
        return new NotifiableItemStackHandler(this, 1, IO.BOTH);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        inventorySubs = inventory.addChangedListener(this::updatedInventory);
    }

    protected void updatedInventory() {
        var stack = inventory.getStackInSlot(0);
        if (GCySItems.KEYCARD.isIn(stack)) {
            this.currentSatelliteOwnerUUID = KeyCardBehaviour.getOwner(stack);
            this.recipeLogic.setWorkingEnabled(true);
        } else if (GCySItems.ID_CHIP.isIn(stack)) {
            this.currentSatelliteOwnerUUID = null;
            this.recipeLogic.setWorkingEnabled(true);
        } else {
            this.recipeLogic.setWorkingEnabled(false);
        }
    }

    @Override
    protected @Nullable GTRecipe getRealRecipe(GTRecipe recipe) {
        if (recipe.data.contains("satellite_type", Tag.TAG_STRING) && recipe.data.getString("satellite_type").equals("gcys.manned_rocket")) return null;
        if (this.getLevel().dimensionType().hasCeiling()) return null;
        return super.modifyRecipe(recipe);
    }

    @Override
    public void afterWorking() {
        super.afterWorking();
        if (recipeLogic.getLastRecipe() != null) {
            var data = this.recipeLogic.getLastRecipe().data;
            SatelliteType<?> type = data.contains("satellite_type") ? GcysRegistries.SATELLITES.get(new ResourceLocation(data.getString("satellite_type"))) : null;
            BlockPos pos = this.getPos();
            if (!this.getLevel().isClientSide && type != null) {
                GcysCapabilityHelper.getSatellites((ServerLevel) this.getLevel()).addSatellite(type.getFactory().create(type, new SatelliteData(new Vec2i(pos.getX(), pos.getZ()), this.getTier() * SATELLITE_RANGE_MULTIPLIER, currentSatelliteOwnerUUID), this.getLevel().dimension()));
            }
        }
    }

    private static final ResourceLocation mannedLaunchRecipeId = GCyS.id("space_shuttle/manned_launch_station");

    private void launchPlayerToStation(ClickData data, Player player) {
        Level level = player.level();
        var recipe = GCySRecipeTypes.SPACE_SHUTTLE_RECIPES.getRecipe(level.getRecipeManager(), mannedLaunchRecipeId);
        if (!level.isClientSide && recipe.matchRecipe(this).isSuccess() && GCySItems.ID_CHIP.isIn(inventory.getStackInSlot(0))) {
            this.recipeLogic.setupRecipe(recipe);
            int stationId = PlanetIdChipBehaviour.getSpaceStationId(inventory.getStackInSlot(0));
            if (stationId != SpaceStation.ID_EMPTY) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                ServerLevel space = level.getServer().getLevel(GCySDimensionTypes.SPACE_LEVEL);
                ISpaceStationHolder stations = GcysCapabilityHelper.getSpaceStations(space);
                Vec2i pos = stations.getStationPos(stationId);
                if (pos == Vec2i.MAX_NEGATIVE) {
                    pos = stations.getFreeStationPos(stationId);
                    stations.addStation(stationId, new SpaceStation(PlanetData.getPlanetFromLevel(Level.OVERWORLD).orElse(null), pos));
                }
                serverPlayer.teleportTo(space, pos.x(), SpaceLevelSource.PLATFORM_HEIGHT + 1, pos.y(), serverPlayer.getYRot(), serverPlayer.getXRot());
            }
        }
    }

    @Override
    public ModularUI createUI(Player player) {
        var screen = new DraggableScrollableWidgetGroup(4, 4, 162, 121).setBackground(getScreenTexture());
        screen.addWidget(new LabelWidget(4, 5, self().getBlockState().getBlock().getDescriptionId()));
        screen.addWidget(new ComponentPanelWidget(4, 17, this::addDisplayText)
                .setMaxWidthLimit(156)
                .clickHandler(this::handleDisplayClick));
        return new ModularUI(190, 216, this, player)
                .background(GuiTextures.BACKGROUND)
                .widget(new SlotWidget(inventory, 0, 168, 107).setBackgroundTexture(new GuiTextureGroup(GuiTextures.SLOT)))
                .widget(new ButtonWidget(168, 87, 18, 18, GcysGuiTextures.BUTTON_LAUNCH_ROCKET, data -> launchPlayerToStation(data, player)).setHoverTooltips(LangHandler.getMultiLang("gcys.multiblock.space_shuttle.launch").toArray(MutableComponent[]::new)))
                .widget(screen)
                .widget(UITemplate.bindPlayerInventory(player.getInventory(), GuiTextures.SLOT, 7, 134, true));
    }
}
