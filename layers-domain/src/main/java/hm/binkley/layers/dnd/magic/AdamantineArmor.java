package hm.binkley.layers.dnd.magic;

import hm.binkley.layers.Layers.Surface;

import static hm.binkley.layers.dnd.magic.Attunement.UNATTUNED;
import static hm.binkley.layers.dnd.magic.Rarity.UNCOMMON;
import static hm.binkley.layers.dnd.magic.Type.ARMOR;

class AdamantineArmor
        extends MagicItem {
    AdamantineArmor(final Surface layers) {
        super(layers, "Adamantine Armor",
                "This suit of armor is reinforced with adamantine, one of "
                        + "the hardest substances in existence. While "
                        + "you're wearing it, any critical hit against you "
                        + "becomes a normal hit.", ARMOR, UNCOMMON, UNATTUNED,
                "medium or heavy, but not hide");
    }
}
