package hm.binkley.layers.rules;

import hm.binkley.layers.Layers.RuleSurface;

import static java.lang.Integer.max;

public class FloorRule
        extends Rule<Integer, Integer> {
    FloorRule() {
        super("Floor");
    }

    @Override
    public Integer apply(final RuleSurface<Integer> layers) {
        return max(layers.currentValue(), layers.valueWithout());
    }
}
