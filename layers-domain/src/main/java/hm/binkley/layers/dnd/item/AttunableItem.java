package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.Rule;
import hm.binkley.layers.set.LayerSet;

import static hm.binkley.layers.dnd.item.Attuned.attune;
import static hm.binkley.layers.dnd.item.Attunement.ATTUNED;

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

    public <K extends Layer<K>> K attuneSaveAndNext(final LayerMaker<K> next) {
        return saveAndNext(attune(this)).
                saveAndNext(next);
    }

    final boolean isAttuned() {
        return layers.<LayerSet<?>>get(Attuned.class).contains(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public L put(final Object key, final Object value) {
        return value instanceof Rule ? super.put(key,
                new AttunableItemRule<>((L) this,
                        (Rule<L, Object, Object>) value))
                : super.put(key, value);
    }
}
