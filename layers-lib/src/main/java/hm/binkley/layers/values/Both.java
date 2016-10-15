package hm.binkley.layers.values;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.rules.Rule;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@EqualsAndHashCode
@RequiredArgsConstructor
public final class Both<T>
        implements Value<T> {
    private final T value;
    private final Rule<T> rule;

    @Override
    public Optional<T> value() {
        return Optional.of(value);
    }

    @Override
    public Optional<Rule<T>> rule() {
        return Optional.of(rule);
    }

    @Override
    public T apply(final Layers layers, final Layer layer) {
        return rule.apply(layers, layer, value);
    }

    @Override
    public String toString() {
        return "{" + value + ", " + rule + "}";
    }
}
