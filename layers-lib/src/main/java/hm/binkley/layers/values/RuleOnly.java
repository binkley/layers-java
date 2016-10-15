package hm.binkley.layers.values;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.rules.Rule;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@EqualsAndHashCode
@RequiredArgsConstructor
public final class RuleOnly<T>
        implements Value<T> {
    private final Rule<T> rule;

    @Override
    public Optional<T> value() {
        return Optional.empty();
    }

    @Override
    public Optional<Rule<T>> rule() {
        return Optional.of(rule);
    }

    /** @todo Rethink rule-only apply with {@code null} */
    @Override
    public T apply(final Layers layers, final Layer layer) {
        return rule.apply(layers, layer, null);
    }

    @Override
    public String toString() {
        return "{" + rule + "}";
    }
}
