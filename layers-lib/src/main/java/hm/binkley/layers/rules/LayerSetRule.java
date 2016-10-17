package hm.binkley.layers.rules;

import hm.binkley.layers.FullnessFunction;
import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerSet;
import hm.binkley.layers.Layers;

import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;

public final class LayerSetRule
        extends KeyRule<Set<Layer>> {
    private final FullnessFunction full;

    LayerSetRule(final Object key, final FullnessFunction full) {
        super(full.toString(), key);
        this.full = full;
    }

    @Override
    public Set<Layer> apply(final Layers layers, final Layer layer,
            final Set<Layer> value) {
        final LayerSet set = new LayerSet(full);
        return layers.<Set<Layer>>plainValuesFor(key).
                flatMap(Collection::stream).
                collect(toCollection(() -> set));
    }
}
