package argent_matter.gcyr.api.data.tag;

import argent_matter.gcyr.GCyR;
import com.lowdragmc.lowdraglib.Platform;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class GCyRTagUtil {
    public static <T> TagKey<T> optionalTag(ResourceKey<? extends Registry<T>> registry, ResourceLocation id) {
        return TagKey.create(registry, id);
    }

    public static <T> TagKey<T> createTag(ResourceKey<? extends Registry<T>> registry, String path, boolean vanilla) {
        if (vanilla) return optionalTag(registry, new ResourceLocation("minecraft", path));
        return optionalTag(registry, Platform.isForge() ? new ResourceLocation("forge", path) : new ResourceLocation("c", path));
    }

    public static <T> TagKey<T> createPlatformTag(ResourceKey<? extends Registry<T>> registry, String forgePath, String fabricPath, boolean modTag) {
        if (modTag) return optionalTag(registry, Platform.isForge() ? GCyR.id(forgePath) : GCyR.id(fabricPath));
        return optionalTag(registry, Platform.isForge() ? new ResourceLocation("forge", forgePath) : new ResourceLocation("c", fabricPath));
    }

    public static <T> TagKey<T> createPlatformUnprefixedTag(ResourceKey<? extends Registry<T>> registry, String forgePath, String fabricPath) {
        return optionalTag(registry, Platform.isForge() ? new ResourceLocation(forgePath) : new ResourceLocation(fabricPath));
    }

    public static <T> TagKey<T> createModTag(ResourceKey<? extends Registry<T>> registry, String path) {
        return optionalTag(registry, GCyR.id(path));
    }

    public static TagKey<Block> createBlockTag(String path) {
        return createTag(Registries.BLOCK, path, false);
    }

    public static TagKey<Block> createBlockTag(String path, boolean vanilla) {
        return createTag(Registries.BLOCK, path, vanilla);
    }

    public static TagKey<Block> createModBlockTag(String path) {
        return createModTag(Registries.BLOCK, path);
    }

    public static TagKey<Block> createPlatformBlockTag(String forgePath, String fabricPath, boolean modTag) {
        return createPlatformTag(Registries.BLOCK, forgePath, fabricPath, modTag);
    }

    public static TagKey<Item> createItemTag(String path) {
        return createTag(Registries.ITEM, path, false);
    }

    public static TagKey<Item> createItemTag(String path, boolean vanilla) {
        return createTag(Registries.ITEM, path, vanilla);
    }

    public static TagKey<Item> createPlatformItemTag(String forgePath, String fabricPath) {
        return createPlatformItemTag(forgePath, fabricPath, false);
    }

    public static TagKey<Item> createPlatformItemTag(String forgePath, String fabricPath, boolean modTag) {
        return createPlatformTag(Registries.ITEM, forgePath, fabricPath, modTag);
    }

    public static TagKey<Item> createModItemTag(String path) {
        return createModTag(Registries.ITEM, path);
    }

    public static TagKey<Fluid> createFluidTag(String path) {
        return createTag(Registries.FLUID, path, false);
    }

    public static TagKey<Fluid> createModFluidTag(String path) {
        return createModTag(Registries.FLUID, path);
    }

}
