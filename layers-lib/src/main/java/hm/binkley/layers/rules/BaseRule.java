package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.Surface;

import static java.util.ServiceLoader.load;

@FunctionalInterface
public interface BaseRule
        extends LayerMaker<Layer> {
    static Layer baseRules(final Surface layers) {
        final Layer layer = new Layer(layers, "Base rules");
        load(BaseRule.class).forEach(layer::blend);
        return layer;
    }
}
