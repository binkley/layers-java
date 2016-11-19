package hm.binkley.layers.values;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.Layers.RuleSurface;
import hm.binkley.layers.ScratchLayer;
import hm.binkley.layers.rules.Rule;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.rules.Rule.doubling;
import static hm.binkley.layers.values.Value.ofBoth;
import static hm.binkley.layers.values.Value.ofRule;
import static hm.binkley.layers.values.Value.ofValue;
import static hm.binkley.layers.values.ValueTest.IdentityRule.identityRule;
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
        final Rule<Integer, Integer> rule = doubling();

        assertEquals(rule, ofRule(rule).rule().get());
    }

    @Test
    void shouldYellWhenApplyingValueOnly() {
        final Layers[] layersHolder = new Layers[1];
        final Layer layer = firstLayer(ScratchLayer::new,
                layers -> layersHolder[0] = layers);

        assertThrows(NullPointerException.class, () -> ofValue(3).
                apply(layersHolder[0].new RuleSurface<>(layer, null)));
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

    @Test
    void shouldHashCodeSameAllInstancesForBoth() {
        assertEquals(ofBoth(3, identityRule(3)).hashCode(),
                ofBoth(3, identityRule(3)).hashCode());
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

    /**
     * @todo This is goofy!  Jacoco cannot exclude methods (only classes), and
     * #toString is a big chunk of code in a small class
     */
    @Test
    void shouldHaveToStringForBoth() {
        final Rule<Integer, Integer> rule = doubling();
        final String valueString = Objects.toString(3);
        final String ruleString = rule.toString();

        final Value<Integer, Integer> both = ofBoth(3, rule);

        assertAll(() -> assertTrue(both.toString().contains(valueString)),
                () -> assertTrue(both.toString().contains(ruleString)));
    }

    static final class IdentityRule<T>
            extends Rule<T, T> {
        private final T value;

        static <T> IdentityRule<T> identityRule(final T value) {
            return new IdentityRule<>(value);
        }

        private IdentityRule(final T value) {
            super("Identity");
            this.value = value;
        }

        @Override
        public T apply(final RuleSurface<T> layers) {
            return value;
        }
    }
}
