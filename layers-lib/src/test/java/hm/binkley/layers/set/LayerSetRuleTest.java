package hm.binkley.layers.set;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.rules.Rule.layerSet;
import static hm.binkley.layers.set.FullnessFunction.max;
import static hm.binkley.layers.set.FullnessFunction.named;
import static hm.binkley.layers.set.LayerSetCommand.add;
import static hm.binkley.layers.set.LayerSetCommand.remove;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LayerSetRuleTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(ScratchLayer::new,
                layers -> this.layers = layers);
    }

    @Test
    void shouldUseLayerSet() {
        firstLayer.
                put("A", layerSet(max(1))).
                saveAndNext(ScratchLayer::new).
                put("A", add("Add " + firstLayer.name(), firstLayer)).
                saveAndNext(ScratchLayer::new);

        assertEquals(singleton(firstLayer), layers.get("A"));
    }

    @Test
    void shouldCapOutLayerSet() {
        final Layer secondLayer = firstLayer.
                put("A", add("Add " + firstLayer.name(), firstLayer), max(1)).
                saveAndNext(ScratchLayer::new);
        secondLayer.
                put("A", add("Add " + secondLayer.name(), secondLayer));

        assertThrows(IllegalStateException.class,
                () -> secondLayer.saveAndNext(ScratchLayer::new));
    }

    @Disabled("WIP")
    @Test
    void shouldRemoveMembers() {
        firstLayer.
                put("A", layerSet(max(1))).
                saveAndNext(ScratchLayer::new).
                put("A", add("Add " + firstLayer.name(), firstLayer)).
                saveAndNext(ScratchLayer::new).
                put("A", remove("Remove " + firstLayer.name(), firstLayer)).
                saveAndNext(ScratchLayer::new);

        assertEquals(emptySet(), layers.get("A"));
    }

    @Test
    void shouldComplainWhenRemovingNonMember() {
        assertThrows(NoSuchElementException.class, () -> firstLayer.
                put("A", layerSet(max(1))).
                saveAndNext(ScratchLayer::new).
                put("A", remove("Remove " + firstLayer.name(), firstLayer)).
                saveAndNext(ScratchLayer::new));
    }

    @Test
    void shouldDisplayRuleNameWhenAvailable() {
        firstLayer.
                put("A", add("Add " + firstLayer.name(), firstLayer),
                        named(max(1), "Bob!")).
                saveAndNext(ScratchLayer::new);

        assertTrue(firstLayer.toString().contains("Bob!"));
    }
}
