package hm.binkley.layers;

import static java.util.ServiceLoader.load;

@FunctionalInterface
public interface BaseRule
        extends LayerMaker<Layer> {
    static Layer baseRules(final Layers.Surface layers) {
        final Layer layer = new Layer(layers, "Base rules");
        load(BaseRule.class).forEach(layer::blend);
        return layer;
    }
}
