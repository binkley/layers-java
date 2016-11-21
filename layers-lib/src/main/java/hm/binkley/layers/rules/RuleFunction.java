package hm.binkley.layers.rules;

import hm.binkley.layers.Layers.RuleSurface;

import java.util.function.Function;

@FunctionalInterface
public interface RuleFunction<T, R>
        extends Function<RuleSurface<T, R>, R> {}
