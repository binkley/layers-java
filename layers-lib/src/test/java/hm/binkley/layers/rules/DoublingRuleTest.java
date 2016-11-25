package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.rules.Rule.doubling;
import static hm.binkley.layers.rules.Rule.sumAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DoublingRuleTest {
    private Layers layers;
    private Layer<?> firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(ScratchLayer::new,
                layers -> this.layers = layers);
    }

    @Test
    void shouldUseDoubling() {
        firstLayer.
                put("A", sumAll()).
                saveAndNext(ScratchLayer::new).
                put("A", 1).
                saveAndNext(ScratchLayer::new).
                put("A", 2).
                saveAndNext(ScratchLayer::new).
                put("A", doubling()).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 6, layers.get("A"));
    }
}
