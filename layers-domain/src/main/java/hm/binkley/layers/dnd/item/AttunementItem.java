package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.Rule;
import hm.binkley.layers.set.LayerSet;

import static hm.binkley.layers.dnd.item.Attunement.attune;

/** @todo Real values for weight/volume */
public class AttunementItem<L extends AttunementItem<L>>
        extends MagicItem<L> {
    public AttunementItem(final LayerSurface layers, final String name,
            final String description, final Type type, final Rarity rarity,
            final Weight weight, final Volume volume, final String notes) {
        // TODO: Why is static method imports not compiling here?
        super(layers, name, description, type, rarity, weight, volume, true,
                notes);
    }

    public AttunementItem(final LayerSurface layers, final String name,
            final String description, final Type type, final Rarity rarity,
            final Weight weight, final Volume volume) {
        this(layers, name, description, type, rarity, weight, volume, "");
    }

    public <K extends Layer<K>> K attuneSaveAndNext(
            final LayerMaker<K> next) {
        return saveAndNext(attune(this)).
                saveAndNext(next);
    }

    final boolean isAttuned() {
        return layers.<LayerSet<?>>get(Attunement.class).contains(this);
    }

    @Override
    public L put(final Object key, final Object value) {
        return value instanceof Rule ? super
                .put(key, new AttunementItemRule<>(this, (Rule<?>) value))
                : super.put(key, value);
    }
}
