package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.rules.Rule;

import static hm.binkley.layers.dnd.Abilities.CON;
import static hm.binkley.layers.dnd.item.Attunement.ATTUNED;
import static hm.binkley.layers.dnd.item.Rarity.RARE;
import static hm.binkley.layers.dnd.item.Type.WONDROUS_ITEM;

public final class AmuletOfHealth
        extends MagicItem {
    public AmuletOfHealth(final Surface layers) {
        super(layers, "Amulet of Health",
                "Your Constitution score is 19 while you wear this amulet. "
                        + "It has no effect on you if your Constitution is "
                        + "already 19 or higher.", WONDROUS_ITEM, RARE,
                ATTUNED);
        put(CON, 19, Rule::floor);
    }
}
