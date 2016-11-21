package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.RuleSurface;

public class MostRecentRule<L extends Layer, T>
        extends Rule<L, T, T> {
    private final T defaultValue;

    MostRecentRule(final T defaultValue) {
        super("Most recent");
        this.defaultValue = defaultValue;
    }

    @Override
    public T apply(final RuleSurface<L, T, T> layers) {
        final Object key = layers.key();
        return layers.reverseValues(key).
                findFirst().
                orElse(defaultValue);
    }
}
