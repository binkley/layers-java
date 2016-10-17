package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static hm.binkley.layers.FullnessFunction.max;
import static hm.binkley.layers.FullnessFunction.named;
import static hm.binkley.layers.LayerSet.empty;
import static hm.binkley.layers.LayerSet.singleton;
import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.rules.Rule.layerSet;
import static hm.binkley.layers.values.Value.ofBoth;
import static hm.binkley.layers.values.Value.ofValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                put("A", ofBoth(empty(), layerSet("A", max(1)))).
                saveAndNext(ScratchLayer::new).
                put("A", ofValue(firstLayer)).
                saveAndNext(ScratchLayer::new);

        assertEquals(Collections.singleton(firstLayer), layers.get("A"));
    }

    @Test
    void shouldCapOutLayerSet() {
        final ScratchLayer secondLayer = firstLayer.
                put("A", ofBoth(firstLayer, layerSet("A", max(1)))).
                saveAndNext(ScratchLayer::new);
        secondLayer.
                put("A", ofValue(singleton(secondLayer)));

        assertThrows(IllegalStateException.class,
                () -> secondLayer.saveAndNext(ScratchLayer::new));
    }

    @Test
    void shouldDisplayRuleNameWhenAvailable() {
        firstLayer.
                put("A", ofBoth(firstLayer, layerSet("A",
                        named((set, layer) -> 1 == set.size(), "Bob!")))).
                saveAndNext(ScratchLayer::new);

        assertTrue(firstLayer.toString().contains("Bob!"));
    }
}
