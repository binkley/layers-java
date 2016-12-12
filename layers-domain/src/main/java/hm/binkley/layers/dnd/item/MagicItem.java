package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.LayerSurface;

public class MagicItem<L extends MagicItem<L>>
        extends Item<L> {
    public MagicItem(final LayerSurface layers, final String name,
            final String description, final Type type, final Rarity rarity,
            final Weight weight, final Volume volume,
            final boolean attunement, final String notes) {
        super(layers, name, description, type, weight, volume, notes);
        putDetail(Attunement.class, attunement).
                putDetail(Rarity.class, rarity);
    }
}
