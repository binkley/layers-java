package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;

public class SumAllRule
        extends KeyRule<Integer, Integer> {
    protected SumAllRule(final Object key) {
        super("Sum all", key);
    }

    @Override
    public Integer apply(final Layers layers, final Layer layer,
            final Integer value) {
        return layers.<Integer>plainValuesLastToFirstFor(key).
                mapToInt(Integer::intValue).
                sum();
    }
}
