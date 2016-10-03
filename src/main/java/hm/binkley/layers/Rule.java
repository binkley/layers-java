package hm.binkley.layers;

import java.util.function.BiFunction;

public abstract class Rule<T>
        implements BiFunction<Layers, T, T> {
    @Override
    public abstract String toString();

    public static <T> Rule<T> mostRecent(final Object key) {
        return new MostRecentRule<>(key);
    }

    public static Rule<Integer> sumAll(final Object key) {
        return new SumAllRule(key);
    }

    public static Rule<Integer> doubling(final Object key) {
        return new DoublingRule(key);
    }

    public static <T> Rule<T> exactly() {
        return new ExactlyRule<>();
    }

    private static final class MostRecentRule<T>
            extends Rule<T> {
        private final Object key;

        private MostRecentRule(final Object key) {this.key = key;}

        @Override
        public T apply(final Layers layers, final T value) {
            return layers.<T>valuesFor(key).
                    findFirst().
                    get();
        }

        @Override
        public String toString() {
            return "Rule: Most recent";
        }
    }

    private static final class SumAllRule
            extends Rule<Integer> {
        private final Object key;

        private SumAllRule(final Object key) {this.key = key;}

        @Override
        public Integer apply(final Layers layers, final Integer value) {
            return layers.<Integer>valuesFor(key).
                    mapToInt(Integer::intValue).
                    sum();
        }

        @Override
        public String toString() {
            return "Rule: Sum all";
        }
    }

    private static final class DoublingRule
            extends Rule<Integer> {
        private final Object key;

        private DoublingRule(final Object key) {this.key = key;}

        @Override
        public Integer apply(final Layers layers, final Integer value) {
            return 2 * sumAll(key).apply(layers, value);
        }

        @Override
        public String toString() {
            return "Rule: Doubling";
        }
    }

    private static class ExactlyRule<T>
            extends Rule<T> {
        @Override
        public T apply(final Layers layers, final T value) {return value;}

        @Override
        public String toString() {
            return "Rule: Exactly";
        }
    }
}
