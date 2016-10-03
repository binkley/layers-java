package hm.binkley.layers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.beltOfGiantStrength;
import static hm.binkley.layers.dnd.AbilityScore.STR;
import static hm.binkley.layers.dnd.AbilityScore.abilityScores;
import static hm.binkley.layers.dnd.AbilityScore.defaultRuleAbilityScores;
import static hm.binkley.layers.dnd.CharacterDescription.NAME;
import static hm.binkley.layers.dnd.CharacterDescription.characterDescription;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LayersTest {
    private Layers layers;

    @BeforeEach
    void setUpLayers() {
        layers = new Layers();
    }

    @Test
    void shouldHaveNoStrengthBeforeAddingScores() {
        defaultRuleAbilityScores(layers).commit(Layer::new);

        assertEquals((Integer) 0, layers.get(STR));
    }

    @Test
    void shouldHaveNetStrengthAfterGainingAbility() {
        defaultRuleAbilityScores(layers).
                commit(layers -> abilityScores(layers, 8, 15, 14, 10, 13, 12)).
                commit(layers -> abilityScores(layers, 1, 0, 0, 0, 0, 0)).
                commit(Layer::new);

        assertEquals((Integer) 9, layers.get(STR));
    }

    @Test
    void shouldHaveStrengthOfBelt() {
        defaultRuleAbilityScores(layers).
                commit(layers -> abilityScores(layers, 8, 15, 14, 10, 13, 12)).
                commit(layers -> abilityScores(layers, 1, 0, 0, 0, 0, 0)).
                commit(layers -> beltOfGiantStrength(layers, 20)).
                commit(Layer::new);

        assertEquals((Integer) 20, layers.get(STR));
    }

    @Test
    void shouldHaveStrengthOfBeltAfterGainingAbility() {
        defaultRuleAbilityScores(layers).
                commit(layers -> abilityScores(layers, 8, 15, 14, 10, 13, 12)).
                commit(layers -> abilityScores(layers, 1, 0, 0, 0, 0, 0)).
                commit(layers -> beltOfGiantStrength(layers, 20)).
                commit(layers -> abilityScores(layers, 1, 0, 0, 0, 0, 0)).
                commit(Layer::new);

        assertEquals((Integer) 20, layers.get(STR));
    }

    @Test
    void shouldHaveNetStrengthAfterRemovingBelt() {
        final Layer girdle = defaultRuleAbilityScores(layers).
                commit(layers -> abilityScores(layers, 8, 15, 14, 10, 13, 12)).
                commit(layers -> abilityScores(layers, 1, 0, 0, 0, 0, 0)).
                commit(layers -> beltOfGiantStrength(layers, 20));
        girdle.commit(layers -> abilityScores(layers, 1, 0, 0, 0, 0, 0)).
                commit(Layer::new);
        girdle.rollback();

        assertEquals((Integer) 10, layers.get(STR));
    }

    @Test
    void shouldHaveMostRecentName() {
        characterDescription(layers, "Bob").
                commit(layers -> characterDescription(layers, "Nancy")).
                commit(Layer::new);

        assertEquals("Nancy", layers.get(NAME));
    }
}
