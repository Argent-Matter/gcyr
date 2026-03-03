package argent_matter.gcyr.common.item;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record PlanetFilter(@Nullable Set<ResourceLocation> accept, @Nullable Set<ResourceLocation> deny) {
    public static final PlanetFilter EMPTY = new PlanetFilter(null, null);

    public List<ResourceLocation> apply(List<ResourceLocation> dimensionIds) {
        List<ResourceLocation> result;
        if (accept != null) {
            result = new ArrayList<>(dimensionIds);
            result.retainAll(accept);
        } else {
            result = new ArrayList<>(dimensionIds);
        }
        if (deny != null) {
            result.removeAll(deny);
        }
        return result;
    }

    public boolean isEmpty() {
        return accept == null && deny == null;
    }

    public PlanetFilter addAccept(ResourceLocation id) {
        Set<ResourceLocation> newAccept = accept != null ? new HashSet<>(accept) : new HashSet<>();
        newAccept.add(id);
        return new PlanetFilter(newAccept, deny);
    }

    public PlanetFilter removeAccept(ResourceLocation id) {
        if (accept == null) return this;
        Set<ResourceLocation> newAccept = new HashSet<>(accept);
        newAccept.remove(id);
        return new PlanetFilter(newAccept.isEmpty() ? null : newAccept, deny);
    }

    public PlanetFilter addDeny(ResourceLocation id) {
        Set<ResourceLocation> newDeny = deny != null ? new HashSet<>(deny) : new HashSet<>();
        newDeny.add(id);
        return new PlanetFilter(accept, newDeny);
    }

    public PlanetFilter removeDeny(ResourceLocation id) {
        if (deny == null) return this;
        Set<ResourceLocation> newDeny = new HashSet<>(deny);
        newDeny.remove(id);
        return new PlanetFilter(accept, newDeny.isEmpty() ? null : newDeny);
    }
}
