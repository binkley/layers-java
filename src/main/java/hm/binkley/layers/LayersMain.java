package hm.binkley.layers;

import hm.binkley.layers.dnd.Abilities;
import hm.binkley.layers.dnd.BaseRule;
import hm.binkley.layers.dnd.Characters;
import hm.binkley.layers.dnd.MagicItems;
import hm.binkley.layers.dnd.MagicItems.MagicItem;
import hm.binkley.layers.dnd.Proficiencies;

import static hm.binkley.layers.Layers.firstLayer;
import static hm.binkley.layers.dnd.Abilities.CON;
import static hm.binkley.layers.dnd.Abilities.STR;
import static hm.binkley.layers.dnd.Abilities.WIS;
import static hm.binkley.layers.dnd.Abilities.abilityScoreIncrease;
import static hm.binkley.layers.dnd.Abilities.abilityScores;
import static hm.binkley.layers.dnd.Characters.characterDescription;
import static hm.binkley.layers.dnd.Proficiencies.ACROBATICS;
import static hm.binkley.layers.dnd.Proficiencies.ATHLETICS;
import static hm.binkley.layers.dnd.Proficiencies.doubleProficiency;
import static hm.binkley.layers.dnd.Proficiencies.proficiencyBonus;
import static hm.binkley.layers.dnd.Races.humanVariant;
import static java.lang.System.out;

/**
 * @todo Annoying, cannot inline with {@link Layers} because Jacoco does not
 * support excluding methods, and #main blows up coverage
 */
public class LayersMain {
    public static void main(final String... args) {
        final Layers[] layersHolder = new Layers[1];
        final Layer firstLayer = firstLayer(BaseRule::baseRules,
                layers -> layersHolder[0] = layers);
        final Layers layers = layersHolder[0];

        final MagicItem beltOfHillGiantStrength = firstLayer.
                saveAndNext(characterDescription("Bob")).
                saveAndNext(abilityScores(8, 15, 14, 10, 13, 12)).
                saveAndNext(humanVariant().withSTR().withDEX()).
                saveAndNext(proficiencyBonus(ACROBATICS, 1)).
                saveAndNext(proficiencyBonus(ATHLETICS, 1)).
                saveAndNext(doubleProficiency(ACROBATICS)).
                saveAndNext(MagicItems::beltOfHillGiantStrength);
        final MagicItem amuletOfHealth = beltOfHillGiantStrength.
                saveAndNext(abilityScoreIncrease(STR)).
                saveAndNext(abilityScoreIncrease(CON, WIS)).
                saveAndNext(MagicItems::amuletOfHealth);
        amuletOfHealth.attuneAndNext(ScratchLayer::new);
        beltOfHillGiantStrength.attuneAndNext(ScratchLayer::new);

        out.println(layers);

        for (final Characters description : Characters.values())
            out.println(description + " = " + layers.get(description));

        for (final Abilities score : Abilities.values())
            out.println(score + " = " + layers.get(score));

        for (final Proficiencies bonus : Proficiencies.values())
            out.println(bonus + " = " + layers.get(bonus));

        layers.view(layer -> layer instanceof MagicItem).
                forEach(out::println);
    }
}
