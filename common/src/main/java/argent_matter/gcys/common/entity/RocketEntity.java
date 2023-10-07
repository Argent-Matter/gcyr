package argent_matter.gcys.common.entity;

import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.api.gui.factory.EntityUIFactory;
import argent_matter.gcys.common.data.GcysBlocks;
import argent_matter.gcys.common.data.GcysEntityDataSerializers;
import argent_matter.gcys.common.data.GcysSoundEntries;
import argent_matter.gcys.common.entity.data.EntityTemperatureSystem;
import argent_matter.gcys.data.recipe.GcysTags;
import argent_matter.gcys.util.PosWithState;
import com.google.common.collect.Sets;
import com.lowdragmc.lowdraglib.gui.modular.IUIHolder;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.TankWidget;
import com.lowdragmc.lowdraglib.misc.FluidStorage;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
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
import net.minecraft.util.Mth;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class RocketEntity extends Entity implements HasCustomInventoryScreen, IUIHolder {
    public static final long DEFAULT_DISTANCE_TRAVELABLE = 13500000L;

    public static final EntityDataAccessor<Boolean> ROCKET_STARTED = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Long> FUEL = SynchedEntityData.defineId(RocketEntity.class, GcysEntityDataSerializers.LONG);
    public static final EntityDataAccessor<Long> FUEL_CAPACITY = SynchedEntityData.defineId(RocketEntity.class, GcysEntityDataSerializers.LONG);
    public static final EntityDataAccessor<Integer> THRUSTER_COUNT = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Long> MAX_DISTANCE_TRAVELABLE = SynchedEntityData.defineId(RocketEntity.class, GcysEntityDataSerializers.LONG);
    public static final EntityDataAccessor<Integer> START_TIMER = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<List<PosWithState>> POSITIONED_STATES = SynchedEntityData.defineId(RocketEntity.class, GcysEntityDataSerializers.POSITIONED_BLOCK_STATE_LIST);
    private static final EntityDataAccessor<List<BlockPos>> SEAT_POSITIONS = SynchedEntityData.defineId(RocketEntity.class, GcysEntityDataSerializers.BLOCK_POS_LIST);


    private final FluidStorage fuelTank;
    private int sizeX, sizeY, sizeZ;
    private int weight;

    public RocketEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        //noinspection deprecation
        this.fuelTank = new FluidStorage(0, fluid -> fluid.getFluid().is(GcysTags.VEHICLE_FUELS)) {
            @Override
            public long fill(int tank, FluidStack resource, boolean simulate, boolean notifyChange) {
                long value = super.fill(tank, resource, simulate, notifyChange);
                RocketEntity.this.setFuel(fluid.getAmount());
                return value;
            }

            @Override
            public void setFluid(FluidStack fluid) {
                super.setFluid(fluid);
                RocketEntity.this.setFuel(fluid.getAmount());
            }
        };
    }

    public void reinitializeFluidStorage() {
        this.fuelTank.setCapacity(this.getFuelCapacity());
    }

    @Override
    public void tick() {
        super.tick();

        this.rotateRocket();
        //this.checkOnBlocks();
        this.rocketExplosion();
        this.burnEntities();

        if (this.entityData.get(ROCKET_STARTED)) {
            this.spawnParticle();
            this.startTimerAndFlyMovement();
        }
    }

    @Override
    protected AABB makeBoundingBox() {
        Vec3 pos = this.position();
        double x = this.sizeX / 2.0;
        double y = this.sizeY;
        double z = this.sizeZ / 2.0;
        return new AABB(pos.x - x, pos.y, pos.z - z, pos.x + x, pos.y + y, pos.z + z);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        super.interact(player, hand);
        InteractionResult result = InteractionResult.sidedSuccess(this.level.isClientSide);

        if (!this.level.isClientSide) {
            if (player.isCrouching()) {
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
                .widget(new TankWidget(this.fuelTank, 13, 13, 20, 58, true, true))
                .widget(new LabelWidget(5, 5, this.getDisplayName().getString()));
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
            BlockPos seatPos = this.getSeatPositions().get(passengerIndex);
            passenger.setPos(this.getX() + seatPos.getX(), this.getY() + seatPos.getY(), this.getZ() + seatPos.getZ());
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
            if (this.getStartTimer() == 200) {
                for (ServerPlayer p : serverLevel.getServer().getPlayerList().getPlayers()) {
                    serverLevel.sendParticles(p, ParticleTypes.FLAME, true, this.getX() - vec.x, this.getY() - vec.y - 2.2, this.getZ() - vec.z, 20, 0.1, 0.1, 0.1, 0.001);
                    serverLevel.sendParticles(p, ParticleTypes.LARGE_SMOKE, true, this.getX() - vec.x, this.getY() - vec.y - 3.2, this.getZ() - vec.z, 10, 0.1, 0.1, 0.1, 0.04);
                }
            } else {
                for (ServerPlayer p : serverLevel.getServer().getPlayerList().getPlayers()) {
                    serverLevel.sendParticles(p, ParticleTypes.CAMPFIRE_COSY_SMOKE, true, this.getX() - vec.x, this.getY() - vec.y - 0.1, this.getZ() - vec.z, 6, 0.1, 0.1, 0.1, 0.023);
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
        Player player = this.getFirstPlayerPassenger();

        if (player != null) {
            SynchedEntityData data = this.getEntityData();

            if (data.get(RocketEntity.FUEL) == this.getFuelCapacity()) {
                if (!data.get(RocketEntity.ROCKET_STARTED)) {
                    data.set(RocketEntity.ROCKET_STARTED, true);
                    GcysSoundEntries.ROCKET.play(this.level, null, this.getX(), this.getY(), this.getZ(), 1, 1);
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
            if (this.getDeltaMovement().y < this.getRocketSpeed() - 0.1) {
                this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + 0.1, this.getDeltaMovement().z);
            } else {
                this.setDeltaMovement(this.getDeltaMovement().x, this.getRocketSpeed(), this.getDeltaMovement().z);
            }
        }
    }

    public void rocketExplosion() {
        if (this.getStartTimer() == 200) {
            if (this.getDeltaMovement().y < -0.07) {
                if (!this.level.isClientSide) {
                    this.level.explode(this, this.getX(), this.getBoundingBox().maxY, this.getZ(), 10, true, Explosion.BlockInteraction.BREAK);
                    this.remove(RemovalReason.DISCARDED);
                }
            }
        }
    }

    public void burnEntities() {
        if (this.getStartTimer() == 200) {
            AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY() - 2, this.getZ()), 2, 2, 2);
            List<LivingEntity> entities = this.getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, aabb);

            for (LivingEntity entity : entities) {
                if (!entity.fireImmune() && !entity.hasEffect(MobEffects.FIRE_RESISTANCE) && !EntityTemperatureSystem.armourIsHeatResistant(entity)) {
                    entity.setSecondsOnFire(15);
                }
            }
        }
    }

    public boolean doesDrop(BlockState state, BlockPos pos) {
        if (this.onGround) {

            BlockState state2 = this.level.getBlockState(new BlockPos((int)Math.floor(this.getX()), (int)(this.getY() - 0.2), (int)Math.floor(this.getZ())));

            if (!this.level.isEmptyBlock(pos) && (state2.is(GcysBlocks.LAUNCH_PAD.get()) || !state.is(GcysBlocks.LAUNCH_PAD.get()))) {
                //this.dropEquipment();
                //this.spawnRocketItem();

                if (!this.level.isClientSide) {
                    this.remove(RemovalReason.DISCARDED);
                }

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

    public void unBuild() {
        if (this.level.isClientSide) return;

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
            if (!this.level.getBlockState(offset).isAir() && !this.level.getBlockState(offset).canBeReplaced(new BlockPlaceContext(this.level, null, InteractionHand.MAIN_HAND, ItemStack.EMPTY, result))) continue;
            this.level.setBlock(offset, state.state(), Block.UPDATE_ALL);
        }

        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public void push(Entity entity) {

    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    public long getFuelCapacity() {
        return this.entityData.get(FUEL_CAPACITY);
    }

    public void setFuelCapacity(long fuelCapacity) {
        this.entityData.set(FUEL_CAPACITY, fuelCapacity);
        this.reinitializeFluidStorage();
    }

    public long getFuel() {
        return this.entityData.get(FUEL);
    }

    public void setFuel(long fuel) {
        this.entityData.set(FUEL, fuel);
    }

    public int getThrusterCount() {
        return this.entityData.get(THRUSTER_COUNT);
    }

    public void setThrusterCount(int count) {
        this.entityData.set(THRUSTER_COUNT, count);
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
        this.entityData.get(POSITIONED_STATES).add(state);
        BlockPos pos = state.pos();
        this.sizeX = Math.max(this.sizeX, Mth.abs(pos.getX() - this.getBlockX()));
        this.sizeY = Math.max(this.sizeY, Mth.abs(pos.getY() - this.getBlockY()));
        this.sizeZ = Math.max(this.sizeZ, Mth.abs(pos.getZ() - this.getBlockZ()));
        float destroyTime = state.state().getBlock().defaultDestroyTime();
        if (destroyTime > 0) {
            this.weight += destroyTime / 5;
        }
        if (state.state().is(GcysBlocks.CASING_ROCKET_MOTOR.get())) {
            this.setThrusterCount(this.getThrusterCount() + 1);
        } else if (state.state().is(GcysBlocks.FUEL_TANK.get())) {
            this.setFuelCapacity(this.getFuelCapacity() + 5 * FluidHelper.getBucket());
        } else if (state.state().is(GcysBlocks.SEAT.get())) {
            this.addSeatPos(pos);
        }
    }

    public List<PosWithState> getBlocks() {
        return this.entityData.get(POSITIONED_STATES);
    }

    public void addSeatPos(BlockPos pos) {
        this.entityData.get(SEAT_POSITIONS).add(pos);
    }

    public List<BlockPos> getSeatPositions() {
        return this.entityData.get(SEAT_POSITIONS);
    }

    public double getRocketSpeed() {
        return this.getThrusterCount() * 2.5 - (10.0 / weight);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(ROCKET_STARTED, false);
        this.entityData.define(FUEL, 0L);
        this.entityData.define(FUEL_CAPACITY, 0L);
        this.entityData.define(THRUSTER_COUNT, 0);
        this.entityData.define(MAX_DISTANCE_TRAVELABLE, DEFAULT_DISTANCE_TRAVELABLE);
        this.entityData.define(START_TIMER, 0);
        this.entityData.define(POSITIONED_STATES, new ArrayList<>());
        this.entityData.define(SEAT_POSITIONS, new ArrayList<>());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        ListTag blocks = compound.getList("blocks", Tag.TAG_COMPOUND);
        for (int i = 0; i < blocks.size(); ++i) {
            this.addBlock(PosWithState.readFromTag(blocks.getCompound(i)));
        }
        ListTag seats = compound.getList("seats", Tag.TAG_COMPOUND);
        for (int i = 0; i < seats.size(); ++i) {
            this.addSeatPos(NbtUtils.readBlockPos(seats.getCompound(i)));
        }

        this.setFuelCapacity(compound.getLong("fuelCapacity"));
        this.setFuel(compound.getLong("fuel"));
        this.setThrusterCount(compound.getInt("thrusterCount"));
        this.setStartTimer(compound.getInt("startTimer"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        var blocks = this.getBlocks();
        ListTag blockTag = new ListTag();
        compound.put("blocks", blockTag);
        for (PosWithState state : blocks) {
            blockTag.add(state.writeToTag());
        }

        var seats = this.getSeatPositions();
        ListTag seatsTag = new ListTag();
        compound.put("seat", seatsTag);
        for (BlockPos seatPos : seats) {
            seatsTag.add(NbtUtils.writeBlockPos(seatPos));
        }

        compound.putLong("fuelCapacity", this.getFuelCapacity());
        compound.putLong("fuel", this.getFuel());
        compound.putLong("thrusterCount", this.getFuel());
        compound.putInt("startTimer", this.getStartTimer());
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
