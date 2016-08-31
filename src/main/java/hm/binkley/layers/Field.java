package hm.binkley.layers;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * {@code Field} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
@RequiredArgsConstructor
@SuppressWarnings("WeakerAccess")
public class Field<T>
        implements Function<List<T>, T> {
    public static <T> T last(final List<T> a) {
        return a.get(a.size() - 1);
    }

    public static final Field<Object> LAST = new Field<>(Object.class,
            Field::last);
    public final Class<T> type;
    private final Function<List<T>, T> rule;

    @Override
    public final T apply(final List<T> values) {
        return rule.apply(values);
    }

    public static final class StringField
            extends Field<String> {
        public StringField() {
            super(String.class, Field::last);
        }
    }

    public static final class IntegerField
            extends Field<Integer> {
        private static final IntegerField ADDITATIVE = new IntegerField(a -> {
            // Could use stream here, this is better behaved
            int sum = 0;
            for (final Integer i : a)
                sum += i;
            return sum;
        });

        public static IntegerField additiveIntegerField() {
            return ADDITATIVE;
        }

        public IntegerField(final Function<List<Integer>, Integer> rule) {
            super(Integer.class, rule);
        }
    }

    public static final class DoubleField
            extends Field<Double> {
        public DoubleField(final Function<List<Double>, Double> rule) {
            super(Double.class, rule);
        }
    }

    public static final class CollectionField
            extends Field<Collection> {
        public CollectionField(final Supplier<Collection> merged) {
            super(Collection.class, a -> {
                final Collection all = merged.get();
                a.forEach(l -> all.addAll(l));
                return all;
            });
        }
    }
}
