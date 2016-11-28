package hm.binkley.layers.dnd.item;

import hm.binkley.layers.Layers.LayerSurface;

import static hm.binkley.layers.dnd.item.Attunement.UNATTUNED;
import static hm.binkley.layers.dnd.item.Rarity.UNCOMMON;
import static hm.binkley.layers.dnd.item.Type.ARMOR;

class AdamantineArmor
        extends MagicItem<AdamantineArmor> {
    AdamantineArmor(final LayerSurface layers) {
        super(layers, "Adamantine Armor",
                "This suit of armor is reinforced with adamantine, one of "
                        + "the hardest substances in existence. While "
                        + "you're wearing it, any critical hit against you "
                        + "becomes a normal hit.", ARMOR, UNCOMMON, UNATTUNED,
                "Medium or heavy, but not hide");
    }
}
