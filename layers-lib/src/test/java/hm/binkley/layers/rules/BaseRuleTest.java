package hm.binkley.layers.rules;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.Numbers.FIRST;
import static hm.binkley.layers.Numbers.numbers;
import static hm.binkley.layers.Texts.NAME;
import static hm.binkley.layers.Texts.texts;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseRuleTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(ScratchLayer::new,
                layers -> this.layers = layers);
    }

    @Test
    void shouldHaveMostRecentTexts() {
        assertEquals("Nancy", firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(texts("Bob")).
                saveAndNext(texts("Nancy")).
                whatIfWith().
                get(NAME));
    }

    @Test
    void shouldHaveSumAllNumbers() {
        assertEquals((Integer) 3, firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(numbers(1, 2)).
                saveAndNext(numbers(2, 0)).
                whatIfWith().
                get(FIRST));
    }
}
