package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.Surface;
import hm.binkley.layers.Value;
import hm.binkley.layers.XEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static hm.binkley.layers.Value.ofValue;
import static java.util.Collections.unmodifiableList;

public class Abilities
        extends XEnum<Abilities> {
    private static final AtomicInteger ordinal = new AtomicInteger();
    private static final List<Abilities> values = new ArrayList<>();
    public static final Abilities STR = new Abilities("STR");
    public static final Abilities DEX = new Abilities("DEX");
    public static final Abilities CON = new Abilities("CON");
    public static final Abilities INT = new Abilities("INT");
    public static final Abilities WIS = new Abilities("WIS");
    public static final Abilities CHA = new Abilities("CHA");

    public static List<Abilities> values() {
        return unmodifiableList(values);
    }

    protected Abilities(final String name) {
        super(name, ordinal.getAndIncrement());
        values.add(this);
    }

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
        for (final Abilities ability : values())
            layer.put(ability, Value.sumAll(ability));
        return layer;
    }
}
