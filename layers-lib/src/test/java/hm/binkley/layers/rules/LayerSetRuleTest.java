package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static hm.binkley.layers.LayerSet.empty;
import static hm.binkley.layers.LayerSet.singleton;
import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.Value.ofBoth;
import static hm.binkley.layers.Value.ofValue;
import static hm.binkley.layers.rules.Rule.layerSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LayerSetRuleTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(ScratchLayer::new,
                layers -> this.layers = layers);
    }

    @Test
    void shouldUseLayerSet() {
        firstLayer.
                put("A", ofBoth(empty(), layerSet("Test", "A", 1))).
                saveAndNext(ScratchLayer::new).
                put("A", ofValue(singleton(firstLayer))).
                saveAndNext(ScratchLayer::new);

        assertEquals(Collections.singleton(firstLayer), layers.get("A"));
    }

    @Test
    void shouldCapOutLayerSet() {
        final ScratchLayer secondLayer = firstLayer.
                put("A", ofBoth(singleton(firstLayer),
                        layerSet("Test", "A", 1))).
                saveAndNext(ScratchLayer::new);
        secondLayer.
                put("A", ofValue(singleton(secondLayer)));

        assertThrows(IllegalStateException.class,
                () -> secondLayer.saveAndNext(ScratchLayer::new));
    }
}
