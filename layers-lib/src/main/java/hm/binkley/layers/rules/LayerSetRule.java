package hm.binkley.layers.rules;

import hm.binkley.layers.FullnessFunction;
import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerSet;
import hm.binkley.layers.Layers;

import java.util.Collection;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;

public final class LayerSetRule<L extends Layer>
        extends KeyRule<Set<L>, Set<L>> {
    private final FullnessFunction<L> full;

    LayerSetRule(final Object key, final FullnessFunction<L> full) {
        super(full.toString(), key);
        this.full = full;
    }

    @Override
    public Set<L> apply(final Layers layers, final Layer layer,
            final Set<L> value) {
        final LayerSet<L> set = new LayerSet<>(full);
        return layers.<Set<L>, Set<L>>plainValuesFor(key).
                flatMap(Collection::stream).
                collect(toCollection(() -> set));
    }
}
