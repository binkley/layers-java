package hm.binkley.layers;

import java.util.function.Predicate;

@FunctionalInterface
public interface LayerFilter
        extends Predicate<LayerView> {}
