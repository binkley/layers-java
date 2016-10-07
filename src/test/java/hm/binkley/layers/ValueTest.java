package hm.binkley.layers;

import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Rule.doubling;
import static hm.binkley.layers.Value.ofBoth;
import static hm.binkley.layers.Value.ofRule;
import static hm.binkley.layers.Value.ofValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ValueTest {
    @Test
    void shouldHaveValueForOfValue() {
        assertEquals((Integer) 3, ofValue(3).value().get());
    }

    @Test
    void shouldHaveValueForOfBoth() {
        assertEquals((Integer) 3, ofBoth(3, doubling(null)).value().get());
    }

    @Test
    void shouldHaveRuleForOfRule() {
        final Rule<Integer> rule = doubling(null);

        assertEquals(rule, ofRule(rule).rule().get());
    }

    @Test
    void shouldHaveRuleForOfBoth() {
        final Rule<Integer> rule = doubling(null);

        assertEquals(rule, ofBoth(3, rule).rule().get());
    }
}
