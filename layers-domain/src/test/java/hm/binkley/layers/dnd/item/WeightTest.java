package hm.binkley.layers.dnd.item;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static hm.binkley.layers.dnd.item.Weight.inPounds;
import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WeightTest {
    @Test
    void shouldBeFaithful() {
        assertEquals(1.1f, inPounds(1.1f).pounds());
    }

    @Test
    void shouldDisplayNicely() {
        assertEquals("#1.1", inPounds(1.1f).toString());
    }

    @Test
    void shouldSort() {
        final List<Weight> actual = new ArrayList<>(2);
        actual.add(inPounds(1));
        actual.add(inPounds(0));
        sort(actual);

        assertEquals(asList(inPounds(0), inPounds(1)), actual);
    }
}
