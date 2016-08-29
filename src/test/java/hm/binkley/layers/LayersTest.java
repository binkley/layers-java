package hm.binkley.layers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static hm.binkley.layers.Layers.newLayers;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@code LayersTest} tests {@link Layers}.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 */
public class LayersTest {
    private Layers layers;
    private AtomicReference<Layer> firstLayer;

    @BeforeEach
    void setUp() {
        firstLayer = new AtomicReference<>();
        layers = newLayers(BlankLayer::new, firstLayer::set);
    }

    @Test
    void shouldStartEmpty() {
        assertAll(() -> assertNotNull(layers),
                () -> assertTrue(layers.isEmpty()),
                () -> assertNotNull(firstLayer.get()));
    }
}
