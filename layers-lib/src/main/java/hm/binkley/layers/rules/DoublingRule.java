package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;

public class DoublingRule
        extends KeyRule<Integer> {
    protected DoublingRule(final Object key) {
        super("Doubling", key);
    }

    @Override
    public Integer apply(final Layers layers, final Layer layer,
            final Integer value) {
        return 2 * sumAll(key).apply(layers, layer, value);
    }
}
