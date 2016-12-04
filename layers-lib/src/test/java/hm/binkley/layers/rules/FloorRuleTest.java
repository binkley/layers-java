package hm.binkley.layers.rules;

import hm.binkley.layers.LayersTestSupport;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.rules.Rule.floor;
import static hm.binkley.layers.rules.Rule.sumAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FloorRuleTest
        extends LayersTestSupport<ScratchLayer> {
    FloorRuleTest() {
        super(ScratchLayer::new);
    }

    @Test
    void shouldUseFloor() {
        firstLayer().
                put("A", key -> sumAll()).
                saveAndNext(ScratchLayer::new).
                put("A", 3).
                saveAndNext(ScratchLayer::new).
                put("A", key -> floor(12)).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 12, layers().get("A"));
    }

    @Test
    void shouldIgnoreFloor() {
        firstLayer().
                put("A", key -> sumAll()).
                saveAndNext(ScratchLayer::new).
                put("A", 12).
                saveAndNext(ScratchLayer::new).
                put("A", key -> floor(0)).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 12, layers().get("A"));
    }
}
