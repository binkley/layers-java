package hm.binkley.layers.dnd;

import hm.binkley.layers.Layer;
import hm.binkley.layers.LayerMaker;
import hm.binkley.layers.Layers.LayerSurface;
import hm.binkley.layers.XEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    public Abilities(final String name) {
        super(name, ordinal.getAndIncrement());
        values.add(this);
    }

    /** @todo Builder */
    public static LayerMaker<AbilitiesLayer> abilityScores(final int strength,
            final int dexterity, final int constitution,
            final int intelligence, final int wisdom, final int charisma) {
        return layers -> new AbilitiesLayer(layers,
                "Base ability " + "scores").
                put(STR, strength).
                put(DEX, dexterity).
                put(CON, constitution).
                put(INT, intelligence).
                put(WIS, wisdom).
                put(CHA, charisma);
    }

    public static LayerMaker<AbilitiesLayer> abilityScoreIncrease(
            final Abilities doubleAbility) {
        return layers -> new AbilitiesLayer(layers,
                "Ability Score (" + doubleAbility + ") double increase").
                put(doubleAbility, 2);
    }

    public static LayerMaker<AbilitiesLayer> abilityScoreIncrease(
            final Abilities firstAbility, final Abilities secondAbility) {
        return layers -> new AbilitiesLayer(layers,
                "Ability Score (" + firstAbility + ", " + secondAbility
                        + ") increases").
                put(firstAbility, 1).
                put(secondAbility, 1);
    }

    public static final class AbilitiesLayer
            extends Layer<AbilitiesLayer> {
        private AbilitiesLayer(final LayerSurface layers, final String name) {
            super(layers, name);
        }
    }
}
