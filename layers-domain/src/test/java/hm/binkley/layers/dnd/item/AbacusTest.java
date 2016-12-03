package hm.binkley.layers.dnd.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class AbacusTest {
    @Test
    void shouldKnowName() {
        assertEquals("Abacus", new Abacus(null).name());
    }
}
