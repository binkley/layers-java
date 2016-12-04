package hm.binkley.layers.rules;

import hm.binkley.layers.LayersTestSupport;
import hm.binkley.layers.ScratchLayer;
import hm.binkley.layers.rules.BaseRule.BaseRulesLayer;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Numbers.FIRST;
import static hm.binkley.layers.Numbers.numbers;
import static hm.binkley.layers.Texts.NAME;
import static hm.binkley.layers.Texts.texts;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseRuleTest
        extends LayersTestSupport<BaseRulesLayer> {
    public BaseRuleTest() {
        super(BaseRule::baseRules);
    }

    @Test
    void shouldHaveMostRecentTexts() {
        firstLayer().
                saveAndNext(texts("Bob")).
                saveAndNext(texts("Nancy")).
                saveAndNext(ScratchLayer::new);
        assertEquals("Nancy", layers().get(NAME));
    }

    @Test
    void shouldHaveSumAllNumbers() {
        firstLayer().
                saveAndNext(numbers(1, 2)).
                saveAndNext(numbers(2, 0)).
                saveAndNext(ScratchLayer::new);
        assertEquals((Integer) 3, layers().get(FIRST));
    }
}
