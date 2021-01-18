package hm.binkley.layers.set;

import hm.binkley.layers.Layer;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor(access = PACKAGE)
class NamedFullnessFunction<L extends Layer<L>>
        implements FullnessFunction<L> {
    private final BiFunction<LayerSet<L>, L, Boolean> full;
    private final String name;

    @Override
    public Boolean apply(final LayerSet<L> layers, final L layer) {
        return full.apply(layers, layer);
    }

    @Override
    public String toString() {
        return name;
    }
}
