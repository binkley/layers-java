package hm.binkley.layers;

import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.rules.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.values.Value.mostRecent;
import static hm.binkley.layers.values.Value.ofRule;
import static hm.binkley.layers.values.Value.ofValue;
import static hm.binkley.layers.values.Value.sumAll;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
                put("FOO", mostRecent("FOO", "BAR")).
                saveAndNext(ScratchLayer::new);

        assertTrue(layers.containsKey("FOO"));
    }

    @Test
    void shouldHaveKeys() {
        firstLayer.
                put("FOO", sumAll("FOO", 3)).
                saveAndNext(ScratchLayer::new);

        assertEquals(singleton("FOO"), layers.keys());
    }

    @Test
    void shouldHaveView() {
        firstLayer.
                put("BOB", mostRecent("BOB", 17)).
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
                put("BOB", mostRecent("BOB", 17)).
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
                put("BOB", mostRecent("BOB", 32));

        assertTrue(firstLayer.whatIfWith().containsKey("BOB"));
    }

    @Test
    void shouldHaveWhatIfWithoutLayer() {
        firstLayer.
                put("BOB", sumAll("BOB", 31)).
                saveAndNext(ScratchLayer::new);

        assertFalse(firstLayer.whatIfWithout().containsKey("BOB"));
    }

    @Test
    void shouldProjectToMap() {
        firstLayer.
                put("FOO", ofRule(Rule.sumAll("FOO"))).
                saveAndNext(ScratchLayer::new).
                put("FOO", ofValue(3)).
                saveAndNext(ScratchLayer::new);

        assertEquals(singletonMap("FOO", 3), layers.toMap());
    }

    @Test
    void shouldHaveSaneToString() {
        firstLayer.saveAndNext(ScratchLayer::new);

        assertTrue(layers.toString().contains("Scratch"));
    }

    @Test
    void shouldComplainIfKeyNull() {
        assertThrows(NullPointerException.class, () -> layers.get(null));
    }

    @Test
    void shouldComplainIfKeyMissing() {
        assertThrows(NoSuchElementException.class,
                () -> layers.get("No such key"));
    }

    private static final class EgLayer
            extends Layer {
        private EgLayer(final Surface layers) {
            super(layers, "Eg");
        }
    }
}
