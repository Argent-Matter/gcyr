package argent_matter.gcyr.mixin;

import argent_matter.gcyr.GCYRClient;
import com.mojang.blaze3d.vertex.VertexFormat;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Hack to add QUAD_STRIP vertex format mode
@Mixin(VertexFormat.Mode.class)
public class VertexFormatModeMixin {
    @NotNull
    @Shadow @Final @Mutable
    private static VertexFormat.Mode[] $VALUES;

    @Invoker("<init>")
    private static VertexFormat.Mode gcyr$invokeInit(String name, int ordinal, int asGlMode, int primitiveLength, int primitiveStride, boolean connectedPrimitives) {
        throw new AssertionError();
    }

    private static VertexFormat.Mode gcyr$addVariant(String name, int asGlMode, int primitiveLength, int primitiveStride, boolean connectedPrimitives) {
        VertexFormat.Mode[] values = new VertexFormat.Mode[$VALUES.length + 1];
        System.arraycopy($VALUES, 0, values, 0, $VALUES.length);
        int internalId = values.length - 1;
        VertexFormat.Mode newValue = gcyr$invokeInit(name, internalId, asGlMode, primitiveLength, primitiveStride, connectedPrimitives);
        values[internalId] = newValue;
        $VALUES = values;
        return newValue;
    }

    static {
        GCYRClient.MODE_QUAD_STRIP = gcyr$addVariant("QUAD_STRIP", GL11.GL_TRIANGLE_STRIP, 4, 4, true);
    }

    @Inject(method = "indexCount", at = @At("HEAD"), cancellable = true)
    private void gcyr$modifyIndexCount(int vertices, CallbackInfoReturnable<Integer> cir) {
        if ((Object) this == GCYRClient.MODE_QUAD_STRIP) {
            cir.setReturnValue(vertices / 4 * 6);
        }
    }
}
