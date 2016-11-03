package hm.binkley.layers.set;

import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static hm.binkley.layers.set.LayerSetCommand.add;
import static hm.binkley.layers.set.LayerSetCommand.remove;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LayerSetCommandTest {
    @Test
    void shouldRememberName() {
        assertEquals("Bob", add("Bob", null).name());
    }

    @Test
    void shouldAdd() {
        final LayerSet<ScratchLayer> set = new LayerSet<>(
                new NamedFullnessFunction<>((s, layer) -> false, "Bounded"));
        final ScratchLayer layer = new ScratchLayer(null);
        add("Add Bob", layer).accept(set);

        assertEquals(singleton(layer), set);
    }

    @Test
    void shouldRemove() {
        final LayerSet<ScratchLayer> set = new LayerSet<>(
                new NamedFullnessFunction<>((s, layer) -> false, "Bounded"));
        final ScratchLayer layer = new ScratchLayer(null);
        add("Add Bob", layer).accept(set);
        remove("Remove Bob", layer).accept(set);

        assertEquals(emptySet(), set);
    }

    @Test
    void shouldComplainWhenRemovingAndEmpty() {
        final LayerSet<ScratchLayer> set = new LayerSet<>(
                new NamedFullnessFunction<>((s, layer) -> false, "Bounded"));
        final ScratchLayer layer = new ScratchLayer(null);

        assertThrows(NoSuchElementException.class,
                () -> remove("Remove Bob", layer).accept(set));
    }
}
