package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.set.LayerSet;

import static hm.binkley.layers.set.LayerSetCommand.add;
import static hm.binkley.layers.set.LayerSetCommand.remove;

/** @todo Real values for weight/volume */
public class MagicItem<L extends MagicItem<L>>
        extends Item<L> {
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

    public boolean isAttuned() {
        return layers.<LayerSet<?>>get(Attuned.class).contains(this);
    }

    /** @todo Contrast with {@link Attuned#attune(MagicItem)} and pick one */
    @SuppressWarnings("unchecked")
    public <K extends Layer<K>> K attuneAndNext(final LayerMaker<K> next) {
        return layers.saveAndNext(
                new Attuned<>(layers, add("Attune " + name(), (L) this)),
                next);
    }

    /** @todo Contrast with {@link Attuned#detune(MagicItem)} and pick one */
    @SuppressWarnings("unchecked")
    public <K extends Layer<K>> K detuneAndNext(final LayerMaker<K> next) {
        return layers.saveAndNext(
                new Attuned<>(layers, remove("Detune " + name(), (L) this)),
                next);
    }
}
