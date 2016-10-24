package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;

public class MostRecentRule<T>
        extends KeyRule<T, T> {
    protected MostRecentRule(final Object key) {
        super("Most recent", key);
    }

    @Override
    public T apply(final Layers layers, final Layer layer, final T value) {
        return layers.<T, T>plainValuesFor(key).
                findFirst().
                orElseThrow(noValueForKey());
    }
}
