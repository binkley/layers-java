package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuleTest {
    @Test
    void shouldEqualWhenRuleAndKeySame() {
        assertEquals(new DummyRule("Rule A", "Key #1"),
                new DummyRule("Rule B", "Key #1"));
    }

    @Test
    void shouldHashByClassAndKey() {
        final Set<Integer> rules = new HashSet<>();
        rules.add(new DummyRule("Rule A", "Key #1").hashCode());
        rules.add(new DummyRule("Rule B", "Key #2").hashCode());
        rules.add(new DummyRule2("Rule C", "Key #1").hashCode());

        assertEquals(3, rules.size());
    }

    private static final class DummyRule
            extends KeyRule<Integer, Integer> {
        private DummyRule(final String name, final Object key) {
            super(name, key);
        }

        @Override
        public Integer apply(final Layers layers, final Layer layer,
                final Integer value) {
            return value;
        }
    }

    private static final class DummyRule2
            extends KeyRule<Integer, Integer> {
        private DummyRule2(final String name, final Object key) {
            super(name, key);
        }

        @Override
        public Integer apply(final Layers layers, final Layer layer,
                final Integer value) {
            return value;
        }
    }
}
