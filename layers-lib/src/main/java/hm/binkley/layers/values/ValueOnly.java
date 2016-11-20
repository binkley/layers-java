package hm.binkley.layers.values;

import hm.binkley.layers.Bug;
import hm.binkley.layers.Layers.RuleSurface;
import hm.binkley.layers.rules.Rule;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@EqualsAndHashCode
@RequiredArgsConstructor
public final class ValueOnly<T, R>
        implements Value<T, R> {
    private final T value;

    @Override
    public Optional<T> value() {
        return Optional.of(value);
    }

    @Override
    public Optional<Rule<T, R>> rule() {
        return Optional.empty();
    }

    @Override
    public R apply(final RuleSurface<T> layers) {
        throw new Bug("Should never apply a value-only element");
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
