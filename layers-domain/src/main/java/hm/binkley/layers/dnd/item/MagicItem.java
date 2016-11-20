package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.LayerSurface;

import static hm.binkley.layers.set.LayerSetCommand.add;

/** @todo Real values for weight/volume */
public class MagicItem
        extends Item {
    public MagicItem(final LayerSurface layers, final String name,
            final String description, final Type type, final Rarity rarity,
            final Attunement attunement, final String notes) {
        // TODO: Why is static method imports not compiling here?s
        super(layers, name, description, type, rarity, Weight.inPounds(0),
                Volume.inCuft(0), notes);
        putDetail(Attuned.class, attunement);
    }

    public MagicItem(final LayerSurface layers, final String name,
            final String description, final Type type, final Rarity rarity,
            final Attunement attunement) {
        this(layers, name, description, type, rarity, attunement, "");
    }

    public <L extends Layer> L attuneAndNext(final LayerMaker<L> next) {
        return layers.saveAndNext(
                new Attuned(layers, add("Attune " + name(), this)), next);
    }
}
