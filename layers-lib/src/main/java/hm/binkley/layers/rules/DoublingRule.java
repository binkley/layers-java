package hm.binkley.layers.rules;

import hm.binkley.layers.Layers.RuleSurface;

public class DoublingRule
        extends Rule<Integer, Integer> {
    DoublingRule() {
        super("Doubling");
    }

    @Override
    public Integer apply(final RuleSurface<Integer, Integer> layers) {
        return 2 * sumAll().apply(layers);
    }
}
