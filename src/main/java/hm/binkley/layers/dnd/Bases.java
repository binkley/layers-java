package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.dnd.MagicItems.Attunement;

import static hm.binkley.layers.Value.mostRecent;
import static hm.binkley.layers.Value.sumAll;
import static java.util.Collections.emptySet;

@SuppressWarnings("WeakerAccess")
public final class Bases {
    private Bases() {}

    public static Layer baseRules(final Surface layers) {
        return new Layer(layers, "Base rules").
                blend(Bases::baseRuleCharacterDescription).
                blend(Bases::baseRuleAbilityScores).
                blend(Bases::baseRuleProficiencyBonuses).
                blend(Bases::baseMagicItems);
    }

    /** @todo Are all character values strings? */
    public static Layer baseRuleCharacterDescription(final Surface layers) {
        final Layer layer = new Layer(layers,
                "Base character description rules");
        for (final Characters key : Characters.values())
            layer.put(key, mostRecent(key, ""));
        return layer;
    }

    public static Layer baseRuleAbilityScores(final Surface layers) {
        final Layer layer = new Layer(layers, "Base ability scores rules");
        for (final Abilities key : Abilities.values())
            layer.put(key, sumAll(key));
        return layer;
    }

    public static Layer baseRuleProficiencyBonuses(final Surface layers) {
        final Layer layer = new Layer(layers, "Base proficiency bonus rules");
        for (final Proficiencies key : Proficiencies.values())
            layer.put(key, sumAll(key));
        return layer;
    }

    public static Layer baseMagicItems(final Surface layers) {
        final Layer layer = new Layer(layers, "Base magic items rules");
        layer.put(Attunement.class, mostRecent(Attunement.class, emptySet()));
        return layer;
    }
}
