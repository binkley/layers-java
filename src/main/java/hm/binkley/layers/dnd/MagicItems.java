package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Rule;
import hm.binkley.layers.Value;

import static hm.binkley.layers.dnd.Abilities.STR;

public class MagicItems {
    public static LayerMaker beltOfGiantStrength(final int strength) {
        return layers -> {
            final Layer layer = new Layer(layers, "Belt of Giant Strength");
            layer.put(STR, Value.ofBoth(strength, Rule.exactly()));
            return layer;
        };
    }
}
