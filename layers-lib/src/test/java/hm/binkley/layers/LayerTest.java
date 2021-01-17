package hm.binkley.layers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LayerTest {
    private Layer<?> layer;

    @BeforeEach
    void setUpLayer() {
        layer = new Layer<>(null, "Eg").
                put("A", "P").
                put("B", 4);
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

        assertEquals(new ArrayList<>(expected.entrySet()),
                new ArrayList<>(layer.toMap().entrySet()));
    }

    @Test
    void shouldBlendLayer() {
        layer.blend(new ScratchLayer(null).
                put("C", 42));

        assertEquals((Integer) 42, layer.get("C"));
    }

    @Test
    void shouldCryIfBlendingDuplicateKey() {
        assertThrows(IllegalStateException.class,
                () -> layer.blend(new ScratchLayer(null).
                        put("A", 42)));
    }

    @Test
    void shouldCryIfBlendingDuplicateKeyTheOtherWay() {
        assertThrows(IllegalStateException.class, () -> layer.
                blend(layers -> new ScratchLayer(null).
                        put("A", 42)));
    }

    @Test
    void shouldPrintClassKeysNicelyForLayer() {
        layer.put(String.class, "FOO");

        final String display = layer.toString();
        assertAll(() -> assertTrue(display.contains("String")),
                () -> assertFalse(display.contains("java.lang.String")));
    }

    @Test
    void shouldPrintClassKeysNicelyForDetails() {
        final String display = layer.
                putDetail(String.class, "FOO").
                toString();
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
        assertEquals(layer.<String>get("A"), layer.view().get("A"));
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
        layer.putDetail("apple", "cart");

        assertEquals(layer.details(), layer.view().details());
    }
}
