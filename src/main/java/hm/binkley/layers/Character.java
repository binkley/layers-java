package hm.binkley.layers;

import hm.binkley.layers.Field.StringField;

import java.util.HashMap;
import java.util.Map;

import static hm.binkley.layers.Field.IntegerField.additiveIntegerField;
import static hm.binkley.layers.Layers.newLayers;
import static java.lang.System.out;

/**
 * {@code Character} <b>needs documentation</b>.
 *
 * @author <a href="mailto:binkley@alumni.rice.edu">B. K. Oxley (binkley)</a>
 * @todo Needs documentation.
 */
public final class Character {
    private final Layers layers;
    private Layer current;

    public static void main(final String... args) {
        final Character bob = new Character();
        out.println(bob);
        bob.layers.history().forEach(out::println);
    }

    public Character() {
        layers = newLayers(
                s -> new PlayerLayer(s, "Brian", "The Empire of Texas"),
                l -> current = l, PlayerLayer.fields);
        current = current.
                accept(s -> new CharacterLayer(s, "Bob"),
                        CharacterLayer.fields).
                accept(s -> new StatsLayer(s, 15, 14, 13, 12, 10, 8),
                        StatsLayer.fields).
                accept(HumanLayer::new, HumanLayer.fields).
                accept(s -> new CharacterLayer(s, "Sally")). // ex of rename
                accept(s -> new BlankLayer(s, "Scratch"));
    }

    @Override
    public String toString() {
        return layers.toString();
    }

    public static final class PlayerLayer
            extends BlankLayer {
        public static final String PLAYER_NAME = "player:name";
        public static final String PLAYER_CAMPAIGN = "player:campaign";

        private static final Map<String, Field> fields = new HashMap<>();

        static {
            fields.put(PLAYER_NAME, new StringField());
            fields.put(PLAYER_CAMPAIGN, new StringField());
        }

        public PlayerLayer(final Surface surface, final String name,
                final String campaign) {
            super(surface, "Player");
            put(PLAYER_NAME, name);
            put(PLAYER_CAMPAIGN, campaign);
        }
    }

    public static final class CharacterLayer
            extends BlankLayer {
        private static final Map<String, Field> fields = new HashMap<>();

        public static final String CHARACTER_NAME = "character:name";

        static {
            fields.put(CHARACTER_NAME, new StringField());
        }

        public CharacterLayer(final Surface surface, final String name) {
            super(surface, "Character");
            put(CHARACTER_NAME, name);
        }
    }

    public static final class StatsLayer
            extends BlankLayer {
        public static final String STATS_STR = "stats:STR";
        public static final String STATS_DEX = "stats:DEX";
        public static final String STATS_CON = "stats:CON";
        public static final String STATS_INT = "stats:INT";
        public static final String STATS_WIS = "stats:WIS";
        public static final String STATS_CHA = "stats:CHA";

        private static final Map<String, Field> fields = new HashMap<>();

        static {
            fields.put(STATS_STR, additiveIntegerField());
            fields.put(STATS_DEX, additiveIntegerField());
            fields.put(STATS_CON, additiveIntegerField());
            fields.put(STATS_INT, additiveIntegerField());
            fields.put(STATS_WIS, additiveIntegerField());
            fields.put(STATS_CHA, additiveIntegerField());
        }

        public StatsLayer(final Surface surface, final int STR, final int DEX,
                final int CON, final int INT, final int WIS, final int CHA) {
            super(surface, "Stats");
            put(STATS_STR, STR);
            put(STATS_DEX, DEX);
            put(STATS_CON, CON);
            put(STATS_INT, INT);
            put(STATS_WIS, WIS);
            put(STATS_CHA, CHA);
        }
    }

    private abstract static class RaceLayer
            extends BlankLayer {
        protected static final Map<String, Field> fields = new HashMap<>();

        static {
            fields.put("race:name", new StringField());
            fields.put("race:STR-bonus", additiveIntegerField());
            fields.put("race:DEX-bonus", additiveIntegerField());
            fields.put("race:CON-bonus", additiveIntegerField());
            fields.put("race:INT-bonus", additiveIntegerField());
            fields.put("race:WIS-bonus", additiveIntegerField());
            fields.put("race:CHA-bonus", additiveIntegerField());
        }

        protected RaceLayer(final Surface surface, final String raceName) {
            super(surface, "Race");
            put("race:name", raceName);
        }
    }

    public static final class HumanLayer
            extends RaceLayer {
        public HumanLayer(final Surface surface) {
            super(surface, "Human");
            put("stats:STR", 1);
            put("race:STR-bonus", 1);
            put("stats:DEX", 1);
            put("race:DEX-bonus", 1);
            put("stats:CON", 1);
            put("race:CON-bonus", 1);
            put("stats:INT", 1);
            put("race:INT-bonus", 1);
            put("stats:WIS", 1);
            put("race:WIS-bonus", 1);
            put("stats:CHA", 1);
            put("race:CHA-bonus", 1);
        }
    }
}
