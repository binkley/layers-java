package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.Surface;

import static hm.binkley.layers.Value.ofValue;
import static hm.binkley.layers.dnd.Abilities.CHA;
import static hm.binkley.layers.dnd.Abilities.CON;
import static hm.binkley.layers.dnd.Abilities.DEX;
import static hm.binkley.layers.dnd.Abilities.INT;
import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.Abilities.WIS;

public final class Races {
    public static Layer plainHuman(final Surface layers) {
        final Layer layer = new Layer(layers, "Human");
        layer.put(STR, ofValue(1));
        layer.put(DEX, ofValue(1));
        layer.put(CON, ofValue(1));
        layer.put(INT, ofValue(1));
        layer.put(WIS, ofValue(1));
        layer.put(CHA, ofValue(1));
        return layer;
    }
}
