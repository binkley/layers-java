package hm.binkley.layers.values;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.rules.Rule;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@EqualsAndHashCode
@RequiredArgsConstructor
public final class ValueOnly<T>
        implements Value<T> {
    private final T value;

    @Override
    public Optional<T> value() {
        return Optional.of(value);
    }

    @Override
    public Optional<Rule<T>> rule() {
        return Optional.empty();
    }

    @Override
    public T apply(final Layers layers, final Layer layer) {
        throw new NullPointerException("Missing rule for value: " + this);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
