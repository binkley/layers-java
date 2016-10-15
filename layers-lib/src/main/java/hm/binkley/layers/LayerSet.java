package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.LinkedHashSet;

import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
public class LayerSet
        extends LinkedHashSet<Layer> {
    private final int max;

    public static LayerSet empty() {
        return new LayerSet(0);
    }

    public static LayerSet singleton(final Layer layer) {
        final LayerSet set = new LayerSet(1);
        set.add(layer);
        return set;
    }

    @Override
    public boolean add(final Layer layer) {
        if (max == size())
            throw new IllegalStateException();
        return super.add(layer);
    }

    @Override
    public final String toString() {
        return stream().
                map(Layer::name).
                collect(joining(", ", "(" + max + ")[", "]"));
    }
}
