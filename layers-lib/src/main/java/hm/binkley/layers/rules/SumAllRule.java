package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.RuleSurface;

public class SumAllRule<L extends Layer<L>>
        extends Rule<L, Integer, Integer> {
    SumAllRule() {
        super("Sum all");
    }

    @Override
    public Integer apply(final RuleSurface<L, Integer, Integer> layers) {
        return layers.<Integer>values().
                mapToInt(Integer::intValue).
                sum();
    }
}
