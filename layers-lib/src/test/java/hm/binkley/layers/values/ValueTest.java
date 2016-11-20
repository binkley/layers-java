package hm.binkley.layers.values;

import hm.binkley.layers.Bug;
import hm.binkley.layers.ScratchLayer;
import hm.binkley.layers.rules.Rule;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Objects;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.rules.Rule.doubling;
import static hm.binkley.layers.values.Value.ofRule;
import static hm.binkley.layers.values.Value.ofValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.expectThrows;

class ValueTest {
    @Test
    void shouldHaveValueForOfValue() {
        assertEquals((Integer) 3, ofValue(3).value().get());
    }

    @Test
    void shouldHaveRuleForOfRule() {
        final Rule<Integer, Integer> rule = doubling();

        assertEquals(rule, ofRule(rule).rule().get());
    }

    @Test
    void shouldYellWhenApplyingValueWithoutRule() {
        final String key = "Complicated key";

        final Exception e = expectThrows(NoSuchElementException.class,
                () -> firstLayer(ScratchLayer::new, layers -> {}).
                        put(key, ofValue(3)).
                        saveAndNext(ScratchLayer::new));
        assertTrue(e.getMessage().contains(key));
    }

    @Test
    void shouldYellBugWhenApplyingValueOnly() {
        assertThrows(Bug.class, () -> Value.ofValue("a").apply(null));
    }

    @Test
    void shouldEqualsAllInstancesForValueOnly() {
        assertEquals(ofValue(3), ofValue(3));
    }

    @Test
    void shouldHashCodeSameAllInstancesForValueOnly() {
        assertEquals(ofValue(3).hashCode(), ofValue(3).hashCode());
    }

    @Test
    void shouldHashCodeSameAllInstancesForRuleOnly() {
        assertEquals(ofRule(doubling()).hashCode(),
                ofRule(doubling()).hashCode());
    }

    /**
     * @todo This is goofy!  Jacoco cannot exclude methods (only classes), and
     * #toString is a big chunk of code in a small class
     */
    @Test
    void shouldHaveToStringForValueOnly() {
        assertTrue(ofValue(3).toString().contains(Objects.toString(3)));
    }

    /**
     * @todo This is goofy!  Jacoco cannot exclude methods (only classes), and
     * #toString is a big chunk of code in a small class
     */
    @Test
    void shouldHaveToStringForRuleOnly() {
        final Rule<Integer, Integer> rule = doubling();

        assertTrue(ofRule(rule).toString().contains(rule.toString()));
    }
}
