package hm.binkley.layers.dnd.item;

import org.junit.jupiter.api.Test;

import static hm.binkley.layers.dnd.item.Rarity.COMMON;
import static hm.binkley.layers.dnd.item.Type.EQUIPMENT;
import static hm.binkley.layers.dnd.item.Volume.inCuft;
import static hm.binkley.layers.dnd.item.Weight.ONE_POUND;
import static hm.binkley.layers.dnd.item.Weight.WEIGHTLESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("rawtypes")
class ItemTest {
    @Test
    void shouldHaveWeight() {
        assertEquals(ONE_POUND,
                new Item(null, "Bob", "Yer Unkel", EQUIPMENT, COMMON,
                        ONE_POUND, inCuft(2.2f), "").weight());
    }

    @Test
    void shouldHaveVolume() {
        assertEquals(inCuft(2.2f),
                new Item(null, "Bob", "Yer Unkel", EQUIPMENT, COMMON,
                        WEIGHTLESS, inCuft(2.2f), "").volume());
    }
}
