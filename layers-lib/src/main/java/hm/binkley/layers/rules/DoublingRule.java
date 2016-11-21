package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.RuleSurface;

public class DoublingRule<L extends Layer>
        extends Rule<L, Integer, Integer> {
    DoublingRule() {
        super("Doubling");
    }

    @Override
    public Integer apply(final RuleSurface<L, Integer, Integer> layers) {
        return 2 * Rule.<L>sumAll().apply(layers);
    }
}
