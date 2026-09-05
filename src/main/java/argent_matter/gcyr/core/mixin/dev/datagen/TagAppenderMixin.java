package argent_matter.gcyr.core.mixin.dev.datagen;

import net.minecraft.data.tags.TagsProvider;

import argent_matter.gcyr.core.extensions.TagAppenderExt;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TagsProvider.TagAppender.class)
public abstract class TagAppenderMixin<T> implements TagAppenderExt<T> {}
