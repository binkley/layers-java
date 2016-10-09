package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.dnd.Proficiencies.ACROBATICS;
import static hm.binkley.layers.dnd.Proficiencies.ATHLETICS;
import static hm.binkley.layers.dnd.Proficiencies.doubleProficiency;
import static hm.binkley.layers.dnd.Proficiencies.proficiencyBonus;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProficienciesTest {
    private Layers layers;
    private Layer firstLayer;

    @BeforeEach
    void setUpLayers() {
        firstLayer = firstLayer(Bases::baseRuleProficiencyBonuses,
                layers -> this.layers = layers);
    }

    @Test
    void shouldDoubleProficiencies() {
        assertEquals((Integer) 2, firstLayer.
                saveAndNext(Bases::baseRules).
                saveAndNext(proficiencyBonus(ACROBATICS, 1)).
                saveAndNext(proficiencyBonus(ATHLETICS, 1)).
                saveAndNext(doubleProficiency(ACROBATICS)).
                whatIfWith().
                get(ACROBATICS));
    }
}
