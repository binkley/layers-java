package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public abstract class Rule<T>
        implements BiFunction<Layers, T, T> {
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

    private static final class MostRecentRule<T>
            extends Rule<T> {
        private final Object key;

        private MostRecentRule(final Object key) {
            super("Most recent");
            this.key = key;
        }

        @Override
        public T apply(final Layers layers, final T value) {
            return layers.<T>plainValuesFor(key).
                    findFirst().
                    get();
        }
    }

    private static final class SumAllRule
            extends Rule<Integer> {
        private final Object key;

        private SumAllRule(final Object key) {
            super("Sum all");
            this.key = key;
        }

        @Override
        public Integer apply(final Layers layers, final Integer value) {
            return layers.<Integer>plainValuesFor(key).
                    mapToInt(Integer::intValue).
                    sum();
        }
    }

    private static final class DoublingRule
            extends Rule<Integer> {
        private final Object key;

        private DoublingRule(final Object key) {
            super("Doubling");
            this.key = key;
        }

        @Override
        public Integer apply(final Layers layers, final Integer value) {
            return 2 * sumAll(key).apply(layers, value);
        }
    }
}
