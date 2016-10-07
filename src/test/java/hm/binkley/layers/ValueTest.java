package hm.binkley.layers;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static hm.binkley.layers.Rule.doubling;
import static hm.binkley.layers.Value.ofBoth;
import static hm.binkley.layers.Value.ofRule;
import static hm.binkley.layers.Value.ofValue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    /**
     * @todo This is goofy!  Jacoco cannot exclude methods (only classes), and
     * #toString is a big chunk of code in a small class
     */
    @Test
    void shouldHaveToString() {
        final Rule<Integer> rule = doubling(null);
        final String valueString = Objects.toString(3);
        final String ruleString = rule.toString();

        final Value<Integer> valueOnly = ofValue(3);
        final Value<Integer> ruleOnly = ofRule(rule);
        final Value<Integer> both = ofBoth(3, rule);

        assertAll(
                () -> assertTrue(valueOnly.toString().contains(valueString)),
                () -> assertTrue(ruleOnly.toString().contains(ruleString)),
                () -> assertAll(() -> assertTrue(
                        both.toString().contains(valueString)),
                        () -> assertTrue(
                                both.toString().contains(ruleString))));
    }
}
