package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.set.FullnessFunction;
import hm.binkley.layers.set.LayerSetRule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Rule<L extends Layer<L>, T, R>
        implements RuleFunction<L, T, R> {
    private final String name;

    @Override
    public final String toString() {
        return "Rule: " + name;
    }

    public static <L extends Layer<L>, T> MostRecentRule<L, T> mostRecent(
            final T defaultValue) {
        return new MostRecentRule<>(defaultValue);
    }

    public static <L extends Layer<L>> SumAllRule<L> sumAll() {
        return new SumAllRule<>();
    }

    public static <L extends Layer<L>> DoublingRule<L> doubling() {
        return new DoublingRule<>();
    }

    public static <L extends Layer<L>> FloorRule<L> floor(final int floor) {
        return new FloorRule<>(floor);
    }

    public static <L extends Layer<L>> LayerSetRule<L> layerSet(
            final FullnessFunction<L> full) {
        return new LayerSetRule<>(full);
    }
}
