package hm.binkley.layers.rules;

import hm.binkley.layers.FullnessFunction;
import hm.binkley.layers.Layer;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public abstract class Rule<T, R>
        implements RuleFunction<T, R> {
    private final String name;

    @Override
    public final String toString() {
        return "Rule: " + name;
    }

    public static <T> Rule<T, T> mostRecent(final Object key) {
        return new MostRecentRule<>(key);
    }

    public static Rule<Integer, Integer> sumAll(final Object key) {
        return new SumAllRule(key);
    }

    public static Rule<Integer, Integer> doubling(final Object key) {
        return new DoublingRule(key);
    }

    public static Rule<Integer, Integer> floor(final Object key) {
        return new FloorRule(key);
    }

    public static <L extends Layer> Rule<Set<L>, Set<L>> layerSet(final Object key,
            final FullnessFunction<L> full) {
        return new LayerSetRule<>(key, full);
    }
}
