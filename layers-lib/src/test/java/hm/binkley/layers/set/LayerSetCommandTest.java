package hm.binkley.layers.set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.set.LayerSetCommand.add;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LayerSetCommandTest {
    @Test
    @Disabled
    void shouldRememberName() {
        assertEquals("Bob", add("Bob", null).name());
    }
}
