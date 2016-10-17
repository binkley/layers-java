package hm.binkley.layers;

import org.junit.jupiter.api.Test;

import static hm.binkley.layers.FullnessFunction.max;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LayerSetTest {
    private LayerSet set = new LayerSet(max(2));

    @Test
    void shouldComplainWhenOverfull() {
        set.add(new ScratchLayer(null));
        set.add(new ScratchLayer(null));

        assertThrows(IllegalStateException.class,
                () -> set.add(new ScratchLayer(null)));
    }

    @Test
    void shouldDisplayBriefly() {
        final ScratchLayer layer = new ScratchLayer(null);
        layer.details().put("Description", "Long, boring description");
        set.add(layer);

        assertTrue(set.toString().contains(layer.name()));
    }

    @Test
    void shouldNotDisplayVerbosely() {
        final ScratchLayer layer = new ScratchLayer(null);
        layer.details().put("Description", "Long, boring description");
        set.add(layer);

        assertFalse(set.toString()
                .contains(layer.details().get("Description").toString()));
    }
}
