package hm.binkley.layers.dnd.item;

import org.junit.jupiter.api.Test;

import static hm.binkley.layers.dnd.item.Rarity.COMMON;
import static hm.binkley.layers.dnd.item.Type.EQUIPMENT;
import static hm.binkley.layers.dnd.item.Volume.inCuft;
import static hm.binkley.layers.dnd.item.Weight.inPounds;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("rawtypes")
class ItemTest {
    @Test
    void shouldHaveWeight() {
        assertEquals(inPounds(1.1f),
                new Item(null, "Bob", "Yer Unkel", EQUIPMENT, COMMON,
                        inPounds(1.1f), inCuft(2.2f), "").weight());
    }

    @Test
    void shouldHaveVolume() {
        assertEquals(inCuft(2.2f),
                new Item(null, "Bob", "Yer Unkel", EQUIPMENT, COMMON,
                        inPounds(1.1f), inCuft(2.2f), "").volume());
    }
}
