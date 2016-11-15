package hm.binkley.layers;

import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.rules.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.rules.Rule.sumAll;
import static hm.binkley.layers.values.Value.ofValue;
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
                put("FOO", "BAR", (key) -> Rule.mostRecent()).
                saveAndNext(ScratchLayer::new);

        assertTrue(layers.containsKey("FOO"));
    }

    @Test
    void shouldHaveKeys() {
        firstLayer.
                put("FOO", 3, (key) -> Rule.sumAll()).
                saveAndNext(ScratchLayer::new);

        assertEquals(singleton("FOO"), layers.keys());
    }

    @Test
    void shouldHaveView() {
        firstLayer.
                put("BOB", 17, (key) -> Rule.mostRecent()).
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
                put("BOB", 17, (key) -> Rule.mostRecent()).
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
                put("BOB", 32, (key) -> Rule.mostRecent());

        assertTrue(layers.whatIfWith(firstLayer).containsKey("BOB"));
    }

    @Test
    void shouldHaveWhatIfWithoutLayer() {
        firstLayer.
                put("BOB", 31, (key) -> Rule.sumAll()).
                saveAndNext(ScratchLayer::new);

        assertFalse(layers.whatIfWithout(firstLayer).containsKey("BOB"));
    }

    @Test
    void shouldProjectToMap() {
        firstLayer.
                put("FOO", sumAll()).
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
        private EgLayer(final LayerSurface layers) {
            super(layers, "Eg");
        }
    }
}
