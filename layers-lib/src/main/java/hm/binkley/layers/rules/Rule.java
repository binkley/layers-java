package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.set.FullnessFunction;
import hm.binkley.layers.set.LayerSetRule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@RequiredArgsConstructor
public abstract class Rule<R>
        implements RuleFunction<R> {
    private final String name;

    @Override
    public String toString() {
        return "[Rule: " + name() + "]";
    }

    public static <T> MostRecentRule<T> mostRecent(final T defaultValue) {
        return new MostRecentRule<>(defaultValue);
    }

    public static SumAllRule sumAll() {
        return new SumAllRule();
    }

    public static DoublingRule doubling() {
        return new DoublingRule();
    }

    public static FloorRule floor(final int floor) {
        return new FloorRule(floor);
    }

    public static <L extends Layer<L>> LayerSetRule<L> layerSet(
            final FullnessFunction<L> full) {
        return new LayerSetRule<>(full);
    }
}
