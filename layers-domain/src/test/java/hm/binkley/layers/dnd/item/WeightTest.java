package hm.binkley.layers.dnd.item;

import org.junit.jupiter.api.Test;

import static hm.binkley.layers.dnd.item.Weight.asFraction;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeightTest {
    @Test
    void shouldDisplayNicely() {
        assertEquals("1.1#", asFraction(11, 10).toString());
    }

    @Test
    void shouldAdd() {
        assertEquals(asFraction(23, 10),
                asFraction(17, 10).add(asFraction(3, 5)));
    }
}
