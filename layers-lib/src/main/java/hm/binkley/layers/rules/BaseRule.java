package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.BaseRule.BaseRulesLayer;

import java.util.function.Function;

import static java.util.ServiceLoader.load;

@FunctionalInterface
public interface BaseRule
        extends Function<LayerSurface, BaseRulesLayer> {
    static BaseRulesLayer baseRules(final LayerSurface layers) {
        final BaseRulesLayer layer = new BaseRulesLayer(layers);
        load(BaseRule.class).forEach(layer::blend);
        return layer;
    }

    final class BaseRulesLayer
            extends Layer<BaseRulesLayer> {
        public BaseRulesLayer(final LayerSurface layers) {
            super(layers, "Base rules");
        }
    }
}
