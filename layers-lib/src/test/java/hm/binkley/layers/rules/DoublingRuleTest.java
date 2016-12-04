package hm.binkley.layers.rules;

import hm.binkley.layers.LayersTestSupport;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.rules.Rule.doubling;
import static hm.binkley.layers.rules.Rule.sumAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DoublingRuleTest
        extends LayersTestSupport<ScratchLayer> {
    public DoublingRuleTest() {
        super(ScratchLayer::new);
    }

    @Test
    void shouldUseDoubling() {
        firstLayer().
                put("A", sumAll()).
                saveAndNext(ScratchLayer::new).
                put("A", 1).
                saveAndNext(ScratchLayer::new).
                put("A", 2).
                saveAndNext(ScratchLayer::new).
                put("A", doubling()).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 6, layers().get("A"));
    }
}
