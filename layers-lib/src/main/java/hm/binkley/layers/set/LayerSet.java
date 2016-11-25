package hm.binkley.layers.set;

import hm.binkley.layers.Layer;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashSet;

import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
public class LayerSet<L extends Layer<L>>
        extends LinkedHashSet<L> {
    private final FullnessFunction<L> full;

    @Override
    public boolean add(final L layer) {
        if (full.apply(this, layer))
            throw new IllegalStateException();
        return super.add(layer);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public final String toString() {
        return stream().
                map(Layer::name).
                collect(joining(", ", "[", "]"));
    }
}
