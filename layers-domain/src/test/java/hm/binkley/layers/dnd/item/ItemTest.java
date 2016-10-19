package hm.binkley.layers.dnd.item;

import org.junit.jupiter.api.Test;

import static hm.binkley.layers.dnd.item.Rarity.COMMON;
import static hm.binkley.layers.dnd.item.Type.EQUIPMENT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {
    @Test
    void shouldHaveWeight() {
        assertEquals(1.1f,
                new Item(null, "Bob", "Yer Unkel", EQUIPMENT, COMMON, 1.1f,
                        2.2f, "").weight());
    }

    @Test
    void shouldHaveVolume() {
        assertEquals(2.2f,
                new Item(null, "Bob", "Yer Unkel", EQUIPMENT, COMMON, 1.1f,
                        2.2f, "").volume());
    }
}
