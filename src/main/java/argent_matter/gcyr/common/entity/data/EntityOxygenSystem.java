package argent_matter.gcyr.common.entity.data;


import argent_matter.gcyr.GCyR;
import argent_matter.gcyr.common.item.armor.SpaceSuitArmorItem;
import argent_matter.gcyr.config.GCyRConfig;
import argent_matter.gcyr.data.loader.PlanetData;
import argent_matter.gcyr.data.recipe.GCyRTags;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.stream.StreamSupport;

/**
 * Proudly copied from <a href="https://github.com/terrarium-earth/Ad-Astra/blob/1.19/common/src/main/java/earth/terrarium/ad_astra/common/entity/system/EntityOxygenSystem.java">Ad Astra</a>
 */
public class EntityOxygenSystem {

    public static final Table<ResourceKey<Level>, BlockPos, Set<BlockPos>> OXYGEN_LOCATIONS = Tables.newCustomTable(new IdentityHashMap<>(), HashMap::new);

    /**
     * Checks if a level has oxygen, regardless of position.
     */
    public static boolean levelHasOxygen(Level level) {
        return PlanetData.isOxygenated(level);
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
        if (!GCyRConfig.INSTANCE.server.enableOxygen) {
            return;
        }
        if (entity.isInvertedHealAndHarm()) {
            return;
        }

        if (entity.getType().is(GCyRTags.IGNORE_OXYGEN)) {
            return;
        }

        if (!PlanetData.isSpaceLevel(level) && !entity.isUnderWater()) {
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
            } else if (!StreamSupport.stream(entity.getArmorSlots().spliterator(), false).allMatch(stack -> stack.is(GCyRTags.SPACESUIT_ARMOR))) {
                entity.hurt(level.damageSources().drown(), GCyRConfig.INSTANCE.server.oxygenDamage);
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
                if (state.hasProperty(BlockStateProperties.LIT)) {
                    level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, false));
                    continue;
                }

                if (block instanceof FireBlock) {
                    level.removeBlock(pos, false);
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
                        if (PlanetData.getWorldTemperature(level) < 273.0f) {
                            level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
                        } else {
                            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            }
        } catch (UnsupportedOperationException e) {
            GCyR.LOGGER.error("Error deoxygenizing blocks");
            e.printStackTrace();
        }
    }

    private static void consumeOxygen(LivingEntity entity) {
        if (entity.level().getGameTime() % 3 == 0) {
            entity.setAirSupply(Math.min(entity.getMaxAirSupply(), entity.getAirSupply() + 4 * 10));
            SpaceSuitArmorItem.consumeSpaceSuitOxygen(entity, 1);
        }
    }

    /**
     * Checks if an entity has oxygen.
     */
    public static boolean entityHasOxygen(Level level, LivingEntity entity) {
        return posHasOxygen(level, BlockPos.containing(entity.getEyePosition()));
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
