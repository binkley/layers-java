package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.Surface;

import java.util.Map;

/** @todo Figure out how to use types for height/weight */
public class Item
        extends Layer {
    public Item(final Surface layers, final String name,
            final String description, final Type type, final Rarity rarity,
            final float weight, final float volume, final String notes) {
        super(layers, name);
        final Map<Object, Object> details = details();
        details.put("Description", description);
        details.put(Type.class, type);
        details.put(Rarity.class, rarity);
        details.put("Weight", weight);
        details.put("Volume", volume);
        details().put("Notes", notes);
    }

    public float weight() {
        return (float) details().get("Weight");
    }

    public float volume() {
        return (float) details().get("Volume");
    }
}
