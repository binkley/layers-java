package hm.binkley.layers;

import java.util.LinkedHashSet;

import static java.util.stream.Collectors.joining;

public class LayerSet
        extends LinkedHashSet<Layer> {
    public static LayerSet singleton(final Layer layer) {
        final LayerSet set = new LayerSet();
        set.add(layer);
        return set;
    }

    @Override
    public final String toString() {
        return stream().
                map(Layer::name).
                collect(joining(", ", "[", "]"));
    }
}
