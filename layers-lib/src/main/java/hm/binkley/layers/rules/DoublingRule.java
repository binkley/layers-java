package hm.binkley.layers.rules;

import hm.binkley.layers.Layers.RuleSurface;

public class DoublingRule
        extends Rule<Integer> {
    DoublingRule() {
        super("Doubling");
    }

    @Override
    public Integer apply(final RuleSurface layers) {
        return 2 * sumAll().apply(layers);
    }
}
