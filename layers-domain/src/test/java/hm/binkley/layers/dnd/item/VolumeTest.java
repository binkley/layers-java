package hm.binkley.layers.dnd.item;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static hm.binkley.layers.dnd.item.Volume.inCuft;
import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VolumeTest {
    @Test
    void shouldBeFaithful() {
        assertEquals(1.1f, inCuft(1.1f).feet());
    }

    @Test
    void shouldDisplayNicely() {
        assertEquals("1.1 cuft", inCuft(1.1f).toString());
    }

    @Test
    void shouldSort() {
        final List<Volume> actual = new ArrayList<>(2);
        actual.add(inCuft(1));
        actual.add(inCuft(0));
        sort(actual);

        assertEquals(asList(inCuft(0), inCuft(1)), actual);
    }
}
