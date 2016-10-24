package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;

@FunctionalInterface
public interface RuleFunction<T, R> {
    R apply(final Layers layers, final Layer layer, final T value);
}
