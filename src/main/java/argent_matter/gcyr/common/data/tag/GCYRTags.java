package argent_matter.gcyr.common.data.tag;

import argent_matter.gcyr.GCYR;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

@SuppressWarnings("SameParameterValue")
public class GCYRTags {

    public static class Items {

        public static final TagKey<Item> SATELLITES = tag("satellites");
        public static final TagKey<Item> IS_SPACESUIT = tag("is_spacesuit");
        public static final TagKey<Item> FREEZE_RESISTANT = tag("freeze_resistant");
        public static final TagKey<Item> HEAT_RESISTANT = tag("heat_resistant");

        // region helpers
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(GCYR.id(name));
        }

        private static TagKey<Item> commonTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", name));
        }
        // endregion
    }

    public static class Blocks {

        public static final TagKey<Block> MOON_ORE_REPLACEABLES = tag("moon_ore_replaceables");
        public static final TagKey<Block> MARS_ORE_REPLACEABLES = tag("mars_ore_replaceables");
        public static final TagKey<Block> INFINIBURN_SPACE = tag("infiniburn_space");
        public static final TagKey<Block> BLOCKS_FLOOD_FILL = tag("blocks_flood_fill");
        public static final TagKey<Block> PASSES_FLOOD_FILL = tag("passes_flood_fill");

        // region helpers
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(GCYR.id(name));
        }

        private static TagKey<Block> commonTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath("forge", name));
        }
        // endregion
    }

    public static class Fluids {

        // region helpers
        private static TagKey<Fluid> tag(String name) {
            return FluidTags.create(GCYR.id(name));
        }

        private static TagKey<Fluid> commonTag(String name) {
            return FluidTags.create(ResourceLocation.fromNamespaceAndPath("forge", name));
        }
        // endregion
    }

    public static class Biomes {

        public static final TagKey<Biome> IS_SPACE = tag("is_space");
        public static final TagKey<Biome> IS_MOON = tag("is_moon");
        public static final TagKey<Biome> IS_MERCURY = tag("is_mercury");
        public static final TagKey<Biome> IS_MARS = tag("is_mars");
        public static final TagKey<Biome> IS_VENUS = tag("is_venus");

        // region helpers
        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, GCYR.id(name));
        }

        private static TagKey<Biome> commonTag(String name) {
            return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("forge", name));
        }
        // endregion
    }

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> IGNORE_OXYGEN = tag("ignore_oxygen");
        public static final TagKey<EntityType<?>> IGNORE_TEMPERATURE = tag("ignore_temperature");

        // region helpers
        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, GCYR.id(name));
        }

        private static TagKey<EntityType<?>> commonTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("forge", name));
        }
        // endregion
    }
}
