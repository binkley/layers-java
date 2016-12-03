package hm.binkley.layers.dnd.item;

import org.junit.jupiter.api.Test;

import static hm.binkley.layers.dnd.item.Volume.asFraction;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VolumeTest {
    @Test
    void shouldDisplayNicely() {
        assertEquals("11/10 cuft", asFraction(11, 10).toString());
    }

    @Test
    void shouldAdd() {
        assertEquals(asFraction(23, 10),
                asFraction(17, 10).add(asFraction(3, 5)));
    }
}
