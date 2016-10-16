package hm.binkley.layers.dnd.magic;

import org.junit.jupiter.api.Test;

import static hm.binkley.layers.dnd.magic.MagicItems.Attunement.ATTUNED;
import static hm.binkley.layers.dnd.magic.MagicItems.Rarity.LEGENDARY;
import static hm.binkley.layers.dnd.magic.MagicItems.Type.WONDROUS_ITEM;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AttuneTest {
    @Test
    void shouldDisplayNicely() {
        final String display = new Attune(null,
                new MagicItem(null, "Bob", "Cool bob", WONDROUS_ITEM,
                        LEGENDARY, ATTUNED, "Some notes about Bob")).
                toString();

        assertTrue(display.contains("Attune"));
    }
}
