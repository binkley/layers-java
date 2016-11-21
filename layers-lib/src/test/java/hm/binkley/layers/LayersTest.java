package hm.binkley.layers;

import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.Layers.RuleSurface;
import hm.binkley.layers.rules.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.rules.Rule.mostRecent;
import static hm.binkley.layers.rules.Rule.sumAll;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyMap;
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
                put("FOO", key -> mostRecent("")).
                saveAndNext(ScratchLayer::new);

        assertTrue(layers.containsKey("FOO"));
    }

    @Test
    void shouldHaveKeys() {
        firstLayer.
                put("FOO", key -> sumAll()).
                saveAndNext(ScratchLayer::new);

        assertEquals(singleton("FOO"), layers.keys());
    }

    @Test
    void shouldHaveView() {
        firstLayer.
                put("BOB", key -> mostRecent(17)).
                saveAndNext(ScratchLayer::new).
                put("BOB", 18).
                saveAndNext(ScratchLayer::new);

        final List<Map<Object, Object>> view = layers.
                view().
                map(layer -> layer.stream().
                        collect(toMap(Entry::getKey, Entry::getValue))).
                collect(toList());

        assertEquals(asList(emptyMap(), singletonMap("BOB", 18)), view);
    }

    @Test
    void shouldHaveFilteredView() {
        firstLayer.
                put("BOB", key -> mostRecent(17)).
                saveAndNext(EgLayer::new).
                put("BOB", 18).
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
                put("BOB", key -> mostRecent(32));

        assertTrue(layers.whatIfWith(firstLayer).containsKey("BOB"));
    }

    @Test
    void shouldHaveWhatIfWithoutLayer() {
        firstLayer.
                put("BOB", key -> sumAll()).
                saveAndNext(ScratchLayer::new);

        assertFalse(layers.whatIfWithout(firstLayer).containsKey("BOB"));
    }

    @Test
    void shouldProjectToMap() {
        firstLayer.
                put("FOO", sumAll()).
                saveAndNext(ScratchLayer::new).
                put("FOO", 3).
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

    @Test
    void shouldGetValueFromSurface() {
        final EgLayer egLayer = firstLayer.
                put("Other OK", mostRecent(true)).
                saveAndNext(ScratchLayer::new).
                put("OK", new Rule<EgLayer, Boolean, Boolean>("Fake OK") {
                    @Override
                    public Boolean apply(
                            final RuleSurface<EgLayer, Boolean, Boolean>
                                    layers) {
                        return layers.get("Other OK");
                    }
                }).
                saveAndNext(EgLayer::new);
        egLayer.saveAndNext(ScratchLayer::new);

        assertTrue(egLayer.isOk());
    }

    private static final class EgLayer
            extends Layer {
        private EgLayer(final LayerSurface layers) {
            super(layers, "Eg");
        }

        boolean isOk() {
            return layers.get("OK");
        }
    }
}
