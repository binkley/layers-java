package hm.binkley.layers.set;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.RuleSurface;
import hm.binkley.layers.rules.Rule;

import java.util.Set;

public final class LayerSetRule<L extends Layer>
        extends Rule<LayerSetCommand<L>, Set<L>> {
    private final FullnessFunction<L> full;

    public LayerSetRule(final FullnessFunction<L> full) {
        super(full.toString());
        this.full = full;
    }

    @Override
    public Set<L> apply(final RuleSurface<LayerSetCommand<L>> layers) {
        final LayerSet<L> set = new LayerSet<>(full);
        layers.<LayerSetCommand<L>>values(layers.key()).
                forEach(command -> command.accept(set));
        return set;
    }
}
