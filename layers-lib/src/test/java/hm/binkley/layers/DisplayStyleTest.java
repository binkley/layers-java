package hm.binkley.layers;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static hm.binkley.layers.DisplayStyle.BRACES;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DisplayStyleTest {
    @Test
    void shouldNotExpandLayerAsKey() {
        final Map<?, ?> map = new LinkedHashMap<Object, Object>() {{
            put(new ScratchLayer(null).
                    put("Bob", "Fred"), 3);
        }};

        assertFalse(BRACES.display(map).contains("Bob"));
    }

    @Test
    void shouldQuoteStringValues() {
        final Map<?, ?> map = new LinkedHashMap<Object, Object>() {{
            put(3, "ABC");
        }};

        assertTrue(BRACES.display(map).contains("\"ABC\""));
    }
}
