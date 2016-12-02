package hm.binkley.layers.set;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.RuleSurface;
import hm.binkley.layers.rules.Rule;

public final class LayerSetRule<L extends Layer<L>>
        extends Rule<LayerSet<L>> {
    private final FullnessFunction<L> full;

    public LayerSetRule(final FullnessFunction<L> full) {
        super(full.toString());
        this.full = full;
    }

    @Override
    public LayerSet<L> apply(
            final RuleSurface layers) {
        final LayerSet<L> set = new LayerSet<>(full);
        layers.<LayerSetCommand<L>>values().
                forEach(command -> command.accept(layers, set));
        return set;
    }
}
