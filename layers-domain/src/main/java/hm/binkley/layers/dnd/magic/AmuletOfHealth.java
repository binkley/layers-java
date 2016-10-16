package hm.binkley.layers.dnd.magic;

import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.values.Value;

import static hm.binkley.layers.dnd.Abilities.CON;

public final class AmuletOfHealth
        extends MagicItem {
    public AmuletOfHealth(final Surface layers) {
        super(layers, "Amulet of Health",
                "Your Constitution score is 19 while you wear this amulet. "
                        + "It has no effect on you if your Constitution is "
                        + "already 19 or higher.", Type.WONDROUS_ITEM,
                Rarity.RARE, Attunement.ATTUNED);
        put(CON, Value.floor(CON, 19));
    }
}
