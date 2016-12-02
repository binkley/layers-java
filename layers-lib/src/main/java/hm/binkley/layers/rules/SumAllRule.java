package hm.binkley.layers.rules;

import hm.binkley.layers.Layers.RuleSurface;

public class SumAllRule
        extends Rule<Integer> {
    SumAllRule() {
        super("Sum all");
    }

    @Override
    public Integer apply(final RuleSurface layers) {
        return layers.<Integer>values().
                mapToInt(Integer::intValue).
                sum();
    }
}
