package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.rules.Rule.sumAll;
import static hm.binkley.layers.values.Value.floor;
import static hm.binkley.layers.values.Value.ofRule;
import static hm.binkley.layers.values.Value.ofValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FloorRuleTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(ScratchLayer::new,
                layers -> this.layers = layers);
    }

    @Test
    void shouldUseFloor() {
        firstLayer.
                put("A", ofRule(sumAll("A"))).
                saveAndNext(ScratchLayer::new).
                put("A", ofValue(3)).
                saveAndNext(ScratchLayer::new).
                put("A", floor("A", 12)).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 12, layers.get("A"));
    }

    @Test
    void shouldIgnoreFloor() {
        firstLayer.
                put("A", ofRule(sumAll("A"))).
                saveAndNext(ScratchLayer::new).
                put("A", ofValue(12)).
                saveAndNext(ScratchLayer::new).
                put("A", floor("A", 3)).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 12, layers.get("A"));
    }
}
