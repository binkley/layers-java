package hm.binkley.layers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static hm.binkley.layers.Value.ofValue;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void shouldDelegateDetailsToLayerFromLayerView() {
        layer.details().put("apple", "cart");

        assertEquals(layer.details(), layer.view().details());
    }
}
