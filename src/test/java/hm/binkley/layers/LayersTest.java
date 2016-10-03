package hm.binkley.layers;

import hm.binkley.layers.dnd.Abilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.Abilities.abilityScores;
import static hm.binkley.layers.dnd.Characters.NAME;
import static hm.binkley.layers.dnd.Characters.characterDescription;
import static hm.binkley.layers.dnd.MagicItems.beltOfGiantStrength;
import static hm.binkley.layers.dnd.Proficiencies.ACROBATICS;
import static hm.binkley.layers.dnd.Proficiencies.ATHLETICS;
import static hm.binkley.layers.dnd.Proficiencies.doubleProficiency;
import static hm.binkley.layers.dnd.Proficiencies.proficiencyBonus;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LayersTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(Abilities::baseRuleAbilityScores,
                layers -> this.layers = layers);
    }

    @Test
    void shouldHaveNoStrengthBeforeAddingScores() {
        firstLayer.saveAndNext(Layer::new);

        assertEquals((Integer) 0, layers.get(STR));
    }

    @Test
    void shouldHaveNetStrengthAfterGainingAbility() {
        firstLayer.
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(Layer::new);

        assertEquals((Integer) 9, layers.get(STR));
    }

    @Test
    void shouldHaveStrengthOfBelt() {
        firstLayer.
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(beltOfGiantStrength(20)).
                saveAndNext(Layer::new);

        assertEquals((Integer) 20, layers.get(STR));
    }

    @Test
    void shouldHaveStrengthOfBeltAfterGainingAbility() {
        firstLayer.
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(beltOfGiantStrength(20)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(Layer::new);

        assertEquals((Integer) 20, layers.get(STR));
    }

    @Test
    void shouldHaveNetStrengthAfterRemovingBelt() {
        final Layer girdle = firstLayer.
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(beltOfGiantStrength(20));
        girdle.saveAndNext(abilityScores(1, 0, 0, 0, 0, 0)).
                saveAndNext(Layer::new);
        girdle.forget();

        assertEquals((Integer) 10, layers.get(STR));
    }

    @Test
    void shouldHaveMostRecentName() {
        firstLayer.
                saveAndNext(characterDescription("Bob")).
                saveAndNext(characterDescription("Nancy")).
                saveAndNext(Layer::new);

        assertEquals("Nancy", layers.get(NAME));
    }

    @Test
    void shouldDoubleProficiencies() {
        firstLayer.
                saveAndNext(proficiencyBonus(ACROBATICS, 1)).
                saveAndNext(proficiencyBonus(ATHLETICS, 1)).
                saveAndNext(doubleProficiency(ACROBATICS)).
                saveAndNext(Layer::new);

        assertEquals((Integer) 2, layers.get(ACROBATICS));
    }
}
