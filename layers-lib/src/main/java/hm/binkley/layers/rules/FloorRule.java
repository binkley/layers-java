package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;

import static java.lang.Integer.max;

public class FloorRule
        extends KeyRule<Integer, Integer> {
    protected FloorRule(final Object key) {
        super("Floor", key);
    }

    @Override
    public Integer apply(final Layers layers, final Layer layer,
            final Integer value) {
        return max(value, layers.whatIfWithout(layer).get(key));
    }
}
