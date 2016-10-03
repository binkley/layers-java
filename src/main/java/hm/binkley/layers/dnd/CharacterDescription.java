package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Value;

public enum CharacterDescription {
    NAME;

    public static Layer characterDescription(final String name) {
        final Layer layer = new Layer();
        layer.put(NAME, Value.ofValue(name));
        return layer;
    }
}
