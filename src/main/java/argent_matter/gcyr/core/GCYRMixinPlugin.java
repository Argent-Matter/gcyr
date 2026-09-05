package argent_matter.gcyr.core;

import net.minecraftforge.fml.loading.FMLLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class GCYRMixinPlugin implements IMixinConfigPlugin {

    private static final String MIXIN_PACKAGE = "argent_matter.gcyr.core.mixin.";
    private static final String DEV_PACKAGE = "dev.";
    private static final String WORLDBORDER_PACKAGE = "worldborder.";

    @Override
    public void onLoad(String s) {}

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!mixinClassName.startsWith(MIXIN_PACKAGE)) {
            // skip checking mixins that aren't in our package
            // this should never happen, but better safe than sorry
            return true;
        }
        if (!FMLLoader.getLoadingModList().getErrors().isEmpty()) {
            // stop processing mixins if we have load errors to avoid getting bad crash reports in our issues
            return false;
        }
        mixinClassName = mixinClassName.substring(MIXIN_PACKAGE.length());

        if (mixinClassName.startsWith(DEV_PACKAGE)) {
            // don't load dev-only mixins in prod
            return !FMLLoader.isProduction();
        }

        if (mixinClassName.startsWith(WORLDBORDER_PACKAGE)) {
            mixinClassName = mixinClassName.substring(WORLDBORDER_PACKAGE.length());
            if (mixinClassName.startsWith("lithium")) {
                return isModLoaded("radium") || isModLoaded("lithium");
            } else if (mixinClassName.startsWith("vanilla")) {
                return !(isModLoaded("radium") || isModLoaded("lithium"));
            }
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> set, Set<String> set1) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {}

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {}

    private static boolean isModLoaded(String modId) {
        return FMLLoader.getLoadingModList().getModFileById(modId) != null;
    }
}
