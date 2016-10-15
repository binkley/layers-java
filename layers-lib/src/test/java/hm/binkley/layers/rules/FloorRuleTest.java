package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.Value.ofBoth;
import static hm.binkley.layers.Value.ofRule;
import static hm.binkley.layers.Value.ofValue;
import static hm.binkley.layers.rules.Rule.floor;
import static hm.binkley.layers.rules.Rule.sumAll;
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
                put("A", ofBoth(12, floor("A"))).
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
                put("A", ofBoth(3, floor("A"))).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 12, layers.get("A"));
    }
}
