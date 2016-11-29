package hm.binkley.layers.set;

import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import hm.binkley.layers.rules.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.set.FullnessFunction.unlimited;
import static hm.binkley.layers.set.LayerSetCommand.add;
import static hm.binkley.layers.set.LayerSetCommand.remove;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LayerSetCommandTest {
    private Layers layers;
    private ScratchLayer firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(ScratchLayer::new,
                layers -> this.layers = layers).
                put("KEY", Rule.<ScratchLayer>layerSet(unlimited())).
                saveAndNext(ScratchLayer::new);
    }

    @Test
    void shouldRememberName() {
        assertEquals("Bob", add("Bob", firstLayer).name());
    }

    @Test
    void shouldGenerateName() {
        assertEquals("Add: Scratch", add(firstLayer).name());
    }

    @Test
    void shouldAdd() {
        firstLayer.
                put("KEY", add(firstLayer)).
                saveAndNext(ScratchLayer::new);

        assertEquals(singleton(firstLayer), layers.get("KEY"));
    }

    @Test
    void shouldRemove() {
        firstLayer.
                put("KEY", add(firstLayer)).
                saveAndNext(ScratchLayer::new).
                put("KEY", remove(firstLayer)).
                saveAndNext(ScratchLayer::new);

        assertEquals(emptySet(), layers.get("KEY"));
    }

    @Test
    void shouldComplainWhenRemovingAndEmpty() {
        assertThrows(NoSuchElementException.class, () -> firstLayer.
                put("KEY", remove(firstLayer)).
                saveAndNext(ScratchLayer::new));
    }
}
