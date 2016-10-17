package hm.binkley.layers;

import java.util.Set;
import java.util.function.BiFunction;

/** @todo Consider reference to Layers in addition to others */
public abstract class FullnessFunction
        implements BiFunction<Set<Layer>, Layer, Boolean> {
    @Override
    public abstract String toString();

    public static FullnessFunction named(
            final BiFunction<Set<Layer>, Layer, Boolean> full,
            final String name) {
        return new FullnessFunction() {
            @Override
            public Boolean apply(final Set<Layer> layers, final Layer layer) {
                return full.apply(layers, layer);
            }

            @Override
            public String toString() {
                return name;
            }
        };
    }

    public static FullnessFunction max(final int max) {
        return named((layers, layer) -> max == layers.size(), "Max " + max);
    }
}
