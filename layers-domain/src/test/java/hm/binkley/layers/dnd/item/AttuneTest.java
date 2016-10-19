package hm.binkley.layers.dnd.item;

import org.junit.jupiter.api.Test;

import static hm.binkley.layers.dnd.item.Attunement.ATTUNED;
import static hm.binkley.layers.dnd.item.Rarity.LEGENDARY;
import static hm.binkley.layers.dnd.item.Type.WONDROUS_ITEM;
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
