package argent_matter.gcys.common.entity.data;

import argent_matter.gcys.common.item.armor.SpaceSuitArmorItem;
import argent_matter.gcys.config.GcysConfig;
import argent_matter.gcys.data.loader.PlanetData;
import argent_matter.gcys.data.recipe.GCySTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;

import java.util.stream.StreamSupport;


/**
 * Proudly copied from <a href="https://github.com/terrarium-earth/Ad-Astra/blob/1.19/common/src/main/java/earth/terrarium/ad_astra/common/entity/system/EntityTemperatureSystem.java">Ad Astra</a>
 */
public class EntityTemperatureSystem {
    public static final UniformInt TEMP_RANGE = UniformInt.of(213, 333);


    public static void temperatureTick(LivingEntity entity, ServerLevel level) {
        if (!GcysConfig.INSTANCE.server.enableOxygen) {
            return;
        }
        if (!entity.getType().equals(EntityType.SKELETON)) {
            if (entity.isInvertedHealAndHarm()) {
                return;
            }

            /*if (ModUtils.checkTag(entity, ModTags.LIVES_WITHOUT_OXYGEN)) {
                return;
            }*/
        }

        float temperature = PlanetData.getWorldTemperature(level);

        // Normal temperature when inside an oxygen bubble. This should probably be changed so that a separate machine is required to manage temperature.
        if (EntityOxygenSystem.inDistributorBubble(level, entity.blockPosition())) {
            temperature = 293.0f;
        }

        UniformInt temperatureResistance = TEMP_RANGE;
        if (SpaceSuitArmorItem.hasFullSet(entity)) {
            temperatureResistance = ((SpaceSuitArmorItem) entity.getArmorSlots().iterator().next().getItem()).getTemperatureThreshold();
        }

        if (temperature > temperatureResistance.getMaxValue() && !entity.fireImmune() && !entity.hasEffect(MobEffects.FIRE_RESISTANCE) && !EntityTemperatureSystem.armourIsHeatResistant(entity)) {
            burnEntity(entity);
        } else if (temperature < temperatureResistance.getMinValue() && !EntityTemperatureSystem.armourIsFreezeResistant(entity)) {
            freezeEntity(entity, level);
        }
    }

    private static void burnEntity(LivingEntity entity) {
        entity.hurt(entity.damageSources().onFire(), GcysConfig.INSTANCE.server.heatDamage);
        entity.setSecondsOnFire(10);
    }

    private static void freezeEntity(LivingEntity entity, ServerLevel level) {
        entity.hurt(entity.damageSources().freeze(), GcysConfig.INSTANCE.server.freezeDamage);
        entity.setTicksFrozen(Math.min(entity.getTicksRequiredToFreeze() + 20, entity.getTicksFrozen() + 5 * 10));
        RandomSource random = entity.level().getRandom();
        level.addParticle(ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY() + 1, entity.getZ(), Mth.randomBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05, (double) Mth.randomBetween(random, -1.0f, 1.0f) * 0.083333336);

        // Turn skeletons into strays
        if (entity instanceof Skeleton skeleton) {
            skeleton.convertTo(EntityType.STRAY, true);
        }
    }

    public static boolean armourIsFreezeResistant(LivingEntity entity) {
        return StreamSupport.stream(entity.getArmorSlots().spliterator(), false).allMatch(s -> s.is(GCySTags.FREEZE_RESISTANT));
    }

    public static boolean armourIsHeatResistant(LivingEntity entity) {
        return StreamSupport.stream(entity.getArmorSlots().spliterator(), false).allMatch(s -> s.is(GCySTags.HEAT_RESISTANT));
    }
}

