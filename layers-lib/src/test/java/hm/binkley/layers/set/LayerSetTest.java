package hm.binkley.layers.set;

import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

import static hm.binkley.layers.set.FullnessFunction.max;
import static hm.binkley.layers.set.FullnessFunction.unlimited;
import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LayerSetTest {
    private LayerSet<EgLayer> set;

    @BeforeEach
    void setUpSet() {
        set = new LayerSet<>(max(2));
    }

    @Test
    void shouldComplainWhenOverfull() {
        set.add(new EgLayer(null, 1));
        set.add(new EgLayer(null, 2));

        assertThrows(IllegalStateException.class,
                () -> set.add(new EgLayer(null, 3)));
    }

    @Test
    void shouldDisplayBriefly() {
        final EgLayer layer = new EgLayer(null, 1);
        layer.details().put("Description", "Long, boring description");
        set.add(layer);

        assertTrue(set.toString().contains(layer.name()));
    }

    @Test
    void shouldNotDisplayVerbosely() {
        final EgLayer layer = new EgLayer(null, 1);
        layer.details().put("Description", "Long, boring description");
        set.add(layer);

        assertFalse(set.toString()
                .contains(layer.details().get("Description").toString()));
    }

    @Test
    void shouldTypeCorrectly() {
        set.add(new EgLayer(null, 1));

        assertEquals(1, set.stream().
                mapToInt(EgLayer::foo).
                sum());
    }

    @Test
    void shouldPreserveOrder() {
        final LayerSet<EgLayer> set = new LayerSet<>(unlimited());
        range(0, 10).
                mapToObj(i -> new EgLayer(null, i)).
                forEach(set::add);

        assertTrue(Arrays.equals(range(0, 10).toArray(), set.stream().
                map(EgLayer::foo).
                mapToInt(Integer::intValue).
                toArray()));
    }

    @Test
    void shouldSquashDuplicates() {
        set.add(new EgLayer(null, 1));
        set.add(new EgLayer(null, 1));

        assertEquals(1, set.size());
    }

    private static final class EgLayer
            extends ScratchLayer {
        private final int i;

        private EgLayer(final Surface layers, final int i) {
            super(layers);
            this.i = i;
        }

        private int foo() {
            return i;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o)
                return true;
            if (null == o || getClass() != o.getClass())
                return false;
            final EgLayer that = (EgLayer) o;
            return i == that.i;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i);
        }
    }
}
