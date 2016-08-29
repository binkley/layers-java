package hm.binkley.layers;

import hm.binkley.layers.Field.IntegerField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static hm.binkley.layers.Layers.newLayers;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.expectThrows;

/**
 * {@code LayersTest} tests {@link Layers}.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 */
public class LayersTest {
    private Layers layers;
    private Layer first;

    @BeforeEach
    void setUp() {
        final AtomicReference<Layer> holder = new AtomicReference<>();
        layers = newLayers(BlankLayer::new, holder::set);
        first = holder.get();
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
        final BlankLayer next = first.accept(BlankLayer::new);
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
        final BlankLayer next = first.reject(BlankLayer::new);
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
    void shouldHaveKeyForLayer() {
        first.put("Bob", "Builder");
        final Layer next = first.accept(BlankLayer::new).
                put("Bob", "Nancy");
        // @formatter:off
        assertAll(
                () -> assertEquals("Builder", layers.get("Bob")),
                () -> assertEquals("Builder", layers.accepted().get("Bob")),
                () -> assertEquals(1, layers.size()),
                () -> assertEquals("Builder", layers.history().
                        collect(toList()).
                        get(0).
                        get("Bob")),
                () -> assertEquals("Nancy", next.changed().get("Bob")),
                () -> assertEquals("Nancy", next.whatIf().get("Bob")));
        // @formatter:on
    }

    @Test
    void shouldHaveLastKeyForLayer() {
        first.put("Bob", "Builder");
        first.accept(BlankLayer::new).
                put("Bob", "Nancy").
                accept(BlankLayer::new);
        // @formatter:off
        assertAll(
                () -> assertEquals("Nancy", layers.get("Bob")),
                () -> assertEquals("Nancy", layers.get("Bob")),
                () -> assertEquals(1, layers.size()),
                () -> assertEquals("Nancy", layers.accepted().get("Bob")),
                () -> assertEquals("Nancy", layers.history().
                        collect(toList()).
                        get(1).
                        get("Bob")));
        // @formatter:on
    }

    @Test
    void shouldApplyFieldRule() {
        layers.add("Total", new IntegerField((a, b) -> a + b));
        first.put("Total", 1).
                accept(BlankLayer::new).
                put("Total", 2).
                accept(BlankLayer::new);
        assertEquals((Integer) 3, layers.get("Total"));
    }

    @Test
    void shouldComplainForWrongValue() {
        layers.add("Total", new IntegerField((a, b) -> a + b));
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
}
