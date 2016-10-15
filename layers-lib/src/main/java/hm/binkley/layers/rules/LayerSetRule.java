package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerSet;
import hm.binkley.layers.Layers;

import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;

public final class LayerSetRule
        extends KeyRule<Set<Layer>> {
    private final int max;

    LayerSetRule(final String name, final Object key,
            final int max) {
        super(name, key);
        this.max = max;
    }

    @Override
    public Set<Layer> apply(final Layers layers, final Layer layer,
            final Set<Layer> value) {
        final LayerSet set = new LayerSet(max);
        return layers.<Set<Layer>>plainValuesFor(key).
                flatMap(Collection::stream).
                collect(toCollection(() -> set));
    }
}
