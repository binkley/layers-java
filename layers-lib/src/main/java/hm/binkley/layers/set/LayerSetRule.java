package hm.binkley.layers.set;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.rules.KeyRule;

import java.util.Set;

public final class LayerSetRule<L extends Layer>
        extends KeyRule<LayerSetCommand<L>, Set<L>> {
    private final FullnessFunction<L> full;

    public LayerSetRule(final Object key, final FullnessFunction<L> full) {
        super(full.toString(), key);
        this.full = full;
    }

    @Override
    public Set<L> apply(final Layers layers, final Layer layer,
            final LayerSetCommand<L> value) {
        final LayerSet<L> set = new LayerSet<>(full);
        layers.<LayerSetCommand<L>, Set<L>>plainValuesFor(key).
                forEach(command -> command.accept(set));
        return set;
    }
}
