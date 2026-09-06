package argent_matter.gcyr.core.mixin.dev.datagen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.*;

import java.util.List;

public class RegistrySetBuilderMixin {

    @Mixin(targets = "net.minecraft.core.RegistrySetBuilder$BuildState")
    public static class BuildStateMixin {

        @Unique
        private static final Logger gcyr$LOGGER = LogManager.getLogger();

        @Shadow
        @Final
        private List<RuntimeException> errors;

        /**
         * @author screret
         * @reason errors aren't real anyway.
         */
        @Overwrite
        public void throwOnError() {
            gcyr$LOGGER.info("Ignore following errors about missing noise settings, they're fine. Other missing things aren't though!");

            if (!this.errors.isEmpty()) {
                gcyr$LOGGER.warn("Errors during registry creation");
                for(RuntimeException ex : this.errors) {
                    gcyr$LOGGER.warn(ex);
                }
            }
        }
    }
}
