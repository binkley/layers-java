package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;

public class SumAllRule
        extends KeyRule<Integer> {
    protected SumAllRule(final Object key) {
        super("Sum all", key);
    }

    @Override
    public Integer apply(final Layers layers, final Layer layer,
            final Integer value) {
        return layers.<Integer>plainValuesFor(key).
                mapToInt(Integer::intValue).
                sum();
    }
}
