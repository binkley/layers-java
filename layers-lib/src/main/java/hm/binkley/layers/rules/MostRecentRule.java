package hm.binkley.layers.rules;

import hm.binkley.layers.Layers.RuleSurface;

public class MostRecentRule<T>
        extends Rule<T, T> {
    private final T defaultValue;

    MostRecentRule(final T defaultValue) {
        super("Most recent");
        this.defaultValue = defaultValue;
    }

    @Override
    public T apply(final RuleSurface<T> layers) {
        final Object key = layers.key();
        return layers.plainValuesLastToFirstFor(key).
                findFirst().
                orElse(defaultValue);
    }
}
