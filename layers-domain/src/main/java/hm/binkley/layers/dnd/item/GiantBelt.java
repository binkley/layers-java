package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.LayerSurface;

import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.item.Type.WONDROUS_ITEM;
import static hm.binkley.layers.rules.Rule.floor;

/**
 * {@code GiantBelt} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
public abstract class GiantBelt<L extends GiantBelt<L>>
        extends AttunementItem<L> {
    protected GiantBelt(final LayerSurface layers, final String giantKind,
            final Rarity rarity, final int strength) {
        super(layers, "Belt of " + giantKind + " Giant Strength",
                "While wearing this belt, your Strength score changes to a "
                        + "score granted by the belt. If your Strength is "
                        + "already equal to or greater than the belt's "
                        + "score, the item has no effect on you.\n"
                        + "Six varieties of this belt exist, corresponding "
                        + "with and having rarity according to the six "
                        + "kinds of true giants. The belt of stone giant "
                        + "strength and the belt of frost giant strength "
                        + "look different, but they have the same effect.",
                WONDROUS_ITEM, rarity, Weight.inPounds(0), Volume.inCuft(0));
        put(STR, key -> floor(strength));
    }
}
