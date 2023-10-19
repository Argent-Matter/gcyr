package argent_matter.gcys.common.entity;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.api.capability.ISpaceStationHolder;
import argent_matter.gcys.api.gui.factory.EntityUIFactory;
import argent_matter.gcys.api.space.planet.Planet;
import argent_matter.gcys.api.space.station.SpaceStation;
import argent_matter.gcys.common.data.*;
import argent_matter.gcys.common.entity.data.EntityTemperatureSystem;
import argent_matter.gcys.common.item.KeyCardBehaviour;
import argent_matter.gcys.common.item.PlanetIdChipBehaviour;
import argent_matter.gcys.data.recipe.GcysTags;
import argent_matter.gcys.util.PlatformUtils;
import argent_matter.gcys.util.PosWithState;
import com.google.common.collect.Sets;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.UITemplate;
import com.lowdragmc.lowdraglib.gui.modular.IUIHolder;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup;
import com.lowdragmc.lowdraglib.gui.texture.TextTexture;
import com.lowdragmc.lowdraglib.gui.widget.ButtonWidget;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.SlotWidget;
import com.lowdragmc.lowdraglib.gui.widget.TankWidget;
import com.lowdragmc.lowdraglib.misc.FluidStorage;
import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class RocketEntity extends Entity implements HasCustomInventoryScreen, IUIHolder {
    public static final EntityDataAccessor<Boolean> ROCKET_STARTED = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Long> REQUIRED_FUEL_AMOUNT = SynchedEntityData.defineId(RocketEntity.class, GcysEntityDataSerializers.LONG);
    public static final EntityDataAccessor<Integer> THRUSTER_COUNT = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> WEIGHT = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> START_TIMER = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<List<PosWithState>> POSITIONED_STATES = SynchedEntityData.defineId(RocketEntity.class, GcysEntityDataSerializers.POSITIONED_BLOCK_STATE_LIST);
    private static final EntityDataAccessor<BlockPos> SIZE = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<List<BlockPos>> SEAT_POSITIONS = SynchedEntityData.defineId(RocketEntity.class, GcysEntityDataSerializers.BLOCK_POS_LIST);


    private final FluidStorage fuelTank;
    private final ItemStackTransfer configSlot;
    private Planet destination;
    private boolean destinationIsSpaceStation;

    private final Set<BlockPos> thrusterPositions = new HashSet<>();

    public RocketEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.configSlot = new ItemStackTransfer(1);
        this.configSlot.setFilter(stack -> GcysItems.ID_CHIP.isIn(stack) || GcysItems.KEYCARD.isIn(stack));
        //noinspection deprecation
        this.fuelTank = new FluidStorage(0, fluid -> fluid.getFluid().is(GcysTags.VEHICLE_FUELS));
    }

    public void reinitializeFluidStorage() {
        this.fuelTank.setCapacity((long) (this.getFuelCapacity() * 1.5));
    }

    @Override
    public void tick() {
        super.tick();

        this.rotateRocket();
        this.burnEntities();

        boolean started = this.entityData.get(ROCKET_STARTED);
        if (started && this.consumeFuel()) {
            this.spawnParticle();
            this.startTimerAndFlyMovement();
            this.goToDestination();
        } else if (started) {
            this.fall();
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    @Override
    protected AABB makeBoundingBox() {
        Vec3 pos = this.position();
        BlockPos size = this.entityData.get(SIZE);
        double x = size.getX() + 1;
        double y = size.getY() + 1;
        double z = size.getZ() + 1;
        return new AABB(pos.x, pos.y, pos.z, pos.x + x, pos.y + y, pos.z + z);
    }

    @Override
    public void refreshDimensions() {
        Vec3 pos = this.position();
        super.refreshDimensions();
        this.setPos(pos);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        super.interact(player, hand);
        InteractionResult result = InteractionResult.sidedSuccess(this.level.isClientSide);

        if (!this.level.isClientSide) {
            if (player.isSecondaryUseActive()) {
                this.openCustomInventoryScreen(player);
                return InteractionResult.CONSUME;
            }

            player.startRiding(this);
            return InteractionResult.CONSUME;
        }

        return result;
    }

    @Override
    public ModularUI createUI(Player entityPlayer) {
        return new ModularUI(176, 166, this, entityPlayer)
                .widget(new LabelWidget(7, 7, this.getDisplayName().getString()))
                .widget(new TankWidget(this.fuelTank, 16, 20, 20, 58, true, true).setBackground(GuiTextures.FLUID_TANK_BACKGROUND))
                .widget(new SlotWidget(configSlot, 0, 40, 20, true, true))
                .widget(new ButtonWidget(40, 60, 36, 18, new GuiTextureGroup(GuiTextures.BUTTON.copy().setColor(0xFFAA0000), new TextTexture("menu.gcys.launch")), (clickData) -> this.startRocket()))
                .widget(new ButtonWidget(40, 40, 36, 18, new GuiTextureGroup(GuiTextures.BUTTON.copy().setColor(0xFFE0B900), new TextTexture("gcys.multiblock.rocket.unbuild")), (clickData) -> this.unBuild()))
                .widget(UITemplate.bindPlayerInventory(entityPlayer.getInventory(), GuiTextures.SLOT, 7, 84, true))
                .background(GuiTextures.BACKGROUND);
    }

    @Override
    public void openCustomInventoryScreen(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            EntityUIFactory.INSTANCE.openUI(this, serverPlayer);
        }
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < this.getMaxPassengers();
    }

    public int getMaxPassengers() {
        return getSeatPositions().size();
    }

    @Override
    public void positionRider(Entity passenger) {
        if (this.hasPassenger(passenger)) {
            int passengerIndex = this.getPassengers().indexOf(passenger);
            if (this.getSeatPositions().isEmpty()) {
                passenger.stopRiding();
                return;
            }
            BlockPos seatPos = this.getSeatPositions().get(passengerIndex);
            passenger.setPos(this.getX() + seatPos.getX() + 0.5, this.getY() + seatPos.getY() - 0.5, this.getZ() + seatPos.getZ() + 0.5);
        }
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
        Vec3[] escapeVectors = new Vec3[]{
                getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot()),
                getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() - 22.5F),
                getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() + 22.5F),
                getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() - 45.0F),
                getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() + 45.0F)
        };
        Set<BlockPos> set = Sets.newLinkedHashSet();
        double maxY = this.getBoundingBox().maxY;
        double minY = this.getBoundingBox().minY - 0.5D;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(Vec3 vector : escapeVectors) {
            mutableBlockPos.set(this.getX() + vector.x, maxY, this.getZ() + vector.z);

            for(double d = maxY; d > minY; --d) {
                set.add(mutableBlockPos.immutable());
                mutableBlockPos.move(Direction.DOWN);
            }
        }

        for(BlockPos blockpos : set) {
            if (!this.level.getFluidState(blockpos).is(FluidTags.LAVA)) {
                double floor = this.level.getBlockFloorHeight(blockpos);
                if (DismountHelper.isBlockFloorValid(floor)) {
                    Vec3 vector3d1 = Vec3.upFromBottomCenterOf(blockpos, floor);

                    for(Pose pose : livingEntity.getDismountPoses()) {
                        if (DismountHelper.isBlockFloorValid(this.level.getBlockFloorHeight(blockpos))) {
                            livingEntity.setPose(pose);
                            return vector3d1;
                        }
                    }
                }
            }
        }

        return new Vec3(this.getX(), this.getBoundingBox().maxY, this.getZ());
    }

    public void spawnParticle() {
        Vec3 vec = this.getDeltaMovement();

        if (this.level instanceof ServerLevel serverLevel) {
            for (BlockPos pos : this.thrusterPositions) {
                if (this.getStartTimer() == 200) {
                    for (ServerPlayer p : serverLevel.getServer().getPlayerList().getPlayers()) {
                        serverLevel.sendParticles(p, ParticleTypes.FLAME, true, this.getX() - vec.x + pos.getX(), this.getY() - vec.y - 2.2 + pos.getY(), this.getZ() - vec.z + pos.getZ(), 20, 0.1, 0.1, 0.1, 0.001);
                        serverLevel.sendParticles(p, ParticleTypes.LARGE_SMOKE, true, this.getX() - vec.x + pos.getX(), this.getY() - vec.y - 3.2 + pos.getY(), this.getZ() - vec.z + pos.getZ(), 10, 0.1, 0.1, 0.1, 0.04);
                    }
                } else {
                    for (ServerPlayer p : serverLevel.getServer().getPlayerList().getPlayers()) {
                        serverLevel.sendParticles(p, ParticleTypes.CAMPFIRE_COSY_SMOKE, true, this.getX() - vec.x + pos.getX(), this.getY() - vec.y - 0.1 + pos.getY(), this.getZ() - vec.z + pos.getZ(), 6, 0.1, 0.1, 0.1, 0.023);
                    }
                }
            }
        }
    }

    @Nullable
    public Player getFirstPlayerPassenger() {
        if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof Player player) {

            return player;
        }

        return null;
    }

    public void rotateRocket() {
        Player player = this.getFirstPlayerPassenger();

        if (player != null) {
            if (player.xxa > 0) {
                setEntityRotation(this, 1);
            }

            if (player.xxa < 0) {
                setEntityRotation(this, -1);
            }
        }
    }

    public void startRocket() {
        if (this.isRemote()) return;
        Player player = this.getFirstPlayerPassenger();

        if (player != null) {
            SynchedEntityData data = this.getEntityData();

            ItemStack config = this.configSlot.getStackInSlot(0);
            if (!config.isEmpty() && this.fuelTank.getFluidAmount() >= this.getFuelCapacity()) {
                if (!data.get(RocketEntity.ROCKET_STARTED)) {
                    if (GcysItems.ID_CHIP.isIn(config)) {
                        this.destination = PlanetIdChipBehaviour.getPlanetFromStack(config);
                    } else if (GcysItems.KEYCARD.isIn(config)) {
                        this.destination = KeyCardBehaviour.getSavedPlanet(config);
                    }

                    if (PlanetIdChipBehaviour.getSpaceStationId(config) != SpaceStation.ID_EMPTY || KeyCardBehaviour.getSavedStation(config) != SpaceStation.ID_EMPTY) {
                        this.destinationIsSpaceStation = true;
                    }

                    if (this.destination.rocketTier() >= determineRocketTier() || (!destinationIsSpaceStation && this.getLevel().dimension().location().equals(this.destination.level().location()))) return;
                    data.set(RocketEntity.ROCKET_STARTED, true);
                    //GcysSoundEntries.ROCKET.play(this.level, null, this.getX(), this.getY(), this.getZ(), 1, 1);
                    this.level.playSound(null, this, GcysSoundEntries.ROCKET.getMainEvent(), SoundSource.NEUTRAL, 1, 1);
                }
            } else {
                sendVehicleHasNoFuelMessage(player);
            }
        }
    }

    public void startTimerAndFlyMovement() {
        if (this.getStartTimer() < 200) {
            this.setStartTimer(this.getStartTimer() + 1);
        }

        if (this.getStartTimer() == 200) {
            Vec3 delta = this.getDeltaMovement();
            if (this.getDeltaMovement().y < this.getRocketSpeed() - 0.1) {
                this.setDeltaMovement(delta.x, delta.y + 0.1, delta.z);
            } else {
                this.setDeltaMovement(delta.x, this.getRocketSpeed(), delta.z);
            }
        }
    }

    public void fall() {
        if (this.getStartTimer() < 200) {
            return;
        }
        Vec3 delta = this.getDeltaMovement();
        this.setDeltaMovement(delta.x, delta.y - 0.1, delta.z);
        rocketExplosion();
    }

    public void rocketExplosion() {
        if (this.getStartTimer() == 200) {
            if (this.getDeltaMovement().y > -0.15 && this.isOnGround()) {
                if (!this.level.isClientSide) {
                    this.level.explode(this, this.getX(), this.getBoundingBox().maxY, this.getZ(), 10, true, Explosion.BlockInteraction.BREAK);
                    this.remove(RemovalReason.DISCARDED);
                }
            }
        }
    }

    public void burnEntities() {
        if (this.getStartTimer() == 200) {
            BlockPos size = this.entityData.get(SIZE);
            AABB aabb = AABB.ofSize(new Vec3(this.getX() + size.getX() / 2f, this.getY() - 2, this.getZ() + size.getZ() / 2f), size.getX() + 2, 2, size.getZ() + 2);
            List<LivingEntity> entities = this.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, aabb);

            for (LivingEntity entity : entities) {
                if (!entity.fireImmune() && !entity.hasEffect(MobEffects.FIRE_RESISTANCE) && !EntityTemperatureSystem.armourIsHeatResistant(entity)) {
                    entity.setSecondsOnFire(15);
                }
            }
        }
    }

    private boolean doesDrop(BlockState state, BlockPos pos) {
        if (this.onGround) {

            BlockState state2 = this.level.getBlockState(new BlockPos((int)Math.floor(this.getX()), (int)(this.getY() - 0.2), (int)Math.floor(this.getZ())));

            if (!this.level.isEmptyBlock(pos) && (state2.is(GcysBlocks.LAUNCH_PAD.get()) || !state.is(GcysBlocks.LAUNCH_PAD.get()))) {
                this.unBuild();

                return true;
            }
        }

        return false;
    }

    protected void checkOnBlocks() {
        AABB aabb = this.getBoundingBox();
        BlockPos blockPos1 = new BlockPos((int) aabb.minX, (int) (aabb.minY - 0.2), (int) aabb.minZ);
        BlockPos blockPos2 = new BlockPos((int) aabb.maxX, (int) aabb.minY, (int) aabb.maxZ);

        //noinspection deprecation
        if (this.level.hasChunksAt(blockPos1, blockPos2)) {
            for (int i = blockPos1.getX(); i <= blockPos2.getX(); ++i) {
                for (int j = blockPos1.getY(); j <= blockPos2.getY(); ++j) {
                    for (int k = blockPos1.getZ(); k <= blockPos2.getZ(); ++k) {
                        BlockPos pos = new BlockPos(i, j, k);
                        BlockState state = this.level.getBlockState(pos);

                        if (this.doesDrop(state, pos)) {
                            return;
                        }
                    }
                }
            }
        }
    }

    private boolean consumeFuel() {
        // Fuel Consumption = thruster count * 2
        if (!this.fuelTank.drain(getThrusterCount() * 2L, true).isEmpty()) {
            return !this.fuelTank.drain(getThrusterCount() * 2L, false).isEmpty();
        }
        return false;
    }

    private void goToDestination() {
        if (this.getY() < 600 || this.isRemote()) return;
        ItemStack configStack = configSlot.getStackInSlot(0);
        if (this.destination == null && GcysItems.ID_CHIP.isIn(configStack)) {
            this.destination = PlanetIdChipBehaviour.getPlanetFromStack(configStack);
        } else if (GcysItems.KEYCARD.isIn(configStack) && KeyCardBehaviour.getSavedStation(configStack) != SpaceStation.ID_EMPTY) {
            this.destinationIsSpaceStation = true;
        }
        final ServerLevel destinationLevel;
        if (this.destinationIsSpaceStation) {
            destinationLevel = this.getServer().getLevel(GcysDimensionTypes.SPACE_LEVEL);
        } else {
            destinationLevel = this.getServer().getLevel(destination.level());
        }

        Vec3 pos = this.position();

        List<Entity> passengers = this.getPassengers();
        Entity newRocket = PlatformUtils.changeDimension(this, destinationLevel);
        if (newRocket == null) {
            this.destination = null;
            this.destinationIsSpaceStation = false;
            this.entityData.set(ROCKET_STARTED, false);
            this.setDeltaMovement(0, -0.5, 0);
            return;
        }
        Set<Entity> newPassengers = new HashSet<>();
        passengers.forEach(passenger -> {
            Entity newPassenger = PlatformUtils.changeDimension(passenger, destinationLevel);
            if (newPassenger != null) {
                newPassenger.startRiding(newRocket);
                newPassengers.add(newPassenger);
            } else {
                passenger.startRiding(newRocket);
                newPassengers.add(passenger);
            }
        });

        if (this.destinationIsSpaceStation) {
            ISpaceStationHolder stations = GcysCapabilityHelper.getSpaceStations(destinationLevel);
            int stationId;
            boolean didChange = false;
            if (GcysItems.KEYCARD.isIn(configStack)) {
                stationId = KeyCardBehaviour.getSavedStation(configStack);
                if (stations.getStation(stationId) == null) {
                    Pair<Integer, SpaceStation> allocated = stations.allocateStation(this.destination);
                    stations.addStation(allocated.getFirst(), allocated.getSecond());
                    stationId = allocated.getFirst();
                    KeyCardBehaviour.setSavedStation(configStack, stationId, KeyCardBehaviour.getSavedPlanet(configStack));
                    didChange = true;
                }
            } else if (GcysItems.ID_CHIP.isIn(configStack)) {
                stationId = PlanetIdChipBehaviour.getSpaceStationId(configStack);
                if (stations.getStation(stationId) == null) {
                    Pair<Integer, SpaceStation> allocated = stations.allocateStation(this.destination);
                    stations.addStation(allocated.getFirst(), allocated.getSecond());
                    stationId = allocated.getFirst();
                    PlanetIdChipBehaviour.setSpaceStation(configStack, stationId);
                    didChange = true;
                }
            } else {
                stationId = SpaceStation.ID_EMPTY;
            }

            if (didChange) {
                newPassengers.forEach(entity -> {
                    if (entity instanceof Player player) {
                        player.sendSystemMessage(Component.translatable("message.gcys.notice_id_changed"));
                    }
                });
            }

            BlockPos stationPos = stations.getStationWorldPos(stationId);
            newRocket.setPos(stationPos.getX(), pos.y, stationPos.getZ());
        } else {
            double scale = DimensionType.getTeleportationScale(this.level.dimensionType(), destinationLevel.dimensionType());
            newRocket.setPos(pos.multiply(scale, 1, scale));
        }

        Vec3 delta = this.getDeltaMovement();
        newRocket.setDeltaMovement(delta.x, -0.5, delta.z);
        if (newRocket instanceof RocketEntity rocketEntity) {
            rocketEntity.destination = null;
            rocketEntity.destinationIsSpaceStation = false;
            rocketEntity.entityData.set(ROCKET_STARTED, false);
            rocketEntity.entityData.set(START_TIMER, 0);
        }
    }

    public void unBuild() {
        if (this.level.isClientSide) return;

        if (!configSlot.getStackInSlot(0).isEmpty())
            this.spawnAtLocation(configSlot.getStackInSlot(0));

        BlockPos origin = this.blockPosition();
        for (PosWithState state : this.getBlocks()) {
            BlockPos offset = origin.offset(state.pos());
            BlockHitResult result = new BlockHitResult(
                    new Vec3(
                            offset.getX() + 0.5,
                            offset.getY() + 0.5,
                            offset.getZ() + 0.5
                    ),
                    Direction.DOWN,
                    offset,
                    false
            );
            if (!this.level.getBlockState(offset).isAir() && !this.level.getBlockState(offset).canBeReplaced(new BlockPlaceContext(this.level, null, InteractionHand.MAIN_HAND, ItemStack.EMPTY, result))) {
                this.spawnAtLocation(state.state().getBlock().asItem());
                continue;
            }
            this.level.setBlock(offset, state.state(), Block.UPDATE_ALL);
        }

        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public void push(Entity entity) {

    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    public long getFuelCapacity() {
        return this.entityData.get(REQUIRED_FUEL_AMOUNT);
    }

    public void setFuelCapacity(long fuelCapacity) {
        this.entityData.set(REQUIRED_FUEL_AMOUNT, fuelCapacity);
        this.reinitializeFluidStorage();
    }

    public int getThrusterCount() {
        return this.entityData.get(THRUSTER_COUNT);
    }

    public void setThrusterCount(int count) {
        this.entityData.set(THRUSTER_COUNT, count);
    }

    public int getWeight() {
        return this.entityData.get(WEIGHT);
    }

    public void setWeight(int weight) {
        this.entityData.set(WEIGHT, weight);
    }

    public int getStartTimer() {
        return this.entityData.get(START_TIMER);
    }
    
    public void setStartTimer(int timer) {
        this.entityData.set(START_TIMER, timer);
    }
    
    public void addBlock(BlockPos pos, BlockState state) {
        this.addBlock(new PosWithState(pos, state));
    }

    public void addBlock(PosWithState state) {
        List<PosWithState> blocks = this.getBlocks();
        if (blocks.stream().anyMatch(pws -> pws.pos().equals(state.pos()))) {
            return;
        }

        blocks.add(state);
        this.entityData.set(POSITIONED_STATES, blocks);
        BlockPos pos = state.pos();
        BlockPos size = this.entityData.get(SIZE);
        this.entityData.set(SIZE, new BlockPos(
                        Math.max(size.getX(), pos.getX()),
                        Math.max(size.getY(), pos.getY()),
                        Math.max(size.getZ(), pos.getZ())
        ));
        float destroyTime = state.state().getBlock().defaultDestroyTime();
        if (destroyTime > 0) {
            this.setWeight(this.getWeight() + (int) (destroyTime / 2.5f));
        }
        if (state.state().is(GcysBlocks.ROCKET_MOTOR.get())) {
            this.setThrusterCount(this.getThrusterCount() + 1);
            this.thrusterPositions.add(pos);
        } else if (state.state().is(GcysBlocks.FUEL_TANK.get())) {
            this.setFuelCapacity(this.getFuelCapacity() + 5 * FluidHelper.getBucket());
        } else if (state.state().is(GcysBlocks.SEAT.get())) {
            this.addSeatPos(pos);
        }
        this.setBoundingBox(makeBoundingBox());
    }

    public List<PosWithState> getBlocks() {
        return this.entityData.get(POSITIONED_STATES);
    }

    public void addSeatPos(BlockPos pos) {
        List<BlockPos> seats = this.entityData.get(SEAT_POSITIONS);
        seats.add(pos);
        this.entityData.set(SEAT_POSITIONS, seats);
    }

    public List<BlockPos> getSeatPositions() {
        return this.entityData.get(SEAT_POSITIONS);
    }

    public double getRocketSpeed() {
        return this.getThrusterCount() * 4.0 - (getWeight() + 1);
    }

    public int determineRocketTier() {
        return this.getThrusterCount() * 2 + (int) getFuelCapacity() * 2 - (getBlocks().size() * 3);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(ROCKET_STARTED, false);
        this.entityData.define(REQUIRED_FUEL_AMOUNT, 0L);
        this.entityData.define(THRUSTER_COUNT, 0);
        this.entityData.define(WEIGHT, 0);
        this.entityData.define(START_TIMER, 0);
        this.entityData.define(POSITIONED_STATES, new ArrayList<>());
        this.entityData.define(SEAT_POSITIONS, new ArrayList<>());
        this.entityData.define(SIZE, BlockPos.ZERO);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.getBlocks().clear();
        ListTag blocks = compound.getList("blocks", Tag.TAG_COMPOUND);
        for (int i = 0; i < blocks.size(); ++i) {
            this.addBlock(PosWithState.readFromTag(blocks.getCompound(i)));
        }
        /*
        this.getSeatPositions().clear();
        ListTag seats = compound.getList("seats", Tag.TAG_COMPOUND);
        for (int i = 0; i < seats.size(); ++i) {
            this.addSeatPos(NbtUtils.readBlockPos(seats.getCompound(i)));
        }
         */

        this.setFuelCapacity(compound.getLong("fuelCapacity"));
        this.fuelTank.setFluid(FluidStack.loadFromTag(compound.getCompound("fuel")));
        this.configSlot.deserializeNBT(compound.getCompound("config"));
        this.setThrusterCount(compound.getInt("thrusterCount"));
        this.setStartTimer(compound.getInt("startTimer"));
        this.entityData.set(ROCKET_STARTED, compound.getBoolean("isStarted"));
        this.setWeight(compound.getInt("weight"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        var blocks = this.getBlocks();
        ListTag blockTag = new ListTag();
        compound.put("blocks", blockTag);
        for (PosWithState state : blocks) {
            blockTag.add(state.writeToTag());
        }
        /*
        var seats = this.getSeatPositions();
        ListTag seatsTag = new ListTag();
        compound.put("seat", seatsTag);
        for (BlockPos seatPos : seats) {
            seatsTag.add(NbtUtils.writeBlockPos(seatPos));
        }
         */

        compound.putLong("fuelCapacity", this.getFuelCapacity());
        CompoundTag fuel = new CompoundTag();
        fuelTank.getFluid().saveToTag(fuel);
        compound.put("fuel", fuel);
        compound.put("config", this.configSlot.serializeNBT());
        compound.putInt("thrusterCount", this.getThrusterCount());
        compound.putInt("startTimer", this.getStartTimer());
        compound.putBoolean("isStarted", this.entityData.get(ROCKET_STARTED));
        compound.putInt("weight", this.getWeight());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (POSITIONED_STATES.equals(key) || SIZE.equals(key)) {
            this.setBoundingBox(makeBoundingBox());
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    public static void setEntityRotation(Entity vehicle, float rotation) {
        vehicle.setYRot(vehicle.getYRot() + rotation);
        vehicle.setYBodyRot(vehicle.getYRot());
        vehicle.yRotO = vehicle.getYRot();
    }

    public static void sendVehicleHasNoFuelMessage(Player player) {
        if (!player.level.isClientSide) {
            player.displayClientMessage(Component.translatable("message." + GregicalitySpace.MOD_ID + ".no_fuel"), false);
        }
    }

    @Override
    public boolean isInvalid() {
        return this.isRemoved();
    }

    @Override
    public boolean isRemote() {
        return this.level.isClientSide;
    }

    @Override
    public void markAsDirty() {

    }
}
