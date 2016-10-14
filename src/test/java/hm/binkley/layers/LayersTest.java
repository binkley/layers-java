package hm.binkley.layers;

import hm.binkley.layers.dnd.BaseRule;
import hm.binkley.layers.dnd.MagicItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.Rule.mostRecent;
import static hm.binkley.layers.Rule.sumAll;
import static hm.binkley.layers.Value.ofBoth;
import static hm.binkley.layers.Value.ofRule;
import static hm.binkley.layers.Value.ofValue;
import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.Abilities.abilityScoreIncrease;
import static hm.binkley.layers.dnd.Abilities.abilityScores;
import static hm.binkley.layers.dnd.Characters.NAME;
import static hm.binkley.layers.dnd.Characters.characterDescription;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LayersTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(ScratchLayer::new,
                layers -> this.layers = layers);
    }

    @Test
    void shouldStartEmpty() {
        assertTrue(layers.isEmpty());
    }

    @Test
    void shouldStayEmptyAfterAddingEmptyLayer() {
        firstLayer.
                saveAndNext(ScratchLayer::new);

        assertTrue(layers.isEmpty());
    }

    @Test
    void shouldStartAtSizeZero() {
        assertEquals(0, layers.size());
    }

    @Test
    void shouldStayAtSizeZeroAfterAddingEmptyLayer() {
        firstLayer.
                saveAndNext(ScratchLayer::new);

        assertEquals(0, layers.size());
    }

    @Test
    void shouldNotContainKey() {
        assertFalse(layers.containsKey("FOO"));
    }

    @Test
    void shouldContainKey() {
        firstLayer.
                put("FOO", ofBoth("BAR", mostRecent("FOO"))).
                saveAndNext(ScratchLayer::new);

        assertTrue(layers.containsKey("FOO"));
    }

    @Test
    void shouldHaveKeys() {
        firstLayer.
                put("FOO", ofBoth(3, sumAll("FOO"))).
                saveAndNext(ScratchLayer::new);

        assertEquals(singleton("FOO"), layers.keys());
    }

    @Test
    void shouldHaveView() {
        firstLayer.
                put("BOB", ofBoth(17, mostRecent("BOB"))).
                saveAndNext(ScratchLayer::new);

        final List<Map<Object, Object>> view = layers.
                view().
                map(layer -> layer.stream().
                        collect(toMap(Entry::getKey, Entry::getValue))).
                collect(toList());

        assertEquals(singletonList(singletonMap("BOB", 17)), view);
    }

    @Test
    void shouldHaveFilteredView() {
        firstLayer.
                put("BOB", ofBoth(17, mostRecent("BOB"))).
                saveAndNext(EgLayer::new).
                put("BOB", ofValue(18)).
                saveAndNext(ScratchLayer::new);

        final List<Map<Object, Object>> view = layers.
                view(layer -> layer instanceof EgLayer).
                map(layer -> layer.stream().
                        collect(toMap(Entry::getKey, Entry::getValue))).
                collect(toList());

        assertEquals(singletonList(singletonMap("BOB", 18)), view);
    }

    @Test
    void shouldHaveWhatIfWithLayer() {
        firstLayer.
                put("BOB", ofBoth(32, mostRecent("BOB")));

        assertTrue(firstLayer.whatIfWith().containsKey("BOB"));
    }

    @Test
    void shouldHaveWhatIfWithoutLayer() {
        firstLayer.
                put("BOB", ofBoth(31, sumAll("BOB"))).
                saveAndNext(ScratchLayer::new);

        assertFalse(firstLayer.whatIfWithout().containsKey("BOB"));
    }

    @Test
    void shouldProjectToMap() {
        firstLayer.
                put("FOO", ofRule(sumAll("FOO"))).
                saveAndNext(ScratchLayer::new).
                put("FOO", ofValue(3)).
                saveAndNext(ScratchLayer::new);

        assertEquals(singletonMap("FOO", 3), layers.toMap());
    }

    @Test
    void shouldHaveNetStrengthIfBeltNotBetter() {
        firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(abilityScores(23, 8, 8, 8, 8, 8)).
                saveAndNext(MagicItems::beltOfHillGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 23, layers.get(STR));
    }

    @Test
    void shouldHaveNoStrengthBeforeAddingScores() {
        firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 0, layers.get(STR));
    }

    @Test
    void shouldHaveNetStrengthAfterGainingAbility() {
        firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 9, layers.get(STR));
    }

    @Test
    void shouldHaveStrengthOfBelt() {
        firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(MagicItems::beltOfHillGiantStrength).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 21, layers.get(STR));
    }

    @Test
    void shouldHaveStrengthOfBeltAfterGainingAbility() {
        firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(MagicItems::beltOfStoneGiantStrength).
                saveAndNext(abilityScoreIncrease(STR)).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 23, layers.get(STR));
    }

    @Test
    @Disabled("Pending issue #9")
    void shouldHaveNetStrengthAfterRemovingBelt() {
        final Layer girdle = firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(MagicItems::beltOfFrostGiantStrength);
        girdle.saveAndNext(abilityScoreIncrease(STR)).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 11, layers.get(STR));
    }

    @Test
    void shouldHaveMostRecentName() {
        assertEquals("Nancy", firstLayer.
                saveAndNext(BaseRule::baseRules).
                saveAndNext(characterDescription("Bob")).
                saveAndNext(characterDescription("Nancy")).
                whatIfWith().
                get(NAME));
    }

    /** @todo Silly jacoco does not support excluding methods */
    @Test
    void shouldHaveSaneToString() {
        firstLayer.saveAndNext(ScratchLayer::new);

        assertTrue(layers.toString().contains("Scratch"));
    }

    private static final class EgLayer
            extends Layer {
        private EgLayer(final Layers.Surface layers) {
            super(layers, "Eg");
        }
    }
}
