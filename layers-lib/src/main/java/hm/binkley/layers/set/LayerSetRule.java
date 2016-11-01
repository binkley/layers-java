package hm.binkley.layers.set;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.rules.KeyRule;

import java.util.Set;

import static java.util.stream.Collectors.toCollection;

public final class LayerSetRule<L extends Layer>
        extends KeyRule<L, Set<L>> {
    private final FullnessFunction<L> full;

    public LayerSetRule(final Object key, final FullnessFunction<L> full) {
        super(full.toString(), key);
        this.full = full;
    }

    @Override
    public Set<L> apply(final Layers layers, final Layer layer,
            final L value) {
        final LayerSet<L> set = new LayerSet<>(full);
        return layers.<L, Set<L>>plainValuesFor(key).
                collect(toCollection(() -> set));
    }
}
