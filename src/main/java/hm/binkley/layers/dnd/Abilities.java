package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.Value;

import static hm.binkley.layers.Value.ofValue;

public enum Abilities {
    STR,
    DEX,
    CON,
    INT,
    WIS,
    CHA;

    public static LayerMaker abilityScores(final int _str, final int _dex,
            final int _con, final int _int, final int _wis, final int _cha) {
        return layers -> {
            final Layer layer = new Layer(layers, "Base ability scores");
            layer.put(STR, ofValue(_str));
            layer.put(DEX, ofValue(_dex));
            layer.put(CON, ofValue(_con));
            layer.put(INT, ofValue(_int));
            layer.put(WIS, ofValue(_wis));
            layer.put(CHA, ofValue(_cha));
            return layer;
        };
    }

    public static LayerMaker abilityScoreIncrease(final Abilities ability) {
        return layers -> {
            final Layer layer = new Layer(layers,
                    "Ability (1) score increase");
            layer.put(ability, ofValue(2));
            return layer;
        };
    }

    public static LayerMaker abilityScoreIncrease(
            final Abilities firstAbility, final Abilities secondAbility) {
        return layers -> {
            final Layer layer = new Layer(layers,
                    "Ability (2) score increases");
            layer.put(firstAbility, ofValue(1));
            layer.put(secondAbility, ofValue(1));
            return layer;
        };
    }

    public static Layer baseRuleAbilityScores(final Surface layers) {
        final Layer layer = new Layer(layers, "Base ability scores rules");
        for (final Abilities ability : Abilities.values())
            layer.put(ability, Value.sumAll(ability));
        return layer;
    }
}
