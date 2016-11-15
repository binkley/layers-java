package hm.binkley.layers.values;

import hm.binkley.layers.Layers.RuleSurface;
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

    @Override
    public R apply(final RuleSurface<T> layers) {
        return rule.apply(layers);
    }

    @Override
    public String toString() {
        return "{" + rule + "}";
    }
}
