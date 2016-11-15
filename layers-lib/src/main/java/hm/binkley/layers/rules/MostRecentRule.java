package hm.binkley.layers.rules;

import hm.binkley.layers.Layers.RuleSurface;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class MostRecentRule<T>
        extends Rule<T, T> {
    MostRecentRule() {
        super("Most recent");
    }

    @Override
    public T apply(final RuleSurface<T> layers) {
        final Object key = layers.key();
        return layers.<T>plainValuesLastToFirstFor(key).
                findFirst().
                orElseThrow(noValueForKey(key));
    }

    private static Supplier<NoSuchElementException> noValueForKey(
            final Object key) {
        return () -> new NoSuchElementException(
                "No value present for key: " + key);
    }
}
