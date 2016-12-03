package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.RuleSurface;
import hm.binkley.layers.rules.Rule;

final class SumWeightRule
        extends Rule<Weight> {
    SumWeightRule() {
        super("Sum weight");
    }

    @Override
    public Weight apply(final RuleSurface layers) {
        return layers.<Weight>values().
                reduce(Weight.inPounds(0), Weight::add);
    }
}
