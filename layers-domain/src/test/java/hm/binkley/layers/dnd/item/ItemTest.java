package hm.binkley.layers.dnd.item;

import org.junit.jupiter.api.Test;

import static hm.binkley.layers.dnd.item.Type.EQUIPMENT;
import static hm.binkley.layers.dnd.item.Volume.ONE_CUBIC_FOOT;
import static hm.binkley.layers.dnd.item.Weight.ONE_POUND;
import static hm.binkley.layers.dnd.item.Weight.WEIGHTLESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("rawtypes")
class ItemTest {
    @Test
    void shouldHaveWeight() {
        assertEquals(ONE_POUND,
                new Item(null, "Bob", "Yer Unkel", EQUIPMENT, ONE_POUND,
                        ONE_CUBIC_FOOT, "").weight());
    }

    @Test
    void shouldHaveVolume() {
        assertEquals(ONE_CUBIC_FOOT,
                new Item(null, "Bob", "Yer Unkel", EQUIPMENT, WEIGHTLESS,
                        ONE_CUBIC_FOOT, "").volume());
    }
}
