package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.set.FullnessFunction;
import hm.binkley.layers.set.LayerSet;
import hm.binkley.layers.set.LayerSetCommand;
import hm.binkley.layers.set.LayerSetRule;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Rule<L extends Layer, T, R>
        implements RuleFunction<L, T, R> {
    private final String name;

    @Override
    public final String toString() {
        return "Rule: " + name;
    }

    public static <L extends Layer, T> Rule<L, T, T> mostRecent(
            final T defaultValue) {
        return new MostRecentRule<>(defaultValue);
    }

    public static <L extends Layer> Rule<L, Integer, Integer> sumAll() {
        return new SumAllRule<>();
    }

    public static <L extends Layer> Rule<L, Integer, Integer> doubling() {
        return new DoublingRule<>();
    }

    public static <L extends Layer> Rule<L, Integer, Integer> floor(
            final int floor) {
        return new FloorRule<>(floor);
    }

    public static <L extends Layer> Rule<L, LayerSetCommand<L>,
            LayerSet<L>> layerSet(
            final FullnessFunction<L> full) {
        return new LayerSetRule<>(full);
    }
}
