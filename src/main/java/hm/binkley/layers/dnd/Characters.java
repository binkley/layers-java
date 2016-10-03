package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.Value;

import java.util.function.Function;

public enum Characters {
    NAME;

    public static Function<Surface, Layer> characterDescription(
            final String name) {
        return layers -> {
            final Layer layer = new Layer(layers, "Character description");
            layer.put(NAME, Value.ofValue(name));
            return layer;
        };
    }
}
