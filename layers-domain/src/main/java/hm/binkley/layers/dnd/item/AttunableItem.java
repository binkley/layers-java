package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.set.LayerSet;

import static hm.binkley.layers.dnd.item.Attunement.ATTUNED;
import static hm.binkley.layers.set.LayerSetCommand.add;
import static hm.binkley.layers.set.LayerSetCommand.remove;

/** @todo Real values for weight/volume */
public class AttunableItem<L extends AttunableItem<L>>
        extends MagicItem<L> {
    public AttunableItem(final LayerSurface layers, final String name,
            final String description, final Type type, final Rarity rarity,
            final String notes) {
        // TODO: Why is static method imports not compiling here?
        super(layers, name, description, type, rarity, ATTUNED, notes);
        putDetail(Attuned.class, ATTUNED);
    }

    public AttunableItem(final LayerSurface layers, final String name,
            final String description, final Type type, final Rarity rarity) {
        this(layers, name, description, type, rarity, "");
    }

    public boolean isAttuned() {
        return layers.<LayerSet<?>>get(Attuned.class).contains(this);
    }

    @SuppressWarnings("unchecked")
    public <K extends Layer<K>> K attuneAndNext(final LayerMaker<K> next) {
        return layers.saveAndNext(
                new Attuned<>(layers, add("Attune " + name(), (L) this)),
                next);
    }

    @SuppressWarnings("unchecked")
    public <K extends Layer<K>> K detuneAndNext(final LayerMaker<K> next) {
        return layers.saveAndNext(
                new Attuned<>(layers, remove("Detune " + name(), (L) this)),
                next);
    }
}
