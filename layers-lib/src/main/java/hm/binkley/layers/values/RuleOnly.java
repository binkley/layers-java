package hm.binkley.layers.values;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.rules.Rule;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@EqualsAndHashCode
@RequiredArgsConstructor
public final class RuleOnly<T, R>
        implements Value<T, R> {
    private final Rule<T, R> rule;

    @Override
    public Optional<T> value() {
        return Optional.empty();
    }

    @Override
    public Optional<Rule<T, R>> rule() {
        return Optional.of(rule);
    }

    /** @todo Rethink rule-only apply with {@code null} */
    @Override
    public R apply(final Layers layers, final Layer layer) {
        return rule.apply(layers, layer, null);
    }

    @Override
    public String toString() {
        return "{" + rule + "}";
    }
}
