package hm.binkley.layers.values;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import hm.binkley.layers.rules.Rule;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.values.Value.doubling;
import static hm.binkley.layers.values.Value.floor;
import static hm.binkley.layers.values.Value.mostRecent;
import static hm.binkley.layers.values.Value.ofBoth;
import static hm.binkley.layers.values.Value.ofRule;
import static hm.binkley.layers.values.Value.ofValue;
import static hm.binkley.layers.values.Value.sumAll;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValueTest {
    @Test
    void shouldHaveValueForOfValue() {
        assertEquals((Integer) 3, ofValue(3).value().get());
    }

    @Test
    void shouldHaveRuleForOfRule() {
        final Rule<Integer> rule = Rule.doubling(null);

        assertEquals(rule, ofRule(rule).rule().get());
    }

    @Test
    void shouldHaveValueForOfBoth() {
        assertEquals((Integer) 3,
                ofBoth(3, Rule.doubling(null)).value().get());
    }

    @Test
    void shouldHaveRuleForOfBoth() {
        final Rule<Integer> rule = Rule.doubling(null);

        assertEquals(rule, ofBoth(3, rule).rule().get());
    }

    @Test
    void shouldYellWhenApplyingValueOnly() {
        final Layers[] layersHolder = new Layers[1];
        final Layer layer = firstLayer(ScratchLayer::new,
                layers -> layersHolder[0] = layers);

        assertThrows(NullPointerException.class,
                () -> ofValue(3).apply(layersHolder[0], layer));
    }

    @Test
    void shouldEqualsAllInstancesForValueOnly() {
        assertEquals(ofValue(3), ofValue(3));
    }

    @Test
    void shouldEqualsAllInstancesForRuleOnly() {
        assertEquals(ofRule(Rule.doubling("BOB")),
                ofRule(Rule.doubling("BOB")));
    }

    @Test
    void shouldEqualsAllInstancesForBoth() {
        assertEquals(ofBoth(3, Rule.doubling("BOB")),
                ofBoth(3, Rule.doubling("BOB")));
    }

    @Test
    void shouldHashCodeSameAllInstancesForValueOnly() {
        assertEquals(ofValue(3).hashCode(), ofValue(3).hashCode());
    }

    @Test
    void shouldHashCodeSameAllInstancesForRuleOnly() {
        assertEquals(ofRule(Rule.doubling("BOB")).hashCode(),
                ofRule(Rule.doubling("BOB")).hashCode());
    }

    @Test
    void shouldHashCodeSameAllInstancesForBoth() {
        assertEquals(ofBoth(3, Rule.doubling("BOB")).hashCode(),
                ofBoth(3, Rule.doubling("BOB")).hashCode());
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
        final Rule<Integer> rule = Rule.doubling(null);

        assertTrue(ofRule(rule).toString().contains(rule.toString()));
    }

    /**
     * @todo This is goofy!  Jacoco cannot exclude methods (only classes), and
     * #toString is a big chunk of code in a small class
     */
    @Test
    void shouldHaveToStringForBoth() {
        final Rule<Integer> rule = Rule.doubling(null);
        final String valueString = Objects.toString(3);
        final String ruleString = rule.toString();

        final Value<Integer> both = ofBoth(3, rule);

        assertAll(() -> assertTrue(both.toString().contains(valueString)),
                () -> assertTrue(both.toString().contains(ruleString)));
    }

    @Test
    void shouldHaveToStringForMostRecent() {
        final String display = mostRecent("A", "xxx").toString();

        assertTrue(display.contains("Most recent"));
    }

    @Test
    void shouldHaveToStringForSumAll() {
        final String display = sumAll("A").toString();

        assertTrue(display.contains("Sum all"));
    }

    @Test
    void shouldHaveToStringForDoubling() {
        final String display = doubling("A").toString();

        assertTrue(display.contains("Doubling"));
    }

    @Test
    void shouldHaveToStringForFloor() {
        final String display = floor("A", 212).toString();

        assertAll(() -> assertTrue(display.contains("Floor")),
                () -> assertTrue(display.contains("212")));
    }
}
