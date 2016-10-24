package hm.binkley.layers.set;

import hm.binkley.layers.Layer;

import java.util.Set;
import java.util.function.BiFunction;

class NamedFullnessFunction<L extends Layer>
        extends FullnessFunction<L> {
    private final BiFunction<Set<L>, L, Boolean> full;
    private final String name;

    NamedFullnessFunction(final BiFunction<Set<L>, L, Boolean> full,
            final String name) {
        this.full = full;
        this.name = name;
    }

    @Override
    public Boolean apply(final Set<L> layers, final L layer) {
        return full.apply(layers, layer);
    }

    @Override
    public String toString() {
        return name;
    }
}
