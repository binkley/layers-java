package hm.binkley.layers.rules;

import hm.binkley.layers.LayersTestSupport;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.rules.Rule.sumAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SumAllRuleTest
        extends LayersTestSupport<ScratchLayer> {
    public SumAllRuleTest() {
        super(ScratchLayer::new);
    }

    @Test
    void shouldUseDefault() {
        firstLayer().
                put("A", key -> sumAll()).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 0, layers().get("A"));
    }

    @Test
    void shouldUseSum() {
        firstLayer().
                put("A", key -> sumAll()).
                saveAndNext(ScratchLayer::new).
                put("A", 1).
                saveAndNext(ScratchLayer::new).
                put("A", 2).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 3, layers().get("A"));
    }
}
