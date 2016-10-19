package hm.binkley.layers;

import java.util.Set;
import java.util.function.BiFunction;

/** @todo Consider reference to Layers in addition to others */
public abstract class FullnessFunction<L extends Layer>
        implements BiFunction<Set<L>, L, Boolean> {
    @Override
    public abstract String toString();

    public static <L extends Layer> FullnessFunction<L> named(
            final BiFunction<Set<L>, L, Boolean> full, final String name) {
        return new FullnessFunction<L>() {
            @Override
            public Boolean apply(final Set<L> layers, final L layer) {
                return full.apply(layers, layer);
            }

            @Override
            public String toString() {
                return name;
            }
        };
    }

    public static <L extends Layer> FullnessFunction<L> max(final int max) {
        return named((layers, layer) -> max == layers.size(), "Max " + max);
    }
}
