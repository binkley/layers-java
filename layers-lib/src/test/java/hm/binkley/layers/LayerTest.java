package hm.binkley.layers;

import hm.binkley.layers.rules.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.values.Value.ofValue;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LayerTest {
    private Layer layer;

    @BeforeEach
    void setUpLayer() {
        layer = new Layer(null, "Eg");
        layer.put("A", ofValue("P"));
        layer.put("B", ofValue(4));
    }

    @Test
    void shouldConvertToMap() {
        final Map<Object, Object> expected = new LinkedHashMap<>();
        expected.put("A", "P");
        expected.put("B", 4);

        assertEquals(expected, layer.toMap());
    }

    @Test
    void shouldIterateInSameOrderWithToMap() {
        final Map<Object, Object> expected = new LinkedHashMap<>();
        expected.put("A", "P");
        expected.put("B", 4);

        assertEquals(expected.entrySet().stream().collect(toList()),
                layer.toMap().entrySet().stream().collect(toList()));
    }

    @Test
    void shouldBlendLayer() {
        final Layer other = new ScratchLayer(null);
        other.put("C", ofValue(42));
        layer.blend(other);

        assertEquals(42, layer.get("C").value().get());
    }

    @Test
    void shouldCryIfBlendingDuplicateKey() {
        final Layer other = new ScratchLayer(null);
        other.put("A", ofValue(42));

        assertThrows(IllegalStateException.class, () -> layer.blend(other));
    }

    @Test
    void shouldCryIfBlendingDuplicateKeyTheOtherWay() {
        final Layer other = new ScratchLayer(null);
        other.put("A", ofValue(42));

        assertThrows(IllegalStateException.class,
                () -> layer.blend(layers -> other));
    }

    @Test
    void shouldPrintClassKeysNicelyForLayer() {
        layer.put(String.class, ofValue("FOO"));

        final String display = layer.toString();
        assertAll(() -> assertTrue(display.contains("String")),
                () -> assertFalse(display.contains("java.lang.String")));
    }

    @Test
    void shouldPrintClassKeysNicelyForDetails() {
        layer.details().put(String.class, ofValue("FOO"));

        final String display = layer.toString();
        assertAll(() -> assertTrue(display.contains("String")),
                () -> assertFalse(display.contains("java.lang.String")));
    }

    @Test
    void shouldDelegateNameToLayerFromLayerMap() {
        assertEquals(layer.name(), layer.view().name());
    }

    @Test
    void shouldDelegateKeysToLayerFromLayerMap() {
        assertEquals(layer.keys(), layer.view().keys());
    }

    @Test
    void shouldDelegateGetToLayerFromLayerMap() {
        assertEquals(layer.get("A"), layer.view().get("A"));
    }

    @Test
    void shouldDelegateStreamToLayerFromLayerMap() {
        assertEquals(layer.stream().collect(toList()),
                layer.view().stream().collect(toList()));
    }

    @Test
    void shouldDelegateToMapToLayerFromLayerView() {
        assertEquals(layer.toMap(), layer.view().toMap());
    }

    @Test
    void shouldDelegateIsEmptyToLayerFromLayerView() {
        assertEquals(layer.isEmpty(), layer.view().isEmpty());
    }

    @Test
    void shouldDelegateSizeToLayerFromLayerView() {
        assertEquals(layer.size(), layer.view().size());
    }

    @Test
    void shouldDelegateContainsKeyToLayerFromLayerView() {
        assertEquals(layer.containsKey("A"), layer.view().containsKey("A"));
    }

    @Test
    void shouldDelegateDetailsToLayerFromLayerView() {
        layer.details().put("apple", "cart");

        assertEquals(layer.details(), layer.view().details());
    }

    @Test
    void shouldDelegateWhatIfWithoutToLayers() {
        final Layers[] layersHolder = new Layers[1];
        final Layer layer = firstLayer(ScratchLayer::new,
                layers -> layersHolder[0] = layers).
                put("A", 0, Rule::sumAll).
                saveAndNext(ScratchLayer::new).
                put("A", 3, Rule::mostRecent);
        layer.saveAndNext(ScratchLayer::new);

        assertEquals(layersHolder[0].whatIfWithout(layer).get("A"),
                layer.whatIfWithout().get("A"));
    }

    @Test
    void shouldDelegateWhatIfWithToLayers() {
        final Layers[] layersHolder = new Layers[1];
        final Layer layer = firstLayer(ScratchLayer::new,
                layers -> layersHolder[0] = layers);
        layer.put("A", 3, Rule::mostRecent);
        final Layers layers = layersHolder[0];

        assertEquals(layers.whatIfWith(layer).get("A"),
                layer.whatIfWith().get("A"));
    }
}
