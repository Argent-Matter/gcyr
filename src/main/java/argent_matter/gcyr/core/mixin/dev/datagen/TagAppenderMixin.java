package argent_matter.gcyr.core.mixin.dev.datagen;

import argent_matter.gcyr.core.extensions.TagAppenderExt;

import net.minecraft.data.tags.TagsProvider;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(TagsProvider.TagAppender.class)
public abstract class TagAppenderMixin<T> implements TagAppenderExt<T> {}
