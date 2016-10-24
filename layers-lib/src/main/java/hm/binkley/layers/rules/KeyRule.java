package hm.binkley.layers.rules;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Supplier;

public abstract class KeyRule<T, R>
        extends Rule<T, R> {
    protected final Object key;

    protected KeyRule(final String name, final Object key) {
        super(name);
        this.key = key;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o)
            return true;
        if (null == o || getClass() != o.getClass())
            return false;
        final KeyRule<?, ?> that = (KeyRule<?, ?>) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getClass(), key);
    }

    protected Supplier<NoSuchElementException> noValueForKey() {
        return () -> new NoSuchElementException(
                "No value present for key: " + key);
    }
}
