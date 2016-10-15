package hm.binkley.layers;

import hm.binkley.layers.rules.BaseRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.Numbers.FIRST;
import static hm.binkley.layers.Numbers.numbers;
import static hm.binkley.layers.rules.Rule.mostRecent;
import static hm.binkley.layers.rules.Rule.sumAll;
import static hm.binkley.layers.Texts.NAME;
import static hm.binkley.layers.Texts.texts;
import static hm.binkley.layers.values.Value.ofBoth;
import static hm.binkley.layers.values.Value.ofRule;
import static hm.binkley.layers.values.Value.ofValue;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LayersTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(ScratchLayer::new,
                layers -> this.layers = layers);
    }

    @Test
    void shouldStartEmpty() {
        assertTrue(layers.isEmpty());
    }

    @Test
    void shouldStayEmptyAfterAddingEmptyLayer() {
        firstLayer.
                saveAndNext(ScratchLayer::new);

        assertTrue(layers.isEmpty());
    }

    @Test
    void shouldStartAtSizeZero() {
        assertEquals(0, layers.size());
    }

    @Test
    void shouldStayAtSizeZeroAfterAddingEmptyLayer() {
        firstLayer.
                saveAndNext(ScratchLayer::new);

        assertEquals(0, layers.size());
    }

    @Test
    void shouldNotContainKey() {
        assertFalse(layers.containsKey("FOO"));
    }

    @Test
    void shouldContainKey() {
        firstLayer.
                put("FOO", ofBoth("BAR", mostRecent("FOO"))).
                saveAndNext(ScratchLayer::new);

        assertTrue(layers.containsKey("FOO"));
    }

    @Test
    void shouldHaveKeys() {
        firstLayer.
                put("FOO", ofBoth(3, sumAll("FOO"))).
                saveAndNext(ScratchLayer::new);

        assertEquals(singleton("FOO"), layers.keys());
    }

    @Test
    void shouldHaveView() {
        firstLayer.
                put("BOB", ofBoth(17, mostRecent("BOB"))).
                saveAndNext(ScratchLayer::new);

        final List<Map<Object, Object>> view = layers.
                view().
                map(layer -> layer.stream().
                        collect(toMap(Entry::getKey, Entry::getValue))).
                collect(toList());

        assertEquals(singletonList(singletonMap("BOB", 17)), view);
    }

    @Test
    void shouldHaveFilteredView() {
        firstLayer.
                put("BOB", ofBoth(17, mostRecent("BOB"))).
                saveAndNext(EgLayer::new).
                put("BOB", ofValue(18)).
                saveAndNext(ScratchLayer::new);

        final List<Map<Object, Object>> view = layers.
                view(layer -> layer instanceof EgLayer).
                map(layer -> layer.stream().
                        collect(toMap(Entry::getKey, Entry::getValue))).
                collect(toList());

        assertEquals(singletonList(singletonMap("BOB", 18)), view);
    }

    @Test
    void shouldHaveWhatIfWithLayer() {
        firstLayer.
                put("BOB", ofBoth(32, mostRecent("BOB")));

        assertTrue(firstLayer.whatIfWith().containsKey("BOB"));
    }

    @Test
    void shouldHaveWhatIfWithoutLayer() {
        firstLayer.
                put("BOB", ofBoth(31, sumAll("BOB"))).
                saveAndNext(ScratchLayer::new);

        assertFalse(firstLayer.whatIfWithout().containsKey("BOB"));
    }

    @Test
    void shouldProjectToMap() {
        firstLayer.
                put("FOO", ofRule(sumAll("FOO"))).
                saveAndNext(ScratchLayer::new).
                put("FOO", ofValue(3)).
                saveAndNext(ScratchLayer::new);

        assertEquals(singletonMap("FOO", 3), layers.toMap());
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

    /** @todo Silly jacoco does not support excluding methods */
    @Test
    void shouldHaveSaneToString() {
        firstLayer.saveAndNext(ScratchLayer::new);

        assertTrue(layers.toString().contains("Scratch"));
    }

    private static final class EgLayer
            extends Layer {
        private EgLayer(final Layers.Surface layers) {
            super(layers, "Eg");
        }
    }
}
