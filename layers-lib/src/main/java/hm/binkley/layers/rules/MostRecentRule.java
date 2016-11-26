package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.RuleSurface;

public class MostRecentRule<L extends Layer<L>, T>
        extends Rule<L, T, T> {
    private final T defaultValue;

    MostRecentRule(final T defaultValue) {
        super("Most recent");
        this.defaultValue = defaultValue;
    }

    @Override
    public T apply(final RuleSurface<L, T, T> layers) {
        return layers.reverseValues().
                findFirst().
                orElse(defaultValue);
    }
}
