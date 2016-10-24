package hm.binkley.layers.set;

import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.set.FullnessFunction.max;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LayerSetTest {
    private LayerSet<EgLayer> set = new LayerSet<>(max(2));

    @Test
    void shouldComplainWhenOverfull() {
        set.add(new EgLayer(null));
        set.add(new EgLayer(null));

        assertThrows(IllegalStateException.class,
                () -> set.add(new EgLayer(null)));
    }

    @Test
    void shouldDisplayBriefly() {
        final EgLayer layer = new EgLayer(null);
        layer.details().put("Description", "Long, boring description");
        set.add(layer);

        assertTrue(set.toString().contains(layer.name()));
    }

    @Test
    void shouldNotDisplayVerbosely() {
        final EgLayer layer = new EgLayer(null);
        layer.details().put("Description", "Long, boring description");
        set.add(layer);

        assertFalse(set.toString()
                .contains(layer.details().get("Description").toString()));
    }

    @Test
    void shouldTypeCorrectly() {
        set.add(new EgLayer(null));

        assertEquals(1, set.stream().
                mapToInt(EgLayer::foo).
                sum());
    }

    private static final class EgLayer
            extends ScratchLayer {
        private EgLayer(final Layers.Surface layers) {
            super(layers);
        }

        private int foo() {
            return 1;
        }
    }
}
