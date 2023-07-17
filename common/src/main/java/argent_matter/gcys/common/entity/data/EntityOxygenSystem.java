package argent_matter.gcys.common.entity.data;


import argent_matter.gcys.GregicalitySpace;
import argent_matter.gcys.common.data.GcysDimensionTypes;
import argent_matter.gcys.common.item.armor.SpaceSuitArmorItem;
import argent_matter.gcys.config.GcysConfig;
import argent_matter.gcys.data.recipe.GcysTags;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.gregtechceu.gtceu.GTCEu;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.StreamSupport;

/**
 * Proudly copied from <a href="https://github.com/terrarium-earth/Ad-Astra/blob/1.19/common/src/main/java/earth/terrarium/ad_astra/common/entity/system/EntityOxygenSystem.java">Ad Astra</a>
 */
public class EntityOxygenSystem {

    public static final Table<ResourceKey<Level>, BlockPos, Set<BlockPos>> OXYGEN_LOCATIONS = Tables.newCustomTable(new HashMap<>(), HashMap::new);


    /**
     * Checks if a level has oxygen, regardless of position.
     */
    public static boolean levelHasOxygen(Level level) {
        return level.dimension() != GcysDimensionTypes.SPACE_LEVEL;
    }

    public static void setEntry(Level level, BlockPos source, Set<BlockPos> entries) {
        // Get all the entries that have changed. If they have been removed, deoxygenate their pos.
        if (!level.isClientSide) {
            if (OXYGEN_LOCATIONS.contains(level.dimension(), source)) {
                Set<BlockPos> changedPositions = new HashSet<>(OXYGEN_LOCATIONS.get(level.dimension(), source));
                if (changedPositions != null && !changedPositions.isEmpty()) {
                    changedPositions.removeAll(entries);
                    deoxygenizeBlocks((ServerLevel) level, changedPositions, source);
                }
            }
        }
        OXYGEN_LOCATIONS.put(level.dimension(), source, entries);
    }

    public static void removeEntry(Level level, BlockPos source) {
        EntityOxygenSystem.setEntry(level, source, Set.of());
    }

    public static void oxygenTick(LivingEntity entity, ServerLevel level) {
        if (!GcysConfig.INSTANCE.server.enableOxygen) {
            return;
        }
        if (entity.isInvertedHealAndHarm()) {
            return;
        }

        /*if (entity.getType().is(GcysTags.NO_OXYGEN)) {
            return;
        }*/

        if (level.dimension() != GcysDimensionTypes.SPACE_LEVEL && !entity.isUnderWater()) {
            return;
        }

        boolean entityHasOxygen = entityHasOxygen(level, entity);
        boolean hasOxygenatedSpaceSuit = SpaceSuitArmorItem.hasOxygenatedSpaceSuit(entity) && SpaceSuitArmorItem.hasFullSet(entity);

        if (entityHasOxygen && hasOxygenatedSpaceSuit && entity.isUnderWater() && !entity.canBreatheUnderwater() && !entity.hasEffect(MobEffects.WATER_BREATHING)) {
            consumeOxygen(entity);
            return;
        }

        if (!entityHasOxygen) {
            if (hasOxygenatedSpaceSuit) {
                consumeOxygen(entity);
            } else if (!StreamSupport.stream(entity.getArmorSlots().spliterator(), false).allMatch(stack -> stack.is(GcysTags.OXYGENATED_ARMOR))) {
                entity.hurt(DamageSource.DROWN, GcysConfig.INSTANCE.server.oxygenDamage);
                entity.setAirSupply(-40);
            }
        }
    }

    /**
     * Removes the oxygen from a set of blocks. For example, turns water into ice or air, converts torches into extinguished torches, puts out flames, kills plants etc.
     */
    public static void deoxygenizeBlocks(ServerLevel level, Set<BlockPos> entries, BlockPos source) {
        try {
            if (entries == null) {
                return;
            }
            if (entries.isEmpty()) {
                return;
            }

            if (levelHasOxygen(level)) {
                OXYGEN_LOCATIONS.remove(level.dimension(), source);
                return;
            }

            for (BlockPos pos : new HashSet<>(entries)) {

                BlockState state = level.getBlockState(pos);

                OXYGEN_LOCATIONS.get(level.dimension(), source).remove(pos);
                if (posHasOxygen(level, pos)) {
                    continue;
                }

                if (state.isAir()) {
                    continue;
                }

                Block block = state.getBlock();
                if (block instanceof CandleCakeBlock) {
                    level.setBlockAndUpdate(pos, state.setValue(CandleCakeBlock.LIT, false));
                    continue;
                }

                if (block instanceof CandleBlock) {
                    level.setBlockAndUpdate(pos, state.setValue(CandleBlock.LIT, false));
                    continue;
                }

                if (block instanceof FireBlock) {
                    level.removeBlock(pos, false);
                    continue;
                }

                if (block instanceof CampfireBlock) {
                    level.setBlockAndUpdate(pos, state.setValue(CampfireBlock.LIT, false));
                    continue;
                }

                if (block instanceof GrassBlock) {
                    level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
                    continue;
                }

                if (block instanceof BushBlock || block instanceof CactusBlock || block instanceof VineBlock) {
                    level.removeBlock(pos, true);
                    continue;
                }

                if (block instanceof FarmBlock) {
                    level.setBlockAndUpdate(pos, state.setValue(FarmBlock.MOISTURE, 0));
                    continue;
                }

                if (state.getFluidState().is(FluidTags.WATER)) {
                    if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
                        level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.WATERLOGGED, false));
                    } else {
                        // maybe check "planet" temperature in the future if we ever add those?
                        // and then turn into ice instead if temp > 0
                        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    }
                }
            }
        } catch (UnsupportedOperationException e) {
            GregicalitySpace.LOGGER.error("Error deoxygenizing blocks");
            e.printStackTrace();
        }
    }

    private static void consumeOxygen(LivingEntity entity) {
        if (entity.getLevel().getGameTime() % 3 == 0) {
            entity.setAirSupply(Math.min(entity.getMaxAirSupply(), entity.getAirSupply() + 4 * 10));
            SpaceSuitArmorItem.consumeSpaceSuitOxygen(entity, 1);
        }
    }

    /**
     * Checks if an entity has oxygen.
     */
    public static boolean entityHasOxygen(Level level, LivingEntity entity) {
        return posHasOxygen(level, new BlockPos(entity.getEyePosition()));
    }

    /**
     * Checks if there is oxygen in a specific block in a specific dimension.
     */
    @SuppressWarnings("deprecation")
    public static boolean posHasOxygen(Level level, BlockPos pos) {

        if (!level.hasChunkAt(pos)) {
            return true;
        }

        if (levelHasOxygen(level)) {
            return true;
        }

        return inDistributorBubble(level, pos);
    }

    public static boolean inDistributorBubble(Level level, BlockPos pos) {
        for (var entry : OXYGEN_LOCATIONS.row(level.dimension()).values()) {
            if (entry.contains(pos)) {
                return true;
            }
        }
        return false;
    }
}
