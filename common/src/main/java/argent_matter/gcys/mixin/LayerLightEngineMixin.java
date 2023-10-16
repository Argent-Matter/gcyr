package argent_matter.gcys.mixin;

import argent_matter.gcys.GregicalitySpaceClient;
import argent_matter.gcys.api.capability.GcysCapabilityHelper;
import argent_matter.gcys.config.GcysConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.DataLayerStorageMap;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraft.world.level.lighting.LayerLightSectionStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LayerLightEngine.class)
public abstract class LayerLightEngineMixin<M extends DataLayerStorageMap<M>, S extends LayerLightSectionStorage<M>> {

    @Shadow @Final
    protected S storage;

    @Shadow @Final protected LightChunkGetter chunkSource;

    @Shadow @Final protected LightLayer layer;

    @Inject(method = "getLightValue", at = @At("HEAD"), cancellable = true)
    public void gcys$overrideSkylight(BlockPos levelPos, CallbackInfoReturnable<Integer> cir) {
        if (this.layer == LightLayer.SKY) {
            if (GregicalitySpaceClient.isDysonSphereActive || (this.chunkSource instanceof ServerChunkCache cache && GcysCapabilityHelper.getDysonSystem(((ServerChunkCacheAccessor)cache).getLevel()).isDysonSphereActive())) {
                cir.setReturnValue(Math.min(((LayerLightSectionStorageAccessor)this.storage).callGetLightValue(levelPos.asLong()), GcysConfig.INSTANCE.server.maxSphereSkylight));
            }
        }
    }
}
