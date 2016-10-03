package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.Rule;
import hm.binkley.layers.Value;

import java.util.function.Function;

import static hm.binkley.layers.dnd.Abilities.STR;

public class MagicItems {
    public static Function<Surface, Layer> beltOfGiantStrength(
            final int strength) {
        return layers -> {
            final Layer layer = new Layer(layers, "Belt of Giant Strength");
            layer.put(STR, Value.ofBoth(strength, Rule.exactly()));
            return layer;
        };
    }
}
