package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public abstract class Rule<T>
        implements RuleFunction<T> {
    private final String name;

    @Override
    public final String toString() {
        return "Rule: " + name;
    }

    public static <T> Rule<T> mostRecent(final Object key) {
        return new MostRecentRule<>(key);
    }

    public static Rule<Integer> sumAll(final Object key) {
        return new SumAllRule(key);
    }

    public static Rule<Integer> doubling(final Object key) {
        return new DoublingRule(key);
    }

    public static Rule<Integer> floor(final Object key) {
        return new FloorRule(key);
    }

    public static Rule<Set<Layer>> layerSet(final Object key, final int max) {
        return new LayerSetRule(key, max);
    }
}
