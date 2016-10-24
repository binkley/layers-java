package hm.binkley.layers.set;

import hm.binkley.layers.Layer;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashSet;

import static hm.binkley.layers.set.FullnessFunction.max;
import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
public class LayerSet<L extends Layer>
        extends LinkedHashSet<L> {
    private final FullnessFunction<L> full;

    public static <L extends Layer> LayerSet<L> empty() {
        return new LayerSet<>(max(0));
    }

    public static <L extends Layer> LayerSet<L> singleton(final L layer) {
        final LayerSet<L> set = new LayerSet<>(max(1));
        set.add(layer);
        return set;
    }

    @Override
    public boolean add(final L layer) {
        if (full.apply(this, layer))
            throw new IllegalStateException();
        return super.add(layer);
    }

    @Override
    public final String toString() {
        return stream().
                map(Layer::name).
                collect(joining(", ", "[", "]"));
    }
}
