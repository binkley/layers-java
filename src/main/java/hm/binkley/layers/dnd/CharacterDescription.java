package hm.binkley.layers.dnd;

import hm.binkley.layers.FixedValueKey;
import hm.binkley.layers.Key;

import java.util.HashMap;
import java.util.Map;

public enum CharacterDescription {
    NAME;

    public static Map<Key, Object> characterDescription(final String name) {
        final Map<Key, Object> layer = new HashMap<>();
        layer.put(new FixedValueKey(NAME), name);
        return layer;
    }
}
