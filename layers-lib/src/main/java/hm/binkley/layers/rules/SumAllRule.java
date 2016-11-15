package hm.binkley.layers.rules;

import hm.binkley.layers.Layers.RuleSurface;

public class SumAllRule
        extends Rule<Integer, Integer> {
    SumAllRule() {
        super("Sum all");
    }

    @Override
    public Integer apply(final RuleSurface<Integer> layers) {
        return layers.<Integer>plainValuesLastToFirstFor(layers.key()).
                mapToInt(Integer::intValue).
                sum();
    }
}
