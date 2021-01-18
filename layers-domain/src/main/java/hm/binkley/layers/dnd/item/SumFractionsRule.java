package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.RuleSurface;
import hm.binkley.layers.rules.Rule;

final class SumFractionsRule<F extends Fraction<F>>
        extends Rule<F> {
    private final F initialValue;

    static <F extends Fraction<F>> SumFractionsRule<F> sumFractions(
            final String name, final F initialValue) {
        return new SumFractionsRule<>(name, initialValue);
    }

    private SumFractionsRule(final String name, final F initialValue) {
        super(name);
        this.initialValue = initialValue;
    }

    @Override
    public F apply(final RuleSurface layers) {
        return layers.<F>values().
                reduce(initialValue, Fraction::add);
    }
}
