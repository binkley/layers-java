package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Value;

public enum Characters {
    NAME;

    public static LayerMaker characterDescription(final String name) {
        return layers -> {
            final Layer layer = new Layer(layers, "Character description");
            layer.put(NAME, Value.ofValue(name));
            return layer;
        };
    }
}
