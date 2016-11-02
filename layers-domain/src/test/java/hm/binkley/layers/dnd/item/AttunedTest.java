package hm.binkley.layers.dnd.item;

import org.junit.jupiter.api.Test;

import static hm.binkley.layers.dnd.item.Attunement.ATTUNED;
import static hm.binkley.layers.dnd.item.Rarity.LEGENDARY;
import static hm.binkley.layers.dnd.item.Type.WONDROUS_ITEM;
import static hm.binkley.layers.set.LayerSetCommand.add;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AttunedTest {
    @Test
    void shouldDisplayNicely() {
        final String display = new Attuned(null, add("Attune Bob",
                new MagicItem(null, "Bob", "Cool bob", WONDROUS_ITEM,
                        LEGENDARY, ATTUNED, "Some notes about Bob"))).
                toString();

        assertTrue(display.contains("Attune"));
    }
}
