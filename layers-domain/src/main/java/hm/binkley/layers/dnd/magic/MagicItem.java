package hm.binkley.layers.dnd.magic;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.Surface;

import java.util.Map;

public class MagicItem
        extends Layer {
    public MagicItem(final Surface layers, final String name,
            final String description, final Type type, final Rarity rarity,
            final Attunement attunement, final String notes) {
        this(layers, name, description, type, rarity, attunement);
        details().put("Notes", notes);
    }

    public MagicItem(final Surface layers, final String name,
            final String description, final Type type, final Rarity rarity,
            final Attunement attunement) {
        super(layers, name);
        final Map<Object, Object> details = details();
        details.put("Description", description);
        details.put(Type.class, type);
        details.put(Rarity.class, rarity);
        details.put(Attunement.class, attunement);
    }

    public <L extends Layer> L attuneAndNext(final LayerMaker<L> next) {
        return layers.saveAndNext(new Attune(layers, this), next);
    }
}
