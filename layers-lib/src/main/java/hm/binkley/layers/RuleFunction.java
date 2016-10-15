package hm.binkley.layers;

@FunctionalInterface
public interface RuleFunction<T> {
    T apply(final Layers layers, final Layer layer, final T value);
}
