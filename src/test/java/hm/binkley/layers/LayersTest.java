package hm.binkley.layers;

import hm.binkley.layers.Field.CollectionField;
import hm.binkley.layers.Field.IntegerField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static hm.binkley.layers.BlankLayer.blankLayer;
import static hm.binkley.layers.Field.IntegerField.additiveIntegerField;
import static hm.binkley.layers.Layers.newLayers;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.expectThrows;

/**
 * {@code LayersTest} tests {@link Layers}.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 */
public class LayersTest {
    private Layers layers;
    private BlankLayer<?> first;

    @BeforeEach
    void setUp() {
        layers = newLayers(blankLayer("first"), l -> first = l);
    }

    @Test
    void shouldStartEmpty() {
        // @formatter:off
        assertAll(() -> assertNotNull(layers),
                () -> assertTrue(layers.isEmpty()),
                () -> assertEquals(0, layers.size()),
                () -> assertTrue(layers.accepted().isEmpty()),
                () -> assertEquals(0, layers.history().count()),
                () -> assertNotNull(first),
                () -> assertTrue(first.isEmpty()),
                () -> assertEquals(0, first.size()),
                () -> assertTrue(first.changed().isEmpty()));
        // @formatter:on
    }

    @Test
    void shouldAccept() {
        final BlankLayer next = first.accept(blankLayer("next"));
        // @formatter:off
        assertAll(
                () -> assertTrue(layers.accepted().isEmpty()),
                () -> assertEquals(0, layers.size()),
                () -> assertEquals(1, layers.history().count()),
                () -> assertNotNull(next),
                () -> assertTrue(next.isEmpty()),
                () -> assertEquals(0, next.size()),
                () -> assertTrue(next.changed().isEmpty()));
        // @formatter:on
    }

    @Test
    void shouldReject() {
        final BlankLayer next = first.reject(blankLayer("next"));
        // @formatter:off
        assertAll(
                () -> assertTrue(layers.accepted().isEmpty()),
                () -> assertEquals(0, layers.size()),
                () -> assertEquals(0, layers.history().count()),
                () -> assertNotNull(next),
                () -> assertTrue(next.isEmpty()),
                () -> assertEquals(0, next.size()),
                () -> assertTrue(next.changed().isEmpty()));
        // @formatter:on
    }

    @Test
    void shouldComplainIfAlreadyAccepted() {
        first.accept(blankLayer("next"));

        assertThrows(IllegalStateException.class, () -> first.put("a", 3));
        assertThrows(IllegalStateException.class, () -> first.accept(null));
        assertThrows(IllegalStateException.class, () -> first.reject(null));
    }

    @Test
    void shouldComplainIfAlreadyRejected() {
        first.accept(blankLayer("next"));

        assertThrows(IllegalStateException.class, () -> first.put("a", 3));
        assertThrows(IllegalStateException.class, () -> first.accept(null));
        assertThrows(IllegalStateException.class, () -> first.reject(null));
    }

    @Test
    void shouldHaveKeyForLayer() {
        first.put("Bob", "Builder");
        final BlankLayer<?> next = first.accept(blankLayer("next")).
                put("Bob", "Nancy");
        // @formatter:off
        assertAll(
                () -> assertEquals("Builder", layers.get("Bob")),
                () -> assertEquals("Builder", layers.accepted().get("Bob")),
                () -> assertEquals(1, layers.size()),
                () -> assertEquals("first", layers.history().
                        findFirst().
                        map(Layer::name).
                        get()),
                () -> assertEquals("Builder", layers.history().
                        findFirst().
                        map(Layer::changed).
                        map(m -> m.get("Bob")).
                        get()),
                () -> assertEquals("Nancy", next.changed().get("Bob")),
                () -> assertEquals("Nancy", next.whatIf().get("Bob")));
        // @formatter:on
    }

