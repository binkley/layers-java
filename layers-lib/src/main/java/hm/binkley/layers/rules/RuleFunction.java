package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.RuleSurface;

import java.util.function.Function;

@SuppressWarnings("WeakerAccess")
@FunctionalInterface
public interface RuleFunction<L extends Layer<L>, T, R>
        extends Function<RuleSurface<L, T, R>, R> {}
