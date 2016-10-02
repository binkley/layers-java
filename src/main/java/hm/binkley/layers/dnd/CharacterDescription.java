package hm.binkley.layers.dnd;

import hm.binkley.layers.FixedValueKey;
import hm.binkley.layers.Layer;

public enum CharacterDescription {
    NAME;

    public static Layer characterDescription(final String name) {
        final Layer layer = new Layer();
        layer.put(new FixedValueKey(NAME), name);
        return layer;
    }
}
