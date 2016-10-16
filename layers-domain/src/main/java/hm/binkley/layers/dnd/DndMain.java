package hm.binkley.layers.dnd;

import hm.binkley.layers.dnd.magic.AmuletOfHealth;
import hm.binkley.layers.dnd.magic.BeltOfHillGiantStrength;
import hm.binkley.layers.dnd.magic.MagicItem;
import hm.binkley.layers.rules.BaseRule;
import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers;
import hm.binkley.layers.ScratchLayer;

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
public class DndMain {
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
                saveAndNext((layers1) -> new BeltOfHillGiantStrength(layers1));
        final MagicItem amuletOfHealth = beltOfHillGiantStrength.
                saveAndNext(abilityScoreIncrease(STR)).
                saveAndNext(abilityScoreIncrease(CON, WIS)).
                saveAndNext((layers1) -> new AmuletOfHealth(layers1));
        amuletOfHealth.attuneAndNext(ScratchLayer::new);
        beltOfHillGiantStrength.attuneAndNext(ScratchLayer::new);

        for (final Characters description : Characters.values())
            out.println(description + " = " + layers.get(description));

        for (final Abilities score : Abilities.values())
            out.println(score + " = " + layers.get(score));

        for (final Proficiencies bonus : Proficiencies.values())
            out.println(bonus + " = " + layers.get(bonus));

        layers.view(layer -> layer instanceof MagicItem).
                forEach(out::println);

        out.println(layers);
    }
}
