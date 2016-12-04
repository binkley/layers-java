package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.LayerSurface;

/** @todo Real values for weight/volume */
public class MagicItem<L extends MagicItem<L>>
        extends Item<L> {
    public MagicItem(final LayerSurface layers, final String name,
            final String description, final Type type, final Rarity rarity,
            final Weight weight, final Volume volume,
            final boolean attunement, final String notes) {
        // TODO: Why is static method imports not compiling here?s
        super(layers, name, description, type, weight, volume, notes);
        putDetail(Attunement.class, attunement).
                putDetail(Rarity.class, rarity);
    }
}
