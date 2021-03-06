package hm.binkley.layers.rules;

import hm.binkley.layers.Layers.RuleSurface;

import java.util.function.Function;

@SuppressWarnings("WeakerAccess")
@FunctionalInterface
public interface RuleFunction<R>
        extends Function<RuleSurface, R> {}