    @Test
    void shouldHaveLastKeyForLayer() {
        first.put("Bob", "Builder");
        first.accept(blankLayer("next")).
                put("Bob", "Nancy").
                accept(blankLayer("last"));
        // @formatter:off
        assertAll(
                () -> assertEquals("Nancy", layers.get("Bob")),
                () -> assertEquals("Nancy", layers.get("Bob")),
                () -> assertEquals(1, layers.size()),
                () -> assertEquals("Nancy", layers.accepted().get("Bob")),
                () -> assertEquals("next", layers.history().
                        map(Layer::name).
                        collect(toList()).
                        get(1)),
                () -> assertEquals("Nancy", layers.history().
                        map(Layer::changed).
                        map(m -> m.get("Bob")).
                        collect(toList()).
                        get(1)));
        // @formatter:on
    }

    @Test
    void shouldApplyFieldRule() {
        layers.add("Total", additiveIntegerField());
        first.put("Total", 1).
                accept(blankLayer("next")).
                put("Total", 2).
                accept(blankLayer("last"));
        assertEquals((Integer) 3, layers.get("Total"));
    }

    @Test
    void shouldComplainForWrongValue() {
        layers.add("Total", additiveIntegerField());
        final ClassCastException thrown = expectThrows(
                ClassCastException.class, () -> first.put("Total", "Fred"));
        final String message = thrown.getMessage();
        // @formatter:off
        assertAll(
                () -> assertNotNull(message),
                () -> assertTrue(message.contains("Total")),
                () -> assertTrue(message.contains("Fred")),
                () -> assertTrue(message.contains(Integer.class.getName())),
                () -> assertTrue(message.contains(String.class.getName())));
        // @formatter:on
    }

    @Test
    void shouldPreserveHistoryWhenAcceptingSameNamedLayer() {
        first.put("Bob", "Builder").
                accept(blankLayer("first")).
                put("Bob", "Nancy").
                accept(blankLayer("next"));
        // @formatter:off
        assertAll(
                () -> assertEquals("Nancy", layers.get("Bob")),
                () -> assertEquals(2, layers.history().count()));
        // @formatter:on
    }

    @Test
    void shouldDisallowFieldForExistingKey() {
        final IllegalStateException thrown = expectThrows(
                IllegalStateException.class,
                () -> first.put("Bob", "Builder").
                        accept(blankLayer("next"),
                                singletonMap("Bob", additiveIntegerField())).
                        put("Bob", 3).
                        accept(blankLayer("last")));

        assertTrue(thrown.getMessage().contains("Bob"));
    }

    @Test
    void shouldFindLayerByName() {
        first.put("Tom", "Sawyer");
        final BlankLayer second = first.
                accept(blankLayer("first"));
        second.put("Huck", "Finn");
        second.accept(blankLayer("first"));

        final View<String, Object> layer = layers.layer("first");
        assertAll(
                // @formatter:off
                () -> assertFalse(layer.containsKey("Tom")),
                () -> assertTrue(layer.containsKey("Huck")));
                // @formatter:on
    }

    @Test
    void shouldMergeCollectionField() {
        layers.add("Free Stuff", new CollectionField(ArrayList::new));
        first.put("Free Stuff", asList("Beer", "Chips")).
                accept(blankLayer("next")).
                put("Free Stuff", asList("Speech", "Air")).
                accept(blankLayer("last"));

        assertEquals(asList("Beer", "Chips", "Speech", "Air"),
                layers.get("Free Stuff"));
    }

    @Test
    void shouldUseHistoryInComputingWhatIfValues() {
        layers.add("Fibonacci", new IntegerField(a -> {
            switch (a.size()) {
            case 0:
                return 0;
            case 1:
                return a.get(0);
            default:
                return a.get(a.size() - 2) + a.get(a.size() - 1);
            }
        }));
        assertEquals(3, first.put("Fibonacci", 1).
                accept(blankLayer("next")).
                put("Fibonacci", 1).
                accept(blankLayer("last")).
                put("Fibonacci", 2).
                whatIf().get("Fibonacci"));
    }
}
