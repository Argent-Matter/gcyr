package argent_matter.gcyr.core.mixin.dev.datagen;

import argent_matter.gcyr.core.extensions.IntrinsicTagAppenderExt;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(IntrinsicHolderTagsProvider.IntrinsicTagAppender.class)
public abstract class IntrinsicTagAppenderMixin<T> implements IntrinsicTagAppenderExt<T> {}
