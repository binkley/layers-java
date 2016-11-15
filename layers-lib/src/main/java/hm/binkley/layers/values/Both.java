package hm.binkley.layers.values;

import hm.binkley.layers.Layers.RuleSurface;
import hm.binkley.layers.rules.Rule;
import hm.binkley.layers.set.LayerSetCommand;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@EqualsAndHashCode
@RequiredArgsConstructor
public final class Both<T, R>
        implements Value<T, R> {
    private final T value;
    private final Rule<T, R> rule;

    @Override
    public Optional<T> value() {
        return Optional.of(value);
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
        final String v;
        if (value instanceof LayerSetCommand)
            v = ((LayerSetCommand) value).name();
        else
            v = value.toString();
        return "{" + v + ", " + rule + "}";
    }
}
