package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.Value;

public enum CharacterDescription {
    NAME;

    public static Layer characterDescription(final Layers layers,
            final String name) {
        final Layer layer = layers.newLayer(Layer::new);
        layer.put(NAME, Value.ofValue(name));
        return layer;
    }
}
