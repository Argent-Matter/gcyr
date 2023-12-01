package argent_matter.gcyr.api.data.tag;

import argent_matter.gcyr.GCyR;
import com.lowdragmc.lowdraglib.Platform;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class GCyRTagUtil {
    public static <T> TagKey<T> optionalTag(Registry<T> registry, ResourceLocation id) {
        return TagKey.create(registry.key(), id);
    }

    public static <T> TagKey<T> createTag(Registry<T> registry, String path, boolean vanilla) {
        if (vanilla) return optionalTag(registry, new ResourceLocation("minecraft", path));
        return optionalTag(registry, Platform.isForge() ? new ResourceLocation("forge", path) : new ResourceLocation("c", path));
    }

    public static <T> TagKey<T> createPlatformTag(Registry<T> registry, String forgePath, String fabricPath, boolean modTag) {
        if (modTag) return optionalTag(registry, Platform.isForge() ? GCyR.id(forgePath) : GCyR.id(fabricPath));
        return optionalTag(registry, Platform.isForge() ? new ResourceLocation("forge", forgePath) : new ResourceLocation("c", fabricPath));
    }

    public static <T> TagKey<T> createPlatformUnprefixedTag(Registry<T> registry, String forgePath, String fabricPath) {
        return optionalTag(registry, Platform.isForge() ? new ResourceLocation(forgePath) : new ResourceLocation(fabricPath));
    }

    public static <T> TagKey<T> createModTag(Registry<T> registry, String path) {
        return optionalTag(registry, GCyR.id(path));
    }

    public static TagKey<Block> createBlockTag(String path) {
        return createTag(BuiltInRegistries.BLOCK, path, false);
    }

    public static TagKey<Block> createBlockTag(String path, boolean vanilla) {
        return createTag(BuiltInRegistries.BLOCK, path, vanilla);
    }

    public static TagKey<Block> createModBlockTag(String path) {
        return createModTag(BuiltInRegistries.BLOCK, path);
    }

    public static TagKey<Block> createPlatformBlockTag(String forgePath, String fabricPath, boolean modTag) {
        return createPlatformTag(BuiltInRegistries.BLOCK, forgePath, fabricPath, modTag);
    }

    public static TagKey<Item> createItemTag(String path) {
        return createTag(BuiltInRegistries.ITEM, path, false);
    }

    public static TagKey<Item> createItemTag(String path, boolean vanilla) {
        return createTag(BuiltInRegistries.ITEM, path, vanilla);
    }

    public static TagKey<Item> createPlatformItemTag(String forgePath, String fabricPath) {
        return createPlatformItemTag(forgePath, fabricPath, false);
    }

    public static TagKey<Item> createPlatformItemTag(String forgePath, String fabricPath, boolean modTag) {
        return createPlatformTag(BuiltInRegistries.ITEM, forgePath, fabricPath, modTag);
    }

    public static TagKey<Item> createModItemTag(String path) {
        return createModTag(BuiltInRegistries.ITEM, path);
    }

    public static TagKey<Fluid> createFluidTag(String path) {
        return createTag(BuiltInRegistries.FLUID, path, false);
    }

    public static TagKey<Fluid> createModFluidTag(String path) {
        return createModTag(BuiltInRegistries.FLUID, path);
    }

}