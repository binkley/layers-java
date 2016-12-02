package hm.binkley.layers.rules;

import hm.binkley.layers.Layers.RuleSurface;

public class MostRecentRule<T>
        extends Rule<T> {
    private final T defaultValue;

    MostRecentRule(final T defaultValue) {
        super("Most recent");
        this.defaultValue = defaultValue;
    }

    @Override
    public T apply(final RuleSurface layers) {
        return layers.<T>reverseValues().
                findFirst().
                orElse(defaultValue);
    }
}
