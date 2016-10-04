package hm.binkley.layers;

import hm.binkley.layers.Layer.LayerView;
import hm.binkley.layers.dnd.Abilities;
import hm.binkley.layers.dnd.Proficiencies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.Abilities.abilityScoreIncrease;
import static hm.binkley.layers.dnd.Abilities.abilityScores;
import static hm.binkley.layers.dnd.Characters.NAME;
import static hm.binkley.layers.dnd.Characters.characterDescription;
import static hm.binkley.layers.dnd.MagicItems.beltOfGiantStrength;
import static hm.binkley.layers.dnd.Proficiencies.ACROBATICS;
import static hm.binkley.layers.dnd.Proficiencies.ATHLETICS;
import static hm.binkley.layers.dnd.Proficiencies.doubleProficiency;
import static hm.binkley.layers.dnd.Proficiencies.proficiencyBonus;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;
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
                put("FOO", Value.ofValue("BAR")).
                saveAndNext(ScratchLayer::new);

        assertTrue(layers.containsKey("FOO"));
    }

    @Test
    void shouldHaveKeys() {
        firstLayer.
                put("FOO", null). // TODO: This should die, but not today!
                saveAndNext(ScratchLayer::new);

        assertEquals(singleton("FOO"), layers.keys());
    }

    @Test
    void shouldHaveHistory() {
        firstLayer.
                saveAndNext(ScratchLayer::new);

        final List<LayerView> history = layers.
                history().
                collect(toList());

        assertEquals(1, history.size());
    }

    @Test
    void shouldHaveNoStrengthBeforeAddingScores() {
        firstLayer.
                saveAndNext(Abilities::baseRuleAbilityScores).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 0, layers.get(STR));
    }

    @Test
    void shouldHaveNetStrengthAfterGainingAbility() {
        firstLayer.
                saveAndNext(Abilities::baseRuleAbilityScores).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 9, layers.get(STR));
    }

    @Test
    void shouldHaveStrengthOfBelt() {
        firstLayer.
                saveAndNext(Abilities::baseRuleAbilityScores).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(beltOfGiantStrength(20)).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 20, layers.get(STR));
    }

    @Test
    void shouldHaveStrengthOfBeltAfterGainingAbility() {
        firstLayer.
                saveAndNext(Abilities::baseRuleAbilityScores).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(beltOfGiantStrength(20)).
                saveAndNext(abilityScoreIncrease(STR)).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 20, layers.get(STR));
    }

    @Test
    void shouldHaveNetStrengthAfterRemovingBelt() {
        final Layer girdle = firstLayer.
                saveAndNext(Abilities::baseRuleAbilityScores).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(beltOfGiantStrength(20));
        girdle.saveAndNext(abilityScoreIncrease(STR)).
                saveAndNext(ScratchLayer::new);
        girdle.forget();

        assertEquals((Integer) 11, layers.get(STR));
    }

    @Test
    void shouldHaveMostRecentName() {
        firstLayer.
                saveAndNext(characterDescription("Bob")).
                saveAndNext(characterDescription("Nancy")).
                saveAndNext(ScratchLayer::new);

        assertEquals("Nancy", layers.get(NAME));
    }

    @Test
    void shouldDoubleProficiencies() {
        firstLayer.
                saveAndNext(Proficiencies::baseRuleProficiencyBonuses).
                saveAndNext(proficiencyBonus(ACROBATICS, 1)).
                saveAndNext(proficiencyBonus(ATHLETICS, 1)).
                saveAndNext(doubleProficiency(ACROBATICS)).
                saveAndNext(ScratchLayer::new);

        assertEquals((Integer) 2, layers.get(ACROBATICS));
    }
}
