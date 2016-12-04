package hm.binkley.layers.rules;

import hm.binkley.layers.LayersTestSupport;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.rules.Rule.mostRecent;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MostRecentRuleTest
        extends LayersTestSupport<ScratchLayer> {
    MostRecentRuleTest() {
        super(ScratchLayer::new);
    }

    @Test
    void shouldUseDefault() {
        final String defaultValue = "defaulted";
        firstLayer().
                put("A", key -> mostRecent(defaultValue)).
                saveAndNext(ScratchLayer::new);

        assertEquals(defaultValue, layers().get("A"));
    }

    @Test
    void shouldUseMostRecent() {
        final Object mostRecent = 3;
        firstLayer().
                put("A", key -> mostRecent("defaulted")).
                saveAndNext(ScratchLayer::new).
                put("A", mostRecent).
                saveAndNext(ScratchLayer::new);

        assertEquals(mostRecent, layers().get("A"));
    }
}
